package com.example.back_jiwuquang_api.vo.orders;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单商品VO
 *
 * @className: OrderInfoVO
 * @author: Kiwi23333
 * @description: 订单商品VO
 * @date: 2023/5/17 16:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@FieldNameConstants
public class RefundOrdersVO {

    @ApiModelProperty("订单id")
    private String id;
    @ApiModelProperty("优惠卷ids")
    private List<String> couponIds;

    @ApiModelProperty(value = "代金卷id")
    String voucherId;

    @ApiModelProperty("积分")
    private Integer points;

    @ApiModelProperty("退还金额")
    private BigDecimal refundPrice;

}