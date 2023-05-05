package com.example.back_jiwuquang_api.core.controller.front;

import com.example.back_jiwuquang_api.service.sys.UserService;
import com.example.back_jiwuquang_api.util.Result;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 基本信息模块
 *
 * @className: UserInfoController
 * @author: Kiwi23333
 * @description: 基本信息模块
 * @date: 2023/5/1 2:09
 */
@Slf4j
@Api(value = "用户模块",tags = {"用户模块"})
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    UserService usersService;
    /**
     * 用户基本信息（增删查改）
     */
    @ApiOperation(value = "验证-用户名是否存在", tags = "用户基本信息模块")
    @ApiParam(name = "username", value = "用户名")
    @GetMapping("/exist")
    Result checkUserExisted(@RequestParam String username) {
        return usersService.checkUserIsExist(username);
    }


    @ApiOperation(value = "获取用户信息", tags = "用户基本信息模块")
    @ApiImplicitParam(name = "Authorization", value = "用户token", required = true)
    @GetMapping("/info")
    Result getUserInfo(@RequestHeader("Authorization") String token, HttpServletRequest request) {
        if (StringUtil.isNullOrEmpty(token)) return Result.fail("未携带用户token！");
        // 获取拦截请求后获取的id
        return usersService.getUserInfoById(String.valueOf(request.getAttribute("userId")));
    }
}
