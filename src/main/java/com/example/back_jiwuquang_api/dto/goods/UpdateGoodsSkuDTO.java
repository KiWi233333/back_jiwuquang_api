package com.example.back_jiwuquang_api.dto.goods;

import com.example.back_jiwuquang_api.pojo.goods.GoodsSku;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 商品规格表单信息
 *
 * @className: GoodsDTO
 * @author: Kiwi23333
 * @description: 商品规格表单信息
 * @date: 2023/5/11 21:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UpdateGoodsSkuDTO {

    @ApiModelProperty("规格")
    private String size;

    @ApiModelProperty("颜色")
    private String color;

    @ApiModelProperty("套餐")
    private String combo;

    @ApiModelProperty("库存")
    private Integer stock;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("原价")
    private BigDecimal costPrice;

    @ApiModelProperty("图片地址")
    private String image;

    @ApiModelProperty("是否上架")
    private Integer isShow;


    /**
     * GoodsSkuDTO转为 GoodsSku实体类
     *
     * @param dto GoodsSkuDTO
     * @return GoodsSku
     */
    public static GoodsSku toGoodsSku(UpdateGoodsSkuDTO dto) {
        return new GoodsSku()
                .setSize(dto.getSize())
                .setColor(dto.getColor())
                .setCombo(dto.getCombo())
                .setStock(dto.getStock())
                .setPrice(dto.getPrice())
                .setCostPrice(dto.getCostPrice())
                .setImage(dto.getImage())
                .setIsShow(dto.getIsShow());
    }
}
