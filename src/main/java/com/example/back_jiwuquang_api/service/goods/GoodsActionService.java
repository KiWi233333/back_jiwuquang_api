package com.example.back_jiwuquang_api.service.goods;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.back_jiwuquang_api.pojo.goods.Goods;
import com.example.back_jiwuquang_api.pojo.goods.GoodsAction;
import com.example.back_jiwuquang_api.repository.goods.GoodsActionMapper;
import com.example.back_jiwuquang_api.repository.goods.GoodsMapper;
import com.example.back_jiwuquang_api.util.RedisUtil;
import com.example.back_jiwuquang_api.util.Result;
import com.example.back_jiwuquang_api.vo.goods.GoodsCollectVO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.back_jiwuquang_api.domain.constant.GoodsConstant.*;

/**
 * 描述
 *
 * @className: GoodsService
 * @author: Kiwi23333
 * @description: TODO描述
 * @date: 2023/5/1 16:06
 */
@Service
@Slf4j
public class GoodsActionService {

    @Autowired
    GoodsActionMapper goodsActionMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    RedisUtil redisUtil;


    /****************************** 商品点赞/收藏 类 ***********************************/


    /**
     * 商品收藏切换
     *
     * @param userId 用户id
     * @param gid    商品id
     * @return Result
     */
    public Result addGoodsCollectionByGid(String userId, String gid) {

        // 1、查询商品是否存在
        if (goodsMapper.selectOne(new LambdaQueryWrapper<Goods>().eq(Goods::getId, gid).last("LIMIT 1")) == null) {
            return Result.fail(Result.LINK_NULL_ERR, "商品不存在！");
        }
        // 2、查询是否存在收藏
        String id = isExistsCollect(userId, gid);
        int count;
        if (StringUtil.isNullOrEmpty(id)) {// 不存在添加
            // 1）添加
            GoodsAction action = new GoodsAction().setGoodsId(gid).setUserId(userId).setType(GOODS_ACTION_COLLECT_TYPE);
            count = goodsActionMapper.insert(action);
        } else {// 存在删除
            // 2）删除
            count = goodsActionMapper.deleteById(id);
        }
        // 3、操作结果
        String text = StringUtil.isNullOrEmpty(id) ? "新增" : "取消";
        if (count <= 0) {
            return Result.fail(Result.NULL_ERR, text + "收藏失败！");
        }
        // 4、清除缓存
        redisUtil.delete(GOODS_ACTION_COLLECT_LIST + userId);
        log.info("用户{} 收藏商品 {}", userId, gid);
        return Result.ok(text + "收藏成功！", count);
    }

    /**
     * 获取商品收藏列表
     *
     * @param userId 用户id
     * @return Result
     */
    public Result getGoodsCollect(String userId) {
        // 1、缓存
        List<GoodsCollectVO> list = (List<GoodsCollectVO>) redisUtil.lRange(GOODS_ACTION_COLLECT_LIST + userId, -1, 0);
        if (list != null && !list.isEmpty()) {
            return Result.ok("获取成功！", list);
        }
        // 2、sql
        MPJLambdaWrapper<GoodsAction> qw = new MPJLambdaWrapper<GoodsAction>()
                .select(GoodsAction::getId,GoodsAction::getCreateTime)
                .selectAssociation(Goods.class, GoodsCollectVO::getGoods)
                .eq(GoodsAction::getUserId, userId)
                .eq(GoodsAction::getType, GOODS_ACTION_COLLECT_TYPE)
                .orderByDesc(GoodsAction::getCreateTime)
                .leftJoin(Goods.class, Goods::getId, GoodsAction::getGoodsId);
        list = goodsActionMapper.selectJoinList(GoodsCollectVO.class, qw);
        // 3、缓存
        if (!list.isEmpty()) {
            long count = redisUtil.lRightPushAll(GOODS_ACTION_COLLECT_LIST + userId, list);
            // 若数据不一致情况
            if (count != list.size()) {
                redisUtil.delete(GOODS_ACTION_COLLECT_LIST + userId);
            }
        }
        return Result.ok("获取成功！", list);
    }


    /**
     * 取消商品（批量）
     *
     * @param userId 用户id
     * @param ids    ids
     * @return Result
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public Result deleteBatchGoodsAction(String userId, List<String> ids) {
        // 1、sql
        LambdaQueryWrapper<GoodsAction> qw = new LambdaQueryWrapper<GoodsAction>()
                .eq(GoodsAction::getUserId, userId)
                .in(GoodsAction::getId, ids);
        int counts = goodsActionMapper.delete(qw);
        // 2、数量不一致
        if (counts != ids.size()) {
            log.warn("Goods action deleted failed {}", ids);
            throw new RuntimeException("取消失败！");
        }
        // 3、清除缓存
        redisUtil.delete(GOODS_ACTION_COLLECT_LIST + userId);
        return Result.ok("取消成功！", counts);
    }


    /**
     * 查询是否收藏商品
     *
     * @param userId 用户id
     * @param gid    商品id
     * @return Result《String》
     */
    public Result isGoodsCollect(String userId, String gid) {
        String id = isExistsCollect(userId, gid);
        if (StringUtils.isNotBlank(id)) {
            return Result.ok("查询成功", id);
        } else {
            return Result.fail(Result.SELECT_ERR,"查询不存在！");
        }
    }

    /**
     * 是否存在该商品（0点赞/1收藏） 获取id
     *
     * @param userId  用户id
     * @param goodsId 商品id
     * @return String 收藏id号
     */
    private String isExistsCollect(String userId, String goodsId) {
        GoodsAction goodsAction = goodsActionMapper.selectOne(new LambdaQueryWrapper<GoodsAction>()
                .select(GoodsAction::getId)
                .eq(GoodsAction::getGoodsId, goodsId)
                .eq(GoodsAction::getUserId, userId)
                .eq(GoodsAction::getType, GOODS_ACTION_COLLECT_TYPE)
                .last("LIMIT 1"));
        if (goodsAction == null) {
            return null;
        } else {
            return goodsAction.getId();
        }
    }
}
