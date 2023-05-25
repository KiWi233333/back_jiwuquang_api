package com.example.back_jiwuquang_api.dto.goods;

import com.example.back_jiwuquang_api.pojo.goods.Goods;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * 修改商品信息参数类
 *
 * @className: UpdateGoodsDTO
 * @author: Kiwi23333
 * @description: 修改商品信息参数类
 * @date: 2023/5/11 21:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UpdateGoodsDTO {

    @ApiModelProperty(value = "商品id",required = true)
    @NotBlank(message = "商品id不能为空")
    private String id;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "商品详情")
    private String description;

    @ApiModelProperty(value = "销售价")
    private BigDecimal price;

    @ApiModelProperty(value = "原价")
    private BigDecimal costPrice;

    @ApiModelProperty(value = "运费")
    private BigDecimal postage;

    @ApiModelProperty(value = "分类id")
    private String categoryId;

    @ApiModelProperty(value = "图片集（,分隔）")
    private String images;

    @ApiModelProperty(value = "视频")
    private String video;

    @ApiModelProperty(value = "是否上架")
    private Integer isShow;

    @ApiModelProperty(value = "是否新品")
    private Integer isNew;


    @ApiModelProperty(value = "发货省")
//    @Pattern(regexp = "省$", message = "省份格式不正确！")
    private String province;

    @ApiModelProperty(value = "发货市")
//    @Pattern(regexp = "市$", message = "市格式不正确！")
    private String city;

    @ApiModelProperty(value = "发货区")
//    @Pattern(regexp = "区$", message = "区格式不正确！")
    private String district;
    @ApiModelProperty(value = "保修时间")
    private String warrantyTime;

    @ApiModelProperty(value = "包换时间")
    private String refundTime;


    public static Goods toGoods(UpdateGoodsDTO p) {
        return new Goods().setId(p.getId())
                .setName(p.getName())
                .setDescription(p.getDescription())
                .setPrice(p.getPrice())
                .setCostPrice(p.getCostPrice())
                .setPostage(p.getPostage())
                .setCategoryId(p.getCategoryId())
                .setImages(p.getImages())
                .setVideo(p.getVideo())
                .setIsShow(p.getIsShow())
                .setIsNew(p.getIsNew())
                .setProvince(p.getProvince())
                .setCity(p.getCity())
                .setDistrict(p.getDistrict());
    }
}
