package com.example.kiwi_community_mall_back.pojo.goods;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.*;

/**
 * 商品分类实体类
 *
 * @className: GoodsCategory
 * @author: Kiwi23333
 * @description: 商品分类
 * @date: 2023/5/1 11:37
 */

@Data
@TableName("goods_category")
public class GoodsCategory {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 商品分类名称
     */
    private String name;

    /**
     * 父id
     */
    private String parentId;

    /**
     * 分类图标
     */
    private String  icon;
    /**
     * 权重
     */
    private Integer sortOrder;

    /**
     * 是否展示
     */
    private Integer isShow;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 用于数据处理生成的子树
     */
    @TableField(exist = false)
    private List<GoodsCategory> children;


    /**
     * 递归 生成的树
     * @param categories 分类集合
     * @param parentId 父id
     * @return 树形集合
     */
    public static List<GoodsCategory> buildNestedCategories(List<GoodsCategory> categories, String parentId) {
        // 下一级目录
        List<GoodsCategory> nestedCategories = new ArrayList<>();
        for (GoodsCategory category : categories) {
            // id 相等
            if (Objects.equals(category.getParentId(), parentId)) {
                List<GoodsCategory> children = buildNestedCategories(categories, category.getId());
                category.setChildren(children);
                nestedCategories.add(category);
            }
        }
        return nestedCategories;
    }


}