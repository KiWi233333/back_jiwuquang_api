package com.example.back_jiwuquang_api.pojo.orders;


import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单项目实体类
 *
 * @className: Orders
 * @author: Kiwi23333
 * @description: 订单项目实体类
 * @date: 2023/5/19 22:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("orders_item")
public class OrdersItem {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("orders_id")
    private String ordersId;

    /**
     * 规格id
     */
    private String skuId;

    private int quantity;

    /**
     * 活动id false
     */
    private String activityId;

    /**
     * 店铺id false
     */
    private String shopId;

    /**
     * 优惠
     */
    private BigDecimal reducePrice;

    /**
     * 子订单总价
     */
    private BigDecimal finalPrice;


    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;


}
