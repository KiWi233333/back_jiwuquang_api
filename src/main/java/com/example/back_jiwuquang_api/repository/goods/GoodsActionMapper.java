package com.example.back_jiwuquang_api.repository.goods;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.back_jiwuquang_api.pojo.goods.Goods;
import com.example.back_jiwuquang_api.pojo.goods.GoodsAction;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsActionMapper extends BaseMapper<GoodsAction>, MPJBaseMapper<GoodsAction> {
}
