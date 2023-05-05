package com.example.back_jiwuquang_api.repository;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.back_jiwuquang_api.pojo.goods.GoodsCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsCategoryMapper extends BaseMapper<GoodsCategory> {

    // 查询全部分类：生成树结构
    default List<GoodsCategory> selectALlCategoryTree() {
        List<GoodsCategory> list;
        // 构建树结构
        list = GoodsCategory.buildNestedCategories(this.selectList(null),null);
        return list;
    }


}
