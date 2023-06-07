package com.example.back_jiwuquang_api.pojo.goods;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 商品点赞/收藏表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("goods_action")
public class GoodsAction {

    /**
     * 唯一标识
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 用户 ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 商品 ID
     */
    @TableField("goods_id")
    private String goodsId;

    /**
     * 行为类型，0 表示点赞，1 表示收藏
     */
    private Integer type;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
