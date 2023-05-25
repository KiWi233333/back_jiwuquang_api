package com.example.back_jiwuquang_api.vo.orders;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;

/**
 * 订单商品VO
 *
 * @className: OrderGoodsVO
 * @author: Kiwi23333
 * @description: 订单商品VO
 * @date: 2023/5/17 16:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@FieldNameConstants
public class OrderGoodsVO {

    @ApiModelProperty("商品id")
    private String id;


    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品描述")
    private String description;

    @ApiModelProperty("运费")
    private BigDecimal postage;


}