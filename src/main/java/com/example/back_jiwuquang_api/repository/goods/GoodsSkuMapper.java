package com.example.back_jiwuquang_api.repository.goods;

import com.example.back_jiwuquang_api.pojo.goods.GoodsCategory;
import com.example.back_jiwuquang_api.pojo.goods.GoodsSku;
import com.example.back_jiwuquang_api.repository.SpiceBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品规格mapper层
 *
 * @className: GoodsSkuMapper
 * @author: Kiwi23333
 * @description: 商品规格mapper层
 * @date: 2023/5/6 2:01
 */
@Mapper
public interface GoodsSkuMapper extends SpiceBaseMapper<GoodsSku> {
}
