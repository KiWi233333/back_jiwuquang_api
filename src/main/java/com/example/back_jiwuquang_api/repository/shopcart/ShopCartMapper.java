package com.example.back_jiwuquang_api.repository.shopcart;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.back_jiwuquang_api.pojo.goods.Goods;
import com.example.back_jiwuquang_api.pojo.goods.GoodsSku;
import com.example.back_jiwuquang_api.pojo.shopcart.ShopCart;
import com.example.back_jiwuquang_api.repository.SpiceBaseMapper;
import com.example.back_jiwuquang_api.vo.shopcart.ShopCartPageVO;
import com.github.yulichang.base.MPJBaseMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShopCartMapper extends SpiceBaseMapper<ShopCart>, MPJBaseMapper<ShopCart> {


    default IPage<ShopCartPageVO> selectShopCartPage(int page, int size, String userId) {

        Page<ShopCartPageVO> pages = new Page<>(page, size); // 创建分页对象，指定当前页码和每页记录数
        MPJLambdaWrapper<ShopCart> mpj = new MPJLambdaWrapper<>(); // 创建查询条件
        // 倒叙查询插入时间
        // 购物车表
        mpj.select(ShopCart::getId, ShopCart::getSkuId, ShopCart::getQuantity, ShopCart::getActivityId, ShopCart::getShopId, ShopCart::getActivityId, ShopCart::getCreateTime, ShopCart::getUpdateTime)
                // 商品表
                .select(Goods::getName)
                // 商品规格表
                .select(GoodsSku::getGoodsId, GoodsSku::getSize, GoodsSku::getColor, GoodsSku::getCombo, GoodsSku::getStock, GoodsSku::getPrice, GoodsSku::getCostPrice,GoodsSku::getImage,GoodsSku::getDescription)
                .leftJoin(GoodsSku.class, GoodsSku::getId, ShopCart::getSkuId)
                .leftJoin(Goods.class, Goods::getId, GoodsSku::getGoodsId)
                .orderByDesc(ShopCart::getCreateTime)
                .eq(ShopCart::getUserId, userId);
        // IPage selectJoinPage
        IPage<ShopCartPageVO> userPage = this.selectJoinPage(pages, ShopCartPageVO.class,mpj); // 调用Mapper接口方法进行分页查询
        return userPage.getTotal() <= 0 ? null : userPage;
    }
}
