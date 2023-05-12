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
public class GoodsSkuDTO {

    @ApiModelProperty(value = "商品id", required = true)
    @NotBlank(message = "商品id不能为空")
    private String goodsId;

    @ApiModelProperty(value = "规格", required = true)
    @NotBlank(message = "规格不能为空")
    private String size;

    @ApiModelProperty("颜色")
    private String color;

    @ApiModelProperty("套餐")
    private String combo;

    @ApiModelProperty(value = "库存", required = true)
    @NotNull(message = "库存不能为空")
    private Integer stock;

    @ApiModelProperty(value = "价格", required = true)
    @NotNull(message = "价格不能为空")
    private BigDecimal price;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty(value = "原价", required = true)
    @NotNull(message = "原价不能为空")
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
    public static GoodsSku toGoodsSku(GoodsSkuDTO dto) {
        return new GoodsSku()
                .setGoodsId(dto.getGoodsId())
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
