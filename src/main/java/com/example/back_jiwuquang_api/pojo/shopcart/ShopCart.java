package com.example.back_jiwuquang_api.pojo.shopcart;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 描述
 *
 * @className: ShopCart
 * @author: Kiwi23333
 * @description: TODO描述
 * @date: 2023/5/13 11:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("shop_cart")
public class ShopCart {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    private String userId;

    /**
     * 规格id
     */
    private String skuId;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 活动id
     */
    private String activityId;

    /**
     * 店铺id
     */
    private String shopId;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

}
