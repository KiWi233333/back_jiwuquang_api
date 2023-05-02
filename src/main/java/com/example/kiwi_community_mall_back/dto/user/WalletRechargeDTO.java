package com.example.kiwi_community_mall_back.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * 钱包充值dto
 *
 * @className: WalletRechargeDTO
 * @author: Kiwi23333
 * @description: 钱包充值dto
 * @date: 2023/4/30 22:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class WalletRechargeDTO {

    @ApiModelProperty(value = "充值类型", notes = "0 任意金额，1 套餐")
    @NotBlank(message = "充值类型不能为空")
    Integer type;

    @ApiModelProperty(value = "充值金额", notes = "type=0", required = false)
    BigDecimal amount;

    @ApiModelProperty(value = "套餐id", notes = "type=1", required = false)
    String id;


}
