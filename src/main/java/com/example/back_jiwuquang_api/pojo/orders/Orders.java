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
    /**
     * 支付金额
     */
    private BigDecimal spendPrice;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 订单状态，0表示待付款，1:已付款，2:已发货，3:已收货，4:已评价，5:已取消，6:已超时取消，7:发起退款，8:退款成功并取消
     */
    private Integer status;

    /**
     * 支付时间
     */
    @TableField(value = "paid_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date paidTime;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}
