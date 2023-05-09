package com.example.back_jiwuquang_api.domain.controller.front;

import com.example.back_jiwuquang_api.dto.sys.UpdatePwdDTO;
import com.example.back_jiwuquang_api.dto.sys.UpdateUserInfoDTO;
import com.example.back_jiwuquang_api.service.sys.UserService;
import com.example.back_jiwuquang_api.util.Result;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.Objects;

import static com.example.back_jiwuquang_api.domain.constant.JwtConstant.HEADER_NAME;

/**
 * 基本信息模块
 *
 * @className: UserInfoController
 * @author: Kiwi23333
 * @description: 基本信息模块
 * @date: 2023/5/1 2:09
 */
@Slf4j
@Api(value = "用户模块", tags = {"用户模块"})
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    UserService usersService;

    /************** 用户基本信息（增删查改）*************/

    @ApiOperation(value = "获取用户信息", tags = "用户基本信息模块")
    @ApiImplicitParam(name = "Authorization", value = "用户token", required = true)
    @GetMapping("/info")
    Result getUserInfo(@RequestHeader("Authorization") String token, HttpServletRequest request) {
        // 获取拦截请求后获取的id
        return usersService.getUserInfoById(String.valueOf(request.getAttribute("userId")));
    }

    @ApiOperation(value = "修改密码", tags = "用户基本信息模块")
    @ApiImplicitParam(name = "Authorization", value = "用户token", required = true)
    @PostMapping("/info/pwd")
    Result updateUserAvatar(
            @RequestHeader(name = HEADER_NAME) String token,
            @Valid @RequestBody UpdatePwdDTO updatePwdDto,
            BindingResult result,
            HttpServletRequest request) {
        // 处理验证错误
        if (result.hasErrors()) return Result.fail(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        if (updatePwdDto.getNewPassword().equals(updatePwdDto.getOldPassword())) return Result.fail("新旧密码一致");
        return usersService.updatePwdByOldNewPwd(updatePwdDto, String.valueOf(request.getAttribute("userId")),token);
    }


    @ApiOperation(value = "修改基本信息", tags = "用户基本信息模块")
    @ApiImplicitParam(name = "Authorization", value = "用户token", required = true)
    @PostMapping("/info")
    Result updateUserInfo(
            @RequestHeader(name = HEADER_NAME) String token,
            @Valid @RequestBody UpdateUserInfoDTO updateUserInfoDTO,
            BindingResult result,
            HttpServletRequest request) {
        // 处理验证错误
        if (result.hasErrors()) return Result.fail(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        return usersService.updateUserInfo(updateUserInfoDTO, String.valueOf(request.getAttribute("userId")));
    }

    @ApiOperation(value = "用户头像更改", tags = "用户基本信息模块")
    @ApiParam(name = "file", value = "图片文件")
    @ApiImplicitParam(name = "Authorization", value = "用户token", required = true)
    @PostMapping("/info/avatar")
    Result updateUserAvatar(@RequestHeader(name = HEADER_NAME) String token,
                            @RequestParam(name = "file") MultipartFile file,
                            HttpServletRequest request) {
        return usersService.updateUserAvatar(file, String.valueOf(request.getAttribute("userId")));
    }


    @ApiOperation(value = "验证-用户名是否存在", tags = "用户基本信息模块")
    @ApiParam(name = "username", value = "用户名")
    @GetMapping("/exist")
    Result checkUserExisted(@RequestParam String username) {
        return usersService.checkUserIsExist(username);
    }


}
