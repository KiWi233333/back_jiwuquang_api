package com.example.back_jiwuquang_api.repository.goods;


import com.example.back_jiwuquang_api.pojo.goods.GoodsCategory;
import com.example.back_jiwuquang_api.repository.SpiceBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
public interface GoodsCategoryMapper extends SpiceBaseMapper<GoodsCategory> {

    // 查询全部分类：生成树结构
    default List<GoodsCategory> selectALlCategoryTree() {
        List<GoodsCategory> list;
        // 构建树结构
        list = GoodsCategory.buildNestedCategories(this.selectList(null), null);
        return list;
    }


    // 低效插入数据
    @Transactional(rollbackFor = RuntimeException.class)
    default boolean saveBatch(List<GoodsCategory> categoryList) {
        try {
            categoryList.forEach(p -> {
                this.insert(p);
            });
            return true;
        } catch (Exception e) {
            throw  new RuntimeException();
        }
    }
}
