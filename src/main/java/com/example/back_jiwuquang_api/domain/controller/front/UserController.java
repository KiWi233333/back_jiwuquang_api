package com.example.back_jiwuquang_api.domain.controller.front;

import com.example.back_jiwuquang_api.dto.sys.LoginDTO;
import com.example.back_jiwuquang_api.dto.sys.LoginEmailCodeDTO;
import com.example.back_jiwuquang_api.dto.sys.LoginPhoneCodeDTO;
import com.example.back_jiwuquang_api.dto.sys.UserRegisterDTO;
import com.example.back_jiwuquang_api.service.sys.UserService;
import com.example.back_jiwuquang_api.util.Result;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.example.back_jiwuquang_api.domain.constant.JwtConstant.HEADER_NAME;
import static com.example.back_jiwuquang_api.domain.constant.UserConstant.*;


/**
 * 用户模块
 */
@Slf4j
@Api(value = "用户模块", tags = {"用户模块"})
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService usersService;

    /**
     * 登陆注册模块
     */
    @ApiOperation(value = "登录-密码", tags = "登录注册模块")
    @PostMapping(value = "/login/pwd")
    Result toLoginByPwd(@Valid @RequestBody LoginDTO loginDTO, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            // 处理校验错误信息
            return Result.fail(result.getFieldError().getDefaultMessage());
        } else {
            return usersService.toUserLoginByPwd(loginDTO.getUsername(), loginDTO.getPassword(), 0, request.getHeader(USER_AGENT));
        }
    }


    @ApiOperation(value = "登录-手机", tags = "登录注册模块")
    @PostMapping(value = "/login/phone")
    Result toLoginPhoneByCode(@Valid @RequestBody LoginPhoneCodeDTO loginPhoneCodeDTO, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            // 处理校验错误信息
            return Result.fail(result.getFieldError().getDefaultMessage());
        } else {
            return usersService.toUserLoginByPhoneCode(loginPhoneCodeDTO.getPhone(), loginPhoneCodeDTO.getCode(), request.getHeader(USER_AGENT));
        }
    }


    @ApiOperation(value = "登录-邮箱", tags = "登录注册模块")
    @PostMapping(value = "/login/email")
    Result toLoginEmailByCode(@Valid @RequestBody LoginEmailCodeDTO loginEmailCodeDTO, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            // 处理校验错误信息
            return Result.fail(result.getFieldError().getDefaultMessage());
        } else {
            return usersService.toUserLoginByEmailCode(loginEmailCodeDTO.getEmail(), loginEmailCodeDTO.getCode(), request.getHeader(USER_AGENT));
        }
    }


    @ApiOperation(value = "登录-获取验证码", tags = "登录注册模块")
    @GetMapping(value = "/login/code/{key}")
    @ApiModelProperty(name = "key", value = "手机号/邮箱")
    @ApiParam(name = "type", value = "类型：0手机号/ 1邮箱")
    Result getLoginCode(@PathVariable String key, @RequestParam Integer type) {
        if (type == 0) {// 手机号
            return usersService.getLoginCodeByPhone(key);
        } else {// 邮箱
            return usersService.getLoginCodeByEmail(key);
        }
    }

    @ApiOperation(value = "登录-退出登录（当前）", tags = "登录注册模块")
    @ApiImplicitParam(name = "token", value = "用户 token", required = true)
    @DeleteMapping(value = "/exit")
    Result toLogout(@RequestHeader(name = HEADER_NAME) String token,
                    HttpServletRequest request) {
        return usersService.logoutOne(request.getAttribute(USER_ID_KEY).toString(), request.getHeader(USER_AGENT));
    }

    @ApiOperation(value = "登录-退出登录（所有）", tags = "登录注册模块")
    @ApiImplicitParam(name = "token", value = "用户 token", required = true)
    @DeleteMapping(value = "/exit/all")
    Result toLogoutAll(@RequestHeader(name = HEADER_NAME) String token,
                       HttpServletRequest request) {
        return usersService.logoutAll(request.getAttribute(USER_ID_KEY).toString());
    }

    /*************************** 注册相关（注册、验证码） **************************/
    @ApiOperation(value = "注册", tags = "登录注册模块")
    @PostMapping(value = "/register")
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
    @ApiModelProperty(name = "key", value = "手机号/邮箱")
    @ApiParam(name = "type", value = "类型：0手机号/ 1邮箱")
    Result getRegisterCode(@PathVariable String key, @RequestParam Integer type) {
        if (type == 0) {// 手机号
            return usersService.getRegisterCodeByPhone(key);
        } else {// 邮箱
            return usersService.getRegisterCodeByEmail(key);
        }
    }





}
