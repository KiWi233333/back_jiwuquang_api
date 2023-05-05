package com.example.back_jiwuquang_api.core.controller.admin;

import com.example.back_jiwuquang_api.dto.sys.LoginDTO;
import com.example.back_jiwuquang_api.service.sys.UserService;
import com.example.back_jiwuquang_api.util.Result;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.back_jiwuquang_api.core.constant.JwtConstant.HEADER_NAME;


/**
 * 管理员模块
 *
 */
@Slf4j
@Api(value = "管理员模块",tags = {"管理员模块"})
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
    Result toLoginByPwd(@Valid @RequestBody LoginDTO loginDTO, BindingResult result) {
        if (result.hasErrors()) {
            // 处理校验错误信息
            return Result.fail(result.getFieldError().getDefaultMessage());
        } else {
            // 后台类用户
            return usersService.toUserLoginByPwd(loginDTO.getUsername(), loginDTO.getPassword(),1);
        }
    }

    @ApiOperation(value = "登录-退出登录", tags = "登录模块")
    @ApiImplicitParam(name = "token", value = "管理员 token", required = true)
    @DeleteMapping(value = "/exit")
    Result getLoginCodeByPhone(@RequestHeader(name = HEADER_NAME) String token) {
        return usersService.loginOut(token);
    }

//    /**
//     * 注册相关（注册、验证码）
//     */
//    @ApiOperation(value = "注册", tags = "登录注册模块")
//    @PutMapping(value = "/register")
//    Result toRegister(@Valid @RequestBody UserRegisterDTO userRegisterDTO, BindingResult res) {
//        if (res.hasErrors()) {
//            // 处理校验错误信息
//            return Result.fail(res.getFieldError().getDefaultMessage());
//        } else {
//            return usersService.toRegister(userRegisterDTO);
//        }
//    }
//
//
//    @ApiOperation(value = "注册-获取验证码", tags = "登录注册模块")
//    @GetMapping(value = "/register/code/{key}")
//    @ApiModelProperty(name = "key", value = "手机号/邮箱")
//    @ApiParam(name = "type", value = "类型：0手机号/ 1邮箱")
//    Result getRegisterCode(@PathVariable String key, @RequestParam Integer type) {
//        if (type == 0) {// 手机号
//            return usersService.getRegisterCodeByPhone(key);
//        } else {// 邮箱
//            return usersService.getRegisterCodeByEmail(key);
//        }
//    }



}
