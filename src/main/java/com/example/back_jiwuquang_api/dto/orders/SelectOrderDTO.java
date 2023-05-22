package com.example.back_jiwuquang_api.dto.orders;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.example.back_jiwuquang_api.dto.shopcart.AddShopCartDTO;
import com.example.back_jiwuquang_api.pojo.orders.Orders;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * 订单分页查询DTO
 *
 * @className: SelectOrderDTO
 * @author: Kiwi23333
 * @description: 订单分页查询DTO
 * @date: 2023/5/22 19:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SelectOrderDTO {

    @ApiModelProperty(value = "订单id")
    String id;

    @ApiModelProperty(value = "订单关键词")
    String name;

    @ApiModelProperty(value = "店铺id")
    String shopId;

    @ApiModelProperty(value = "起始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "截至时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
}
