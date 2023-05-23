package com.example.back_jiwuquang_api.vo.orders;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单商品VO
 *
 * @className: OrderInfoVO
 * @author: Kiwi23333
 * @description: 订单商品VO
 * @date: 2023/5/17 16:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@FieldNameConstants
public class OrderGoodsSkuVO {

    @ApiModelProperty("属性id")
    private String id;

    @ApiModelProperty("属性图片")
    private String image;

    @ApiModelProperty("尺寸")
    private String size;

    @ApiModelProperty("颜色")
    private String color;

    @ApiModelProperty("版本")
    private String combo;

    @ApiModelProperty("库存")
    private Integer stock;

    @ApiModelProperty("原价")
    private BigDecimal price;

    @ApiModelProperty("折扣价")
    private BigDecimal costPrice;

}