package com.example.kiwi_community_mall_back.controller;

import com.example.kiwi_community_mall_back.pojo.Users;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


@Api("用户模块")
@RestController("/users")
public class UsersController {


    @ApiOperation("用户登录-密码")
    @PostMapping(value = "/login/pwd")
    public Users toLoginByPwd(
            @RequestBody @ApiParam("用户名/手机号/邮箱") String username,
            @RequestBody @ApiParam("密码") String password) {
        Users users = new Users();
        users.setUsername(username);
        users.setPassword(password);

        return users;
    }

    @ApiOperation("用户登录-验证码")
    @PostMapping(value = "/login/code")
    public Users toLoginByCode(@RequestBody Users users) {
        return users;
    }
}
