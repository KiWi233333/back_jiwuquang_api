package com.example.back_jiwuquang_api.repository.goods;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.back_jiwuquang_api.pojo.goods.Goods;
import com.example.back_jiwuquang_api.vo.goods.GoodsInfoVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {


    // 查询商品信息 GoodsInfoVO
    default GoodsInfoVO selectInfoById(String id) {
        LambdaQueryWrapper<Goods> qw = new LambdaQueryWrapper<Goods>().eq(Goods::getId, id);
        Goods goods = this.selectOne(qw);
        if (goods == null) {
            return null;
        } else {
            return GoodsInfoVO.toGoodsInfo(goods);
        }
    }
}
