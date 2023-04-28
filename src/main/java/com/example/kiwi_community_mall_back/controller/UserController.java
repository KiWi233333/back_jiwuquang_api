package com.example.kiwi_community_mall_back.controller;

import com.example.kiwi_community_mall_back.dto.user.LoginDTO;
import com.example.kiwi_community_mall_back.dto.user.LoginEmailCodeDTO;
import com.example.kiwi_community_mall_back.dto.user.LoginPhoneCodeDTO;
import com.example.kiwi_community_mall_back.dto.user.UserRegisterDTO;
import com.example.kiwi_community_mall_back.service.UserSaltService;
import com.example.kiwi_community_mall_back.service.UserService;
import com.example.kiwi_community_mall_back.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@Api(value = "用户模块")
@RestController("/user")
public class UserController {

    @Autowired
    UserService usersService;

    /**
     * 登录相关（登录、验证码）
     */

    @ApiOperation(value = "登录-密码", tags = "登录注册模块")
    @PostMapping(value = "/login/pwd")
    Result toLoginByPwd(@Valid @RequestBody LoginDTO loginDTO, BindingResult result) {
        if (result.hasErrors()) {
            // 处理校验错误信息
            return Result.fail(result.getFieldError().getDefaultMessage());
        } else {
            return usersService.toUserLoginByPwd(loginDTO.getUsername(), loginDTO.getPassword());
        }
    }


    @ApiOperation(value = "登录-手机", tags = "登录注册模块")
    @PostMapping(value = "/login/phone")
    Result toLoginPhoneByCode(@Valid @RequestBody LoginPhoneCodeDTO loginPhoneCodeDTO, BindingResult result) {
        if (result.hasErrors()) {
            // 处理校验错误信息
            return Result.fail(result.getFieldError().getDefaultMessage());
        } else {
            return usersService.toUserLoginByPhoneCode(loginPhoneCodeDTO.getPhone(), loginPhoneCodeDTO.getCode());
        }
    }


    @ApiOperation(value = "登录-邮箱", tags = "登录注册模块")
    @PostMapping(value = "/login/email")
    Result toLoginEmailByCode(@Valid @RequestBody LoginEmailCodeDTO loginEmailCodeDTO, BindingResult result) {
        if (result.hasErrors()) {
            // 处理校验错误信息
            return Result.fail(result.getFieldError().getDefaultMessage());
        } else {
            return usersService.toUserLoginByEmailCode(loginEmailCodeDTO.getEmail(), loginEmailCodeDTO.getCode());
        }
    }


    @ApiOperation(value = "登录-获取验证码", tags = "登录注册模块")
    @GetMapping(value = "/login/code/{key}")
    @ApiModelProperty(name = "key",value = "手机号/邮箱")
    @ApiParam(name = "type",value = "类型：0手机号/ 1邮箱")
    Result getLoginCode(@PathVariable String key,@RequestParam Integer type) {
        if (type==0) {// 手机号
            return usersService.getLoginCodeByPhone(key);
        }else {// 邮箱
            return usersService.getLoginCodeByEmail(key);
        }
    }


    @ApiOperation(value = "登录-退出登录", tags = "登录注册模块")
    @DeleteMapping(value = "/login/out/{phone}")
    @ApiParam(name = "phone",value = "手机号")
    Result getLoginCodeByPhone(@PathVariable String phone) {
        return usersService.loginOut(phone);
    }



    /**
     * 注册相关（注册、验证码）
     */

    @ApiOperation(value = "注册", tags = "登录注册模块")
    @PutMapping(value = "/register")
    Result toRegister(@Valid @RequestBody UserRegisterDTO userRegisterDTO, BindingResult res) {
        if (res.hasErrors()) {
            // 处理校验错误信息
            return Result.fail(res.getFieldError().getDefaultMessage());
        } else {
            return usersService.toRegister(userRegisterDTO);
        }
    }


    @ApiOperation(value = "注册-获取验证码", tags = "登录注册模块")
    @GetMapping(value = "/register/code/{key}")
    @ApiModelProperty(name = "key",value = "手机号/邮箱")
    @ApiParam(name = "type",value = "类型：0手机号/ 1邮箱")
    Result getRegisterCode(@PathVariable String key,@RequestParam Integer type) {
        if (type==0) {// 手机号
            return usersService.getRegisterCodeByPhone(key);
        }else {// 邮箱
            return usersService.getRegisterCodeByEmail(key);
        }
    }

    @ApiOperation(value = "验证-用户名是否存在", tags = "登录注册模块")
    @ApiParam(name = "username", value = "用户名")
    @GetMapping("/user/exist")
    Result checkUserExisted(@RequestParam String username) {
        return usersService.checkUserIsExist(username);
    }
}
