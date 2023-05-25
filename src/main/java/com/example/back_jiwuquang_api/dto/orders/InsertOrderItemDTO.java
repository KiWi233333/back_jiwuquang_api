package com.example.back_jiwuquang_api.dto.orders;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * 提交订单商品项 DTO
 *
 * @className: SelectOrderDTO
 * @author: Kiwi23333
 * @description: 提交订单商品项 DTO
 * @date: 2023/5/22 19:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class InsertOrderItemDTO {

    @ApiModelProperty(value = "商品规格id", required = true)
    @NotNull(message = "商品规格id不能为空")
    String skuId;

    @ApiModelProperty(value = "数量", required = true)
    @NotNull(message = "数量不能为空")
    @Min(value = 1, message = "数量>0")
    Integer quantity;

    @ApiModelProperty(value = "活动id")
    String activityId;

    @ApiModelProperty(value = "店铺id")
    String shopId;

    @ApiModelProperty(value = "优惠卷id")
    String couponId;


}
