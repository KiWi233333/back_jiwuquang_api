package com.example.back_jiwuquang_api.dto.shopcart;

import com.example.back_jiwuquang_api.pojo.shopcart.ShopCart;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 添加购物车类
 *
 * @className: AddShopCartDTO
 * @author: Kiwi23333
 * @description: AddShopCartDTO
 * @date: 2023/5/13 16:27
 */
@Data
public class AddShopCartDTO {
    @ApiModelProperty(value = "规格id", required = true)
    @NotBlank(message = "规格id不能为空")
    String skuId;

    @ApiModelProperty(value = "数量", required = true)
    @NotNull(message = "数量不能为空")
    private Integer quantity;

    @ApiModelProperty("活动id")
    private String activityId;

    @ApiModelProperty("店铺id")
    private String shopId;

    public static ShopCart toShopCart(AddShopCartDTO p) {
        return new ShopCart()
                .setSkuId(p.getSkuId())
                .setQuantity(p.getQuantity())
                .setShopId(p.getShopId())
                .setActivityId(p.getActivityId());
    }
}
