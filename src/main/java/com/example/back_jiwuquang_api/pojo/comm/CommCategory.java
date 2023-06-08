package com.example.back_jiwuquang_api.pojo.comm;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.back_jiwuquang_api.pojo.goods.GoodsCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * 社区圈子分类表
 *
 * @className: CommCategory
 * @author: Kiwi23333
 * @description: 社区圈子分类表
 * @date: 2023/6/8 2:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("comm_category")
public class CommCategory {

    private String id;
    private String name;
    /**
     * 父id
     */
    private String parentId;
    /**
     * 对应图标
     */
    private String image;
    /**
     * 权重
     */
    private Integer sortOrder;

    /**
     * 是否展示
     */
    private Integer isShow;

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
    private List<CommCategory> children;


    /**
     * 构建树
     *
     * @param list     原始列表
     * @param parentId null
     * @return List<CommCategory>
     */
    public static List<CommCategory> buildTree(List<CommCategory> list, String parentId) {
        List<CommCategory> result = new ArrayList<>();
        for (CommCategory category : list) {
            // id 相等
            if (Objects.equals(category.getParentId(), parentId)) {
                List<CommCategory> children = buildTree(list, category.getId());
                category.setChildren(children);
                result.add(category);
            }
        }
        return result;
    }
}