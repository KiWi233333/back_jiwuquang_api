package com.example.back_jiwuquang_api.dto.goods;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.back_jiwuquang_api.pojo.goods.Goods;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品表单信息参数类
 *
 * @className: GoodsDTO
 * @author: Kiwi23333
 * @description: 商品表单信息参数类
 * @date: 2023/5/11 21:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GoodsDTO {


    @ApiModelProperty(value = "商品名称",required = true)
    @NotBlank(message = "商品名称不能为空")
    private String name;

    @ApiModelProperty(value = "商品详情")
    private String description;

    @ApiModelProperty(value = "销售价",required = true)
    @NotNull(message = "销售价不能为空")
    private BigDecimal price;

    @ApiModelProperty(value = "原价",required = true)
    @NotNull(message = "原价不能为空")
    private BigDecimal costPrice;

    @ApiModelProperty(value = "分类id",required = true)
    @NotBlank(message = "分类id不能为空")
    private String categoryId;

    @ApiModelProperty(value = "图片集")
    private String images;

    @ApiModelProperty(value = "视频")
    private String video;

    @ApiModelProperty(value = "是否上架")
    private Integer isShow;

    @ApiModelProperty(value = "是否新品")
    private Integer isNew;


    @ApiModelProperty(value = "保修时长")
    private String warrantyTime;

    @ApiModelProperty(value = "包换时长")
    private String refundTime;

    @ApiModelProperty(value = "发货省")
    private String province;

    @ApiModelProperty(value = "发货市")
    private String city;

    @ApiModelProperty(value = "发货区")
    private String district;


    public static Goods toGoods (GoodsDTO goodsDTO) {
        return new Goods().setName(goodsDTO.getName())
                .setDescription(goodsDTO.getDescription())
                .setPrice(goodsDTO.getPrice())
                .setCostPrice(goodsDTO.getCostPrice())
                .setCategoryId(goodsDTO.getCategoryId())
                .setImages(goodsDTO.getImages())
                .setVideo(goodsDTO.getVideo())
                .setIsShow(goodsDTO.getIsShow())
                .setIsNew(goodsDTO.getIsNew())
                .setProvince(goodsDTO.getProvince())
                .setCity(goodsDTO.getCity())
                .setDistrict(goodsDTO.getDistrict());
    }
}
