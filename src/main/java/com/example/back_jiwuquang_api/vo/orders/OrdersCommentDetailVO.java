package com.example.back_jiwuquang_api.vo.orders;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单评价详情列表 视图
 *
 * @className: OrdersCommentVO
 * @author: Kiwi23333
 * @description: 订单评价表视图
 * @date: 2023/5/30 21:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrdersCommentDetailVO {


    /**
     * 评论id
     */
    private String id;
    /**
     * 昵称
     */
    private String nickName;



    /**
     * 推荐skuId
     */
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