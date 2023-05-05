package com.example.back_jiwuquang_api.pojo.goods;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品模块
 * ## 商品规格项
 *
 * @className: GoodsSku
 * @author: Kiwi23333
 * @description: 商品模块
 * @date: 2023/5/5 22:29
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("goods_sku")
public class GoodsSku {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField(value = "goods_id")
    private String goodsId;

    private String size;

    private String color;

    private String combo;

    private Integer stock;

    private BigDecimal price;

    private String description;

    @TableField(value = "cost_price")
    private BigDecimal costPrice;

    private String image;

    @TableField(value = "is_show")
    private Integer isShow;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}