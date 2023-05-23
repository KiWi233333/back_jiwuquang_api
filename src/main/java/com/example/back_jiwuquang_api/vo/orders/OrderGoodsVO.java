package com.example.back_jiwuquang_api.vo.orders;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

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

    @ApiModelProperty("订单id")
    private String id;


    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品描述")
    private String description;


    @ApiModelProperty(value = "状态", notes = "0:待付款，1:已付款，2:已发货，3:待收货，4:已收货，5:已评价，6:已取消，7:已超时取消")
    private Integer status;

}