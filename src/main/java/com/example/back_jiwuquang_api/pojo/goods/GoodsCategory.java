package com.example.back_jiwuquang_api.pojo.goods;

import com.baomidou.mybatisplus.annotation.*;
import com.example.back_jiwuquang_api.dto.goods.GoodsCategoryDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

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
@AllArgsConstructor
@NoArgsConstructor
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
    private String icon;
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
    private List<GoodsCategory> children;


    /**
     * 递归 生成的树
     *
     * @param list     分类集合
     * @param parentId 父id
     * @return 树形集合
     */

    public static List<GoodsCategory> buildTree(List<GoodsCategory> list, String parentId) {
        // 下一级目录
        List<GoodsCategory> result = new ArrayList<>();
        for (GoodsCategory category : list) {
            // id 相等
            if (Objects.equals(category.getParentId(), parentId)) {
                List<GoodsCategory> children = buildTree(list, category.getId());
                category.setChildren(children);
                result.add(category);
            }
        }
        return result;
    }


    public static GoodsCategory buildTreeByOne(List<GoodsCategory> list, String parentId) {
        // 下一级目录
        for (GoodsCategory category : list) {
            // id 相等
            if (Objects.equals(category.getParentId(), parentId)) {
                List<GoodsCategory> children = buildTree(list, category.getId());
                category.setChildren(children);
            }
        }
        return list.get(0);
    }


    // vo 转换为 po
    public static GoodsCategory toGoodsCategory(GoodsCategoryDTO g) {
        return new GoodsCategory(null, g.getName(), g.getParentId(), g.getIcon(), g.getSortOrder(), g.getIsShow(), null, null, null);
    }
}