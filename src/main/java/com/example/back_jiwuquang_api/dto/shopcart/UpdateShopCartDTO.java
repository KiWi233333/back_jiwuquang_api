package com.example.back_jiwuquang_api.dto.shopcart;

import com.example.back_jiwuquang_api.pojo.shopcart.ShopCart;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 修改购物车类
 *
 * @className: UpdateShopCartDTO
 * @author: Kiwi23333
 * @description: UpdateShopCartDTO
 * @date: 2023/5/13 16:27
 */
@Data
public class UpdateShopCartDTO {

    @ApiModelProperty(value = "规格id")
    String skuId;

    @ApiModelProperty(value = "数量")
    private Integer quantity;

    public static ShopCart toShopCart(UpdateShopCartDTO p) {
        return new ShopCart()
                .setSkuId(p.getSkuId())
                .setQuantity(p.getQuantity());
    }
}
