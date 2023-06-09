package com.example.back_jiwuquang_api.pojo.comm;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("comm_post_comment")
public class CommPostComment {

    private String id;
    /**
     * 帖子id
     */
    private String postId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 评论对象id
     */
    private String parentId;

    /**
     * 内容
     */
    private String content;

    private String images;

    private Integer views;

    private Integer likes;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 用于数据处理生成的子树
     */
    @TableField(exist = false)
    private List<CommPostComment> children;

    /**
     * 构建树
     *
     * @param list     原始列表
     * @param parentId null
     * @return List<CommPostComment>
     */
    public static List<CommPostComment> buildTree(List<CommPostComment> list, String parentId) {
        List<CommPostComment> result = new ArrayList<>();
        for (CommPostComment category : list) {
            // id 相等
            if (Objects.equals(category.getParentId(), parentId)) {
                List<CommPostComment> children = buildTree(list, category.getId());
                category.setChildren(children);
                result.add(category);
            }
        }
        return result;
    }

}