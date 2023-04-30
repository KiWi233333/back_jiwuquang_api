package com.example.kiwi_community_mall_back.controller;

import com.example.kiwi_community_mall_back.dto.user.WalletRechargeDTO;
import com.example.kiwi_community_mall_back.service.UserWalletService;
import com.example.kiwi_community_mall_back.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 用户模块
 * <p>
 * 钱包模块控制器
 *
 * @className: UserWalletController
 * @author: Kiwi23333
 * @description: 钱包模块
 * @date: 2023/4/30 22:27
 */
@Slf4j
@Api(value = "用户模块", tags = {"钱包模块"})
@RestController
@RequestMapping("/user")
public class UserWalletController {

    @Autowired
    UserWalletService userWalletService;


    @ApiOperation(value = "获取充值套餐", tags = "钱包模块")
    @GetMapping("/wallet/combo")
    Result getAllRechargeCombo() {
        return userWalletService.getAllRechargeCombo();
    }

    @ApiOperation(value = "获取钱包信息", tags = "钱包模块")
    @ApiImplicitParam(name = "Authorization", value = "用户token", required = true)
    @GetMapping("/wallet")
    Result getUserPurseInfo(@RequestHeader("Authorization") String token, HttpServletRequest request) {
        return userWalletService.getUserWalletById(String.valueOf(request.getAttribute("userId")));
    }


    @ApiOperation(value = "钱包充值", tags = "钱包模块")
    @ApiImplicitParam(name = "Authorization", value = "用户token", required = true)
    @PostMapping("/wallet")
    Result toRechargeByCombo(@RequestHeader("Authorization") String token,
                             @RequestBody WalletRechargeDTO walletRechargeDTO, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            // 处理校验错误信息
            return Result.fail(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        } else {
            return userWalletService.toRechargeByUserId(walletRechargeDTO, String.valueOf(request.getAttribute("userId")));
        }
    }


}
