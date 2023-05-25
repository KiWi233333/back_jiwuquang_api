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
 * 主订单实体类
 *
 * @className: Orders
 * @author: Kiwi23333
 * @description: 主订单实体类
 * @date: 2023/5/19 22:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("orders")
public class Orders {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    private String userId;

    private String addressId;
    /**
     * 备注
     */
    private String remark;

    @TableField(value = "orders_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date ordersTime;

    private BigDecimal totalPrice;

    private Integer status;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**支付时间 */
    @TableField(value = "paid_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date paidTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

}
