package com.example.kiwi_community_mall_back.pojo.wallet;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值套装实体类
 *
 * @className: RechargeCombo
 * @author: Kiwi23333
 * @description: 充值套装实体类
 * @date: 2023/4/30 21:57
 */
@Data
@TableName("recharge_combo")
public class RechargeCombo {

    @TableId
    private Integer id;

    /**
     * 套餐描述
     */
    private String name;

    /**
     * 折扣
     */
    private Float discount;

    /**
     * 额度
     */
    private BigDecimal amount;

    /**
     * 送积分
     */
    private Long points;

    @TableField(value="create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}