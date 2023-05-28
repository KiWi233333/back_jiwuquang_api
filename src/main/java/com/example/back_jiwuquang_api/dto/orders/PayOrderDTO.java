package com.example.back_jiwuquang_api.dto.orders;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;


/**
 * 支付订单 DTO
 *
 * @className: PayOrderDTO
 * @author: Kiwi23333
 * @description: 支付订单 DTO
 * @date: 2023/5/22 19:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PayOrderDTO {

    @ApiModelProperty(value = "支付方式",notes = "默认0钱包支付 ", required = true)
    @NotNull(message = "支付方式不能为空")
    Integer type;

    @ApiModelProperty(value = "积分抵扣")
    Integer points;

    @ApiModelProperty(value = "代金卷")
    String voucherId;


}
