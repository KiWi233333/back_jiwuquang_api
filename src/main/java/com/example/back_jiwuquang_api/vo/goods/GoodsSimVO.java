package com.example.back_jiwuquang_api.vo.goods;

import com.example.back_jiwuquang_api.pojo.goods.Goods;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 商品简略表面信息
 *
 * @className: GoodsDTO
 * @author: Kiwi23333
 * @description: 商品简略表面信息
 * @date: 2023/5/11 21:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GoodsSimVO {


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

    @ApiModelProperty(value = "图片集")
    private String images;

    @ApiModelProperty(value = "视频")
    private String video;

    @ApiModelProperty(value = "是否新品")
    private Integer isNew;

    @ApiModelProperty(value = "保修时长")
    private Integer warrantyTime;

    @ApiModelProperty(value = "包换时长")
    private Integer refundTime;

    @ApiModelProperty(value = "发货省")
    private String province;

    @ApiModelProperty(value = "发货市")
    private String city;

    @ApiModelProperty(value = "发货区")
    private String district;


}
