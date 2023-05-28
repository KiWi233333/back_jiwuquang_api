package com.example.back_jiwuquang_api.pojo.orders;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 订单发货表
 *
 * @className: OrdersDelivery
 * @author: Kiwi23333
 * @description: TODO订单发货表
 * @date: 2023/5/26 22:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("orders_delivery")
public class OrdersDelivery {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("订单号")
    private String ordersId;

    @ApiModelProperty("快递单号")
    private String deliveryNum;

    @ApiModelProperty("发货地址")
    private String sendAddress;

    @ApiModelProperty("收货地址")
    private String deliverAddress;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}