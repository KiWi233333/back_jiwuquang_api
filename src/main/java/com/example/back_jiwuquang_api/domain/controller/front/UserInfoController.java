package com.example.back_jiwuquang_api.domain.controller.front;

import com.example.back_jiwuquang_api.dto.goods.GoodsPageDTO;
import com.example.back_jiwuquang_api.dto.other.IdsList;
import com.example.back_jiwuquang_api.dto.sys.*;
import com.example.back_jiwuquang_api.service.sys.UserAddressService;
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
import static com.example.back_jiwuquang_api.domain.constant.UserConstant.USER_ID_KEY;

/**
 * 用户模块
 * #基本信息模块
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
    @ApiImplicitParam(name = HEADER_NAME, value = "用户token", required = true)
    @GetMapping("/info")
    Result getUserInfo(@RequestHeader(HEADER_NAME) String token, HttpServletRequest request) {
        // 获取拦截请求后获取的id
        return usersService.getUserInfoById(String.valueOf(request.getAttribute(USER_ID_KEY)));
    }

    @ApiOperation(value = "修改密码", tags = "用户基本信息模块")
    @ApiImplicitParam(name = HEADER_NAME, value = "用户token", required = true)
    @PutMapping("/info/pwd")
    Result updateUserAvatar(
            @RequestHeader(name = HEADER_NAME) String token,
            @Valid @RequestBody UpdatePwdDTO updatePwdDto,
            BindingResult result,
            HttpServletRequest request) {
        // 处理验证错误
        if (result.hasErrors()) return Result.fail(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        if (updatePwdDto.getNewPassword().equals(updatePwdDto.getOldPassword())) return Result.fail("新旧密码一致");
        return usersService.updatePwdByOldNewPwd(updatePwdDto, String.valueOf(request.getAttribute(USER_ID_KEY)), token);
    }


    @ApiOperation(value = "修改基本信息", tags = "用户基本信息模块")
    @ApiImplicitParam(name = HEADER_NAME, value = "用户token", required = true)
    @PutMapping("/info")
    Result updateUserInfo(
            @RequestHeader(name = HEADER_NAME) String token,
            @Valid @RequestBody UpdateUserInfoDTO updateUserInfoDTO,
            BindingResult result,
            HttpServletRequest request) {
        // 处理验证错误
        if (result.hasErrors()) return Result.fail(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        return usersService.updateUserInfo(updateUserInfoDTO, String.valueOf(request.getAttribute(USER_ID_KEY)));
    }


    @ApiOperation(value = "获取新手机/邮箱验证码", tags = "用户基本信息模块")
    @GetMapping(value = "/code/{key}")
    @ApiImplicitParam(name = HEADER_NAME, value = "用户token", required = true)
    @ApiModelProperty(name = "key", value = "手机号/邮箱")
    @ApiParam(name = "type", value = "类型：0手机号/ 1邮箱")
    Result getUpdatePhoneOrEmailCode(@RequestHeader(name = HEADER_NAME) String token,
                                     @PathVariable String key, @RequestParam Integer type) {
        return usersService.sendUpdateCode(key, type);
    }

    @ApiOperation(value = "更换手机号", tags = "用户基本信息模块")
    @ApiImplicitParam(name = HEADER_NAME, value = "用户token", required = true)
    @PutMapping("/info/phone")
    Result updateUserPhone(
            @RequestHeader(name = HEADER_NAME) String token,
            @Valid @RequestBody UpdatePhoneDTO updatePhoneDTO,
            BindingResult result,
            HttpServletRequest request) {
        // 处理验证错误
        if (result.hasErrors()) return Result.fail(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        // 业务
        return usersService.updateUserPhone(updatePhoneDTO, String.valueOf(request.getAttribute(USER_ID_KEY)));
    }

    @ApiOperation(value = "更换邮箱", tags = "用户基本信息模块")
    @ApiImplicitParam(name = HEADER_NAME, value = "用户token", required = true)
    @PutMapping("/info/email")
    Result updateUserEmail(
            @RequestHeader(name = HEADER_NAME) String token,
            @Valid @RequestBody UpdateEmailDTO updateEmailDTO,
            BindingResult result,
            HttpServletRequest request) {
        // 处理验证错误
        if (result.hasErrors()) return Result.fail(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        // 业务
        return usersService.updateUserEmail(updateEmailDTO, String.valueOf(request.getAttribute(USER_ID_KEY)));
    }


    @ApiOperation(value = "用户头像更改", tags = "用户基本信息模块")
    @ApiParam(name = "file", value = "图片文件")
    @ApiImplicitParam(name = HEADER_NAME, value = "用户token", required = true)
    @PutMapping("/info/avatar")
    Result updateUserAvatar(@RequestHeader(name = HEADER_NAME) String token,
                            @RequestParam(name = "file") MultipartFile file,
                            HttpServletRequest request) {
        return usersService.updateUserAvatar(file, String.valueOf(request.getAttribute(USER_ID_KEY)));
    }


    @ApiOperation(value = "验证-用户名是否存在", tags = "用户基本信息模块")
    @ApiParam(name = "username", value = "用户名")
    @GetMapping("/exist")
    Result checkUserExisted(@RequestParam String username) {
        return usersService.checkUserIsExist(username);
    }


    /*********************** 用户地址 ****************************    **/
    @Autowired
    UserAddressService userAddressService;

    @ApiOperation(value = "获取用户地址", tags = "用户地址模块")
    @ApiImplicitParam(name = "token", value = "用户 token", required = true)
    @GetMapping("/address")
    Result getUserAddressPage(@RequestHeader(name = HEADER_NAME) String token,
                              HttpServletRequest request) {
        String userId = String.valueOf(request.getAttribute(USER_ID_KEY));
        // 查询
        return userAddressService.getUserAddressByUserId(userId);
    }

    @ApiOperation(value = "添加收货地址", tags = "用户地址模块")
    @ApiImplicitParam(name = "token", value = "用户 token", required = true)
    @PostMapping("/address")
    Result addUserAddress(@Valid @RequestBody UserAddressDTO userAddressDTO,
                          @RequestHeader(name = HEADER_NAME) String token,
                          BindingResult res,
                          HttpServletRequest request) {
        if (res.hasErrors()) {
            return Result.fail(res.getFieldError().getDefaultMessage());
        }
        String userId = String.valueOf(request.getAttribute(USER_ID_KEY));
        // 添加
        return userAddressService.addUserAddress(userAddressDTO, userId);
    }

    @ApiOperation(value = "修改收货地址", tags = "用户地址模块")
    @ApiImplicitParam(name = "token", value = "用户 token", required = true)
    @PutMapping("/address/{id}")
    Result updateAddressById(@RequestHeader(name = HEADER_NAME) String token,
                             @Valid @RequestBody UserAddressDTO userAddressDTO,
                             @PathVariable String id,
                             BindingResult res,
                             HttpServletRequest request) {
        if (res.hasErrors()) {
            return Result.fail(res.getFieldError().getDefaultMessage());
        }
        String userId = String.valueOf(request.getAttribute(USER_ID_KEY));
        // 修改
        return userAddressService.updateAddressById(userAddressDTO, id, userId);
    }

    @ApiOperation(value = "删除收货地址（单个）", tags = "用户地址模块")
    @ApiImplicitParam(name = "token", value = "用户 token", required = true)
    @DeleteMapping("/address/one/{id}")
    Result deleteAddressById(@RequestHeader(name = HEADER_NAME) String token,
                             @PathVariable String id,
                             HttpServletRequest request) {
        String userId = String.valueOf(request.getAttribute(USER_ID_KEY));
        // 修改
        return userAddressService.deleteAddressById(id, userId);
    }

    @ApiOperation(value = "删除收货地址（批量）", tags = "用户地址模块")
    @ApiImplicitParam(name = "token", value = "用户 token", required = true)
    @DeleteMapping("/address/some")
    Result deleteAddressById(@RequestHeader(name = HEADER_NAME) String token,
                             @RequestBody IdsList idsList,
                             HttpServletRequest request) {
        String userId = String.valueOf(request.getAttribute(USER_ID_KEY));
        // 修改
        return userAddressService.deleteAddressByIds(idsList.getIds(), userId);
    }

    @ApiOperation(value = "设为默认地址", tags = "用户地址模块")
    @ApiImplicitParam(name = "token", value = "用户 token", required = true)
    @PutMapping("/address/default/{id}")
    Result setAddressDefault(@RequestHeader(name = HEADER_NAME) String token,
                             @RequestParam int isDefault,
                             @PathVariable String id,
                             HttpServletRequest request) {
        String userId = String.valueOf(request.getAttribute(USER_ID_KEY));
        if (isDefault != 0 && isDefault != 1) {
            return Result.fail("默认值为0或1");
        }
        // 修改默认地址
        return userAddressService.updateAddressDefault(id, userId, isDefault);
    }


    /*************************** 账号安全和设备 **************************/

    /**
     * 获取登录设备
     */
    @ApiOperation(value = "获取登录设备", tags = "账号安全和设备模块")
    @ApiImplicitParam(name = "token", value = "用户 token", required = true)
    @GetMapping(value = "/info/device")
    Result getUserLoginDevice(@RequestHeader(name = HEADER_NAME) String token,
                              HttpServletRequest request) {
        String userId = (String) request.getAttribute(USER_ID_KEY);
        return usersService.getUserLoginDevice(userId);
    }


}
