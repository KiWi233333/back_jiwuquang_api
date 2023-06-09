package com.example.back_jiwuquang_api.pojo.orders;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单评价表实体类
 *
 * @className: OrdersComment
 * @author: Kiwi23333
 * @description: 订单评价表实体类
 * @date: 2023/5/30 21:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("orders_comment")
public class OrdersComment {

    @TableId("id")
    private String id;

    private String userId;

    /**
     * 订单项目id
     */
    private String ordersItemId;

    private String skuId;

    private Integer isRecommend;

    private Integer isAnonymous;

    private String content;

    /**
     * 图片名称集合
     */
    private String images;
    /**
     * 视频名称
     */
    private String video;

    /**
     * 星级别
     */
    private BigDecimal rate;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}