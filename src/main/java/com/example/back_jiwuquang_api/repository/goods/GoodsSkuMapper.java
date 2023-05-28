package com.example.back_jiwuquang_api.repository.goods;

import com.example.back_jiwuquang_api.pojo.goods.GoodsSku;
import com.example.back_jiwuquang_api.repository.SpiceBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

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
    // 自增库存 更新
    @Update("UPDATE goods_sku SET stock = stock + #{addNum} WHERE id = #{skuId}")
    int addSkuStock(@Param("skuId")String skuId,@Param("addNum") Integer addNum);

    // 自减库存 更新
    @Update("UPDATE goods_sku SET stock = stock - #{reduceNum} WHERE id = #{skuId}")
    int reduceSkuStock(@Param("skuId")String skuId, @Param("reduceNum")Integer reduceNum);
}
