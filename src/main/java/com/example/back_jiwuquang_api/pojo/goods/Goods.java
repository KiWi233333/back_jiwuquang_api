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
 * 商品表实体类
 *
 * @className: Goods
 * @author: Kiwi23333
 * @description: 商品表实体类
 * @date: 2023/5/1 2:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("goods")
public class Goods {


    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String name;

    private String description;

    private BigDecimal price;

    @TableField("cost_price")
    private BigDecimal costPrice;

    @TableField("category_id")
    private String categoryId;

    private String images;

    private String video;

    private BigDecimal postage;

    /**
     * 省份
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;

    @TableField("warranty_time")
    private Integer warrantyTime;

    @TableField("refund_time")
    private Integer refundTime;

    @TableField("is_show")
    private Integer isShow;

    @TableField("is_new")
    private Integer isNew;

    /**
     * 销量
     */
    private Long sales;

    /**
     * 浏览量
     */
    private Long views;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
