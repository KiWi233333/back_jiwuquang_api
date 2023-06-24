package com.example.back_jiwuquang_api.domain.controller.admin;

import com.example.back_jiwuquang_api.dto.sys.LoginDTO;
import com.example.back_jiwuquang_api.dto.sys.UpdateUserAllInfoDTO;
import com.example.back_jiwuquang_api.dto.sys.UpdateUserInfoDTO;
import com.example.back_jiwuquang_api.dto.sys.UserInfoPageDTO;
import com.example.back_jiwuquang_api.service.sys.UserService;
import com.example.back_jiwuquang_api.util.RedisUtil;
import com.example.back_jiwuquang_api.util.Result;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.Objects;

import static com.example.back_jiwuquang_api.domain.constant.JwtConstant.HEADER_NAME;
import static com.example.back_jiwuquang_api.domain.constant.UserConstant.USER_AGENT;
import static com.example.back_jiwuquang_api.domain.constant.UserConstant.USER_ID_KEY;


/**
 * 管理员模块
 * #登录添加模块
 */
@Slf4j
@Api(value = "管理员模块", tags = {"管理员模块"})
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService usersService;

    /**
     * 登陆模块
     */
    @ApiOperation(value = "登录-密码", tags = "登录注册模块")
    @PostMapping(value = "/login/pwd")
    Result toLoginByPwd(@Valid @RequestBody LoginDTO loginDTO, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            // 处理校验错误信息
            return Result.fail(result.getFieldError().getDefaultMessage());
        } else {
            // 后台类用户
            return usersService.toUserLoginByPwd(loginDTO.getUsername(), loginDTO.getPassword(), 1, request.getHeader(USER_AGENT));
        }
    }

    @ApiOperation(value = "登录-退出登录", tags = "登录模块")
    @ApiImplicitParam(name = "token", value = "管理员 token", required = true)
    @DeleteMapping(value = "/exit")
    Result toLogout(@RequestHeader(name = HEADER_NAME) String token, HttpServletRequest request) {
        return usersService.logoutOne(request.getAttribute(USER_ID_KEY).toString(), request.getHeader(USER_AGENT));
    }


    @ApiOperation(value = "分页获取用户列表", tags = "用户模块")
    @ApiImplicitParam(name = "token", value = "管理员 token", required = true)
    @PostMapping(value = "/user/{page}/{size}")
    Result getUserInfoPage(@RequestHeader(name = HEADER_NAME) String token,
                           @ApiParam("页码") @PathVariable Integer page,
                           @ApiParam("每页个数") @PathVariable Integer size,
                           @Valid @RequestBody UserInfoPageDTO userInfoPageDTO) {
        return usersService.getUserInfoPage(page, size, userInfoPageDTO);
    }

    @ApiOperation(value = "修改用户信息", tags = "用户信息模块")
    @ApiImplicitParam(name = "token", value = "管理员 token", required = true)
    @PutMapping("/user/info")
    Result updateUserInfo(
            @RequestHeader(name = HEADER_NAME) String token,
            @Valid @RequestBody UpdateUserAllInfoDTO updateUserAllInfoDTO,
            BindingResult result,
            HttpServletRequest request) {
        // 处理验证错误
        if (result.hasErrors()) return Result.fail(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        return usersService.updateUserAllInfo(updateUserAllInfoDTO, request.getAttribute(USER_ID_KEY).toString());
    }

    @ApiOperation(value = "用户禁用", tags = "用户模块")
    @ApiImplicitParam(name = "token", value = "管理员 token", required = true)
    @DeleteMapping(value = "/user/disable/{userId}")
    Result toUserDisable(@RequestHeader(name = HEADER_NAME) String token, @PathVariable String userId, @ApiParam("是否禁用") @RequestParam Integer disable) {
        return usersService.toUserDisableToggle(userId, disable);
    }

    @ApiOperation(value = "用户强制下线", tags = "用户模块")
    @ApiImplicitParam(name = "token", value = "管理员 token", required = true)
    @DeleteMapping(value = "/user/logout/{userId}")
    Result toUserLogout(@RequestHeader(name = HEADER_NAME) String token, @PathVariable String userId) {
        return usersService.loginOutById(userId);
    }


    @Autowired
    RedisUtil redisUtil;

    @ApiOperation(value = "清除缓存", tags = "系统模块")
    @ApiImplicitParam(name = "token", value = "管理员 token", required = true)
    @DeleteMapping(value = "/redis/cache")
    Result clearRedisCache(@RequestHeader(name = HEADER_NAME) String token) {
        try {
            redisUtil.flushDb();
            return Result.ok("删除成功！", null);
        } catch (Exception e) {
            return Result.fail("删除失败！");
        }
    }


}
