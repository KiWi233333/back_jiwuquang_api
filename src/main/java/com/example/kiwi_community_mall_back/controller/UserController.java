package com.example.kiwi_community_mall_back.controller;

import com.example.kiwi_community_mall_back.dto.user.LoginCodeDTO;
import com.example.kiwi_community_mall_back.dto.user.LoginDTO;
import com.example.kiwi_community_mall_back.dto.user.UserRegisterDTO;
import com.example.kiwi_community_mall_back.service.UserService;
import com.example.kiwi_community_mall_back.util.Result;
import com.example.kiwi_community_mall_back.util.interfaces.Phone;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@Api("用户模块")
@RestController("/user")
public class UserController {

    @Autowired
    UserService usersService;

    /**
     * 登录相关（登录、验证码）
     */
    @ApiOperation(value = "登录-密码", tags = "登录")
    @PostMapping(value = "/login/pwd")
    Result toLoginByPwd(@Valid @RequestBody LoginDTO loginDTO, BindingResult result) {
        if (result.hasErrors()) {
            // 处理校验错误信息
            return Result.fail(result.getFieldError().getDefaultMessage());
        } else {
            return usersService.toUserLoginByPwd(loginDTO.getUsername(), loginDTO.getPassword());
        }
    }

    @ApiOperation(value = "登录-验证码", tags = "登录")
    @PostMapping(value = "/login/code")
    Result toLoginByCode(@Valid @RequestBody LoginCodeDTO loginCodeDTO, BindingResult result) {
        if (result.hasErrors()) {
            // 处理校验错误信息
            return Result.fail(result.getFieldError().getDefaultMessage());
        } else {
            return usersService.toUserLoginByCode(loginCodeDTO.getPhone(), loginCodeDTO.getCode());
        }
    }

    @ApiOperation(value = "登录-获取手机验证码", tags = "登录")
    @GetMapping(value = "/login/{phone}")
    @Valid
    Result getLoginCode( @PathVariable String phone) {
        return usersService.getLoginCodeByPhone(phone);
    }


    /**
     * 注册相关（注册、验证码）
     */
    @ApiOperation(value = "注册")
    @PutMapping(value = "/register")
    Result toRegister(@Valid @RequestBody UserRegisterDTO userRegisterDTO, BindingResult res) {
        if (res.hasErrors()) {
            // 处理校验错误信息
            return Result.fail(res.getFieldError().getDefaultMessage());
        } else {
            return usersService.toRegister(userRegisterDTO);
        }
    }

    @ApiOperation(value = "注册-获取手机验证码")
    @ApiParam(name = "email", value = "手机号")
    @GetMapping(value = "/register/{phone}")
    Result getRegisterByPhone(@PathVariable String phone) {
        return usersService.getRegisterByPhone(phone);
    }

    @ApiOperation(value = "注册-获取邮箱验证码")
    @GetMapping(value = "/register/{email}")
    @ApiParam(name = "email", value = "邮箱")
    Result getRegisterByEmail(@PathVariable String email) {
        return usersService.getRegisterByEmail(email);
    }


    @ApiOperation(value = "验证-用户是否存在")
    @ApiParam(name = "username", value = "用户名")
    @GetMapping("/user/exist")
    Result checkUserExisted(@RequestParam String username) {
        return usersService.checkUserIsExist(username);
    }
}
