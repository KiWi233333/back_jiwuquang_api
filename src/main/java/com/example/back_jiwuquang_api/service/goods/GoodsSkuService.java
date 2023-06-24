package com.example.back_jiwuquang_api.service.goods;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.back_jiwuquang_api.dto.goods.GoodsSkuDTO;
import com.example.back_jiwuquang_api.dto.goods.UpdateGoodsSkuDTO;
import com.example.back_jiwuquang_api.pojo.goods.Goods;
import com.example.back_jiwuquang_api.pojo.goods.GoodsSku;
import com.example.back_jiwuquang_api.repository.goods.GoodsMapper;
import com.example.back_jiwuquang_api.repository.goods.GoodsSkuMapper;
import com.example.back_jiwuquang_api.util.RedisUtil;
import com.example.back_jiwuquang_api.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

//import static com.example.back_jiwuquang_api.domain.constant.GoodsConstant.GOODS_SKU_MAPS;

/**
 * 商品规格业务层
 *
 * @className: GoodsService
 * @author: Kiwi23333
 * @description: 商品规格业务层
 * @date: 2023/5/1 16:06
 */
@Service
@Slf4j
public class GoodsSkuService {

    @Autowired
    GoodsSkuMapper goodsSkuMapper;
    @Autowired
    GoodsService goodsService;
    @Autowired
    RedisUtil redisUtil;

    /**
     * 获取商品规格
     *
     * @param gid 商品id
     * @return Result
     */
    public Result getGoodsSkuByGoodsById(String gid) {
        List<GoodsSku> goodsSkuList = new ArrayList<>();
        // 1、redis 并构建
//        Map<String, Object> map = redisUtil.hGetAll(GOODS_SKU_MAPS + gid);
//        if (!map.isEmpty()) {
//            for (String key : map.keySet()) {
//                goodsSkuList.add((GoodsSku) map.get(key));
//            }
//            return Result.ok("获取成功！", goodsSkuList);
//        }
        // 2、数据库查询
        goodsSkuList = goodsSkuMapper.selectList(new QueryWrapper<GoodsSku>().lambda().eq(GoodsSku::getGoodsId, gid));
        if (goodsSkuList.isEmpty()) return Result.fail("商品规格为空！");
        log.info("getGoodsSkuByGoods ok!");
//        Map map = new HashMap<>();
//        for (GoodsSku goodsSku : goodsSkuList) {
//            map.put(goodsSku.getId(), goodsSku);
//        }
        // 3、缓存
//        redisUtil.hPutAll(GOODS_SKU_MAPS + gid, map);
        return Result.ok("获取成功！", goodsSkuList);
    }

    @Autowired
    GoodsMapper goodsMapper;

    /**
     * 添加商品规格
     *
     * @param goodsSkuDTO goodsSkuDTO
     * @return Result
     */
    public Result addGoodsSkuByGoods(GoodsSkuDTO goodsSkuDTO) {
        // 1、dto转化为实体类
        GoodsSku goodsSku = GoodsSkuDTO.toGoodsSku(goodsSkuDTO);
        // 查询是否有该商品
        if (goodsMapper.selectOne(new LambdaQueryWrapper<Goods>().eq(Goods::getId, goodsSku.getGoodsId()).last("limit 1")) == null) {
            return Result.fail(Result.LINK_NULL_ERR, "对应商品不存在！");
        }
        log.info("addGoodSku ing 添加规格中 {}", goodsSku);
        // 2、插入操作
        if (goodsSkuMapper.insert(goodsSku) <= 0) return Result.fail("添加规格失败！");
        // 3、添加redis缓存
//        redisUtil.delete(GOODS_SKU_MAPS + goodsSkuDTO.getGoodsId());
        return Result.ok("添加成功！", null);
    }


    /**
     * 修改规格
     *
     * @param goodsId        商品id
     * @param skuId          规格id
     * @param updateGoodsSku updateGoodsSku
     * @return Result
     */
    public Result updateGoodsSku(String goodsId, String skuId, UpdateGoodsSkuDTO updateGoodsSku) {
        // 1、修改
        GoodsSku goodsSku = UpdateGoodsSkuDTO.toGoodsSku(updateGoodsSku).setId(skuId);
        if (goodsSkuMapper.updateById(goodsSku) <= 0) return Result.fail("修改失败！");
        // 2、删除对应缓存
//        redisUtil.delete(GOODS_SKU_MAPS + goodsId);
        // 3、success
        return Result.ok("修改成功！", null);
    }


    /**
     * 删除单个规格
     *
     * @param goodsId 商品id
     * @param skuId   规格id
     * @return Result
     */
    public Result deleteGoodsSkuById(String goodsId, String skuId) {
        // 1、sql
        int flag = goodsSkuMapper.delete(new QueryWrapper<GoodsSku>().lambda()
                .eq(GoodsSku::getGoodsId, goodsId)
                .eq(GoodsSku::getId, skuId));
        if (flag <= 0)
            return Result.fail("删除失败，没有此规格！");
        // 2、删除对应缓存
//        redisUtil.hDelete(GOODS_SKU_MAPS + goodsId, skuId);
        // 3、success
        return Result.ok("删除成功！", flag);
    }


    /**
     * 批量删除规格
     *
     * @param ids 规格id 数组
     * @return Result
     */
    public Result batchDeleteGoodsSkuByIds(String goodsId, List<String> ids) {
        // 1、sql
        int flag = goodsSkuMapper.delete(new QueryWrapper<GoodsSku>().lambda()
                .eq(GoodsSku::getGoodsId, goodsId)
                .in(GoodsSku::getId, ids));
        if (flag <= 0) return Result.fail("删除失败，没有此规格！");
        // 2、删除缓存
//        redisUtil.delete(GOODS_SKU_MAPS + goodsId);
        // 3、success
        return Result.ok("删除成功！", flag);
    }

    /**
     * 获取商品规格
     * @param ids ids
     * @return Result
     */
    public Result getGoodsSkuByGoodsByIds(List<String> ids) {
        List<GoodsSku> goodsSkuList  = goodsSkuMapper.selectBatchIds(ids);
        log.info("getGoodsSkuByGoods sku ok!");
        Map map = new HashMap<>();
        return Result.ok("获取成功！", goodsSkuList);
    }
}

