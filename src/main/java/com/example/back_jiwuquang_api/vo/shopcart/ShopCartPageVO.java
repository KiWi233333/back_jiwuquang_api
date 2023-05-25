package com.example.back_jiwuquang_api.vo.shopcart;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 购物车分页信息
 *
 * @className: ShopCartVO
 * @author: Kiwi23333
 * @description: ShopCartPageVO
 * @date: 2023/5/13 13:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ShopCartPageVO {

    @ApiModelProperty("购物车id")
    private String id;

    @ApiModelProperty("规格id")
    private String skuId;

    /** 规格表 **/
    @ApiModelProperty("商品id")
    private String goodsId;

    @ApiModelProperty("规格")
    private String size;

    @ApiModelProperty("颜色")
    private String color;

    @ApiModelProperty("版本")
    private String combo;

    @ApiModelProperty("库存")
    private Integer stock;

    @ApiModelProperty("销售价")
    private BigDecimal price;

    @ApiModelProperty("原价")
    private BigDecimal costPrice;

    @ApiModelProperty("图片")
    private String image;
    @ApiModelProperty("描述")
    private String description;

    /** 购物车表 **/
    @ApiModelProperty("数量")
    private Integer quantity;

    @ApiModelProperty("活动id")
    private String activityId;

    @ApiModelProperty("店铺id")
    private String shopId;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;




}
