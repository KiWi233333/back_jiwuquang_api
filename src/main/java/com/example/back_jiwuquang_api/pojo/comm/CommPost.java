package com.example.back_jiwuquang_api.pojo.comm;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 社区圈子表
 *
 * @className: CommPost
 * @author: Kiwi23333
 * @description: 社区圈子表
 * @date: 2023/6/8 2:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("comm_post")
public class CommPost {

    private String id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 分类id
     */
    private String categoryId;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 图片
     */
    private String images;
    /**
     * 标签
     */
    private String tags;
    /**
     * 是否加精，0-不加精，1-加精
     */
    private Boolean isEssence;

    /**
     * 顶置评论id
     */
    private String commentTopId;
    /**
     * 观看数
     */
    private Integer views;

    /**
     * 评论数
     */
    private Integer comments;

    /**
     * 点赞数
     */
    private Integer likes;

    /**
     * 状态：0-草稿，1-已发布，2-已删除
     */
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}