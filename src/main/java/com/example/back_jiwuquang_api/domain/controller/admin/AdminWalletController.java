package com.example.back_jiwuquang_api.domain.controller.admin;

import com.example.back_jiwuquang_api.domain.constant.JwtConstant;
import com.example.back_jiwuquang_api.dto.pay.RechargeComboDTO;
import com.example.back_jiwuquang_api.service.sys.UserWalletService;
import com.example.back_jiwuquang_api.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 钱包套餐模块
 *
 * @className: AdminWalletController
 * @author: Kiwi23333
 * @description: 钱包、充值、套餐模块
 * @date: 2023/5/5 15:08
 */
@Slf4j
@Api(value = "钱包套餐模块" )
@RestController
@RequestMapping("/admin/wallet")
public class AdminWalletController {
    @Autowired
    UserWalletService userWalletService;

    @ApiOperation(value = "添加充值套餐", tags = "钱包模块")
    @ApiImplicitParam(name = JwtConstant.HEADER_NAME, value = "管理员token", required = true)
    @PostMapping("/combo/one")
    Result addRechargeCombo(@RequestHeader(name= JwtConstant.HEADER_NAME) String token,
                            @Valid @RequestBody RechargeComboDTO rechargeComboDTO, BindingResult res) {
        if (res.hasErrors()) {
            return Result.fail(res.getFieldError().getDefaultMessage());// 验证格式错误！
        }
        return userWalletService.addRechargeCombo(rechargeComboDTO);
    }
}
