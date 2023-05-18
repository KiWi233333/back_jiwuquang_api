package com.example.back_jiwuquang_api.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 活动商品列表视图类
 *
 * @className: EventGoodsVO
 * @author: Kiwi23333
 * @description: TODO描述
 * @date: 2023/5/17 16:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EventGoodsVO {

    private String eventId;
    private String goodsId;
    private String eventGoodsId;

    @ApiModelProperty("活动价")
    private BigDecimal eventPrice;

    private String name;

    private String description;


    private BigDecimal price;

    private BigDecimal costPrice;

    private String categoryId;

    private String images;

    private String video;

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
    private String warrantyTime;

    @TableField("refund_time")
    private String refundTime;

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
