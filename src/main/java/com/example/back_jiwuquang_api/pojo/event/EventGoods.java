package com.example.back_jiwuquang_api.pojo.event;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.yulichang.autoconfigure.conditional.MPJSqlInjectorCondition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 活动商品关联表实体类
 *
 * @className: Event
 * @author: Kiwi23333
 * @description: 活动商品关联表实体类
 * @date: 2023/5/17 16:29
 */
@Data
@TableName("event_goods")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EventGoods {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 活动id
     */
    @TableField(value = "event_id")
    private String eventId;

    /**
     * 商品id
     */
    @TableField(value = "goods_id")
    private String goodsId;

    /**
     * 活动价格
     */
    @TableField(value = "event_price")
    private BigDecimal eventPrice;


    @TableField(value = "created_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;

    @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatedTime;

}