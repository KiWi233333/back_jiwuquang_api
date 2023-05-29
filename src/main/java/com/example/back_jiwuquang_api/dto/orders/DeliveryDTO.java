package com.example.back_jiwuquang_api.dto.orders;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.back_jiwuquang_api.pojo.orders.OrdersDelivery;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;


/**
 * 订单发货对象 DTO
 *
 * @className: InseDeliveryDTO
 * @author: Kiwi23333
 * @description: 订单发货对象 DTO
 * @date: 2023/5/22 19:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class DeliveryDTO {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("订单号")
    private String ordersId;

    @ApiModelProperty("快递单号")
    private String deliveryNum;

    @ApiModelProperty(value = "发货地址", required = true)
    @NotBlank(message = "发货地址不能为空！")
    private String sendAddress;

    @ApiModelProperty(value = "收货地址", required = true)
    @NotBlank(message = "收货地址不能为空！")
    private String deliverAddress;


    public static OrdersDelivery toOrdersDelivery(DeliveryDTO dto) {
        return new OrdersDelivery()
                .setOrdersId(dto.getOrdersId())
                .setDeliveryNum(dto.getDeliveryNum())
                .setSendAddress(dto.getSendAddress())
                .setDeliverAddress(dto.getDeliverAddress());
    }

    public static OrdersDelivery toAddOrdersDelivery(DeliveryDTO dto) {
        return new OrdersDelivery()
                .setId(dto.getId())
                .setOrdersId(dto.getOrdersId())
                .setDeliveryNum(dto.getDeliveryNum())
                .setSendAddress(dto.getSendAddress())
                .setDeliverAddress(dto.getDeliverAddress());
    }
}
