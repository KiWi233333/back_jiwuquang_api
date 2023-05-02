package com.example.kiwi_community_mall_back.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.kiwi_community_mall_back.dto.goods.GoodsCategoryVO;
import com.example.kiwi_community_mall_back.pojo.goods.GoodsCategory;
import com.example.kiwi_community_mall_back.repository.GoodsCategoryMapper;
import com.example.kiwi_community_mall_back.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.kiwi_community_mall_back.core.constant.GoodsConstant.GOODS_CATEGORY_LIST;

/**
 * 描述
 *
 * @className: GoodsService
 * @author: Kiwi23333
 * @description: TODO描述
 * @date: 2023/5/1 16:06
 */
@Service
public class GoodsCategoryService {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    GoodsCategoryMapper goodsCategoryMapper;

    /**
     * 获取所有分类
     *
     * @return
     */
    public Result getAllCategoryTree() {
        List<GoodsCategory> list = (List<GoodsCategory>) redisTemplate.opsForValue().get(GOODS_CATEGORY_LIST);
        // 数据库取
        if (list == null) {
            list = goodsCategoryMapper.selectALlCategoryTree();
        } else {
            return Result.ok(list);
        }
        // 缓存
        if (!list.isEmpty()) {
            redisTemplate.opsForValue().set(GOODS_CATEGORY_LIST, list);
            return Result.ok(list);
        } else {
            return Result.fail("获取失败！");
        }
    }

    /**
     * 添加分类（单个）
     *
     * @param goodsCategoryVOs
     * @return
     */
    public Result addCategoryByOne(GoodsCategoryVO goodsCategoryVOs) {
        return Result.ok();
    }

    /**
     * 添加分类（批量）
     *
     * @param categoryVOList
     * @return
     */
    public Result addCategoryByList(List<GoodsCategoryVO> categoryVOList) {
        return Result.ok();
    }

    /**
     * 删除分类（单一）
     *
     * @param id
     * @return
     */
    public Result deleteOneByCategoryId(String id) {
        // 删除其附属的分类
        int flags = goodsCategoryMapper.delete(new QueryWrapper<GoodsCategory>().eq("id", id).or().eq("parent_id", id));
        // 重新缓存
        if (flags > 0) {// 重新缓存
            redisTemplate.opsForValue().set(GOODS_CATEGORY_LIST, goodsCategoryMapper.selectALlCategoryTree());
            return Result.ok("删除成功！", "删除共" + flags + "条数据");
        } else {
            return Result.fail("删除失败！");
        }
    }


    /**
     * 删除分类（批量）可跨级
     *
     * @param ids
     * @return
     */
    public Result deleteByCategoryIds(List<String> ids) {

        int count = 0;
        count = goodsCategoryMapper.delete(new QueryWrapper<GoodsCategory>().in("id", ids).or().in("parent_id", ids));
        if (count == 0) {
            redisTemplate.opsForValue().set(GOODS_CATEGORY_LIST, goodsCategoryMapper.selectALlCategoryTree());
            return Result.fail("删除失败！");
        } else {
            return Result.ok("删除成功！", "共删除" + count + "条数据");
        }
    }
}
