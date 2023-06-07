package com.example.back_jiwuquang_api.service.goods;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.back_jiwuquang_api.dto.goods.GoodsDTO;
import com.example.back_jiwuquang_api.dto.goods.GoodsPageDTO;
import com.example.back_jiwuquang_api.dto.goods.UpdateGoodsDTO;
import com.example.back_jiwuquang_api.pojo.goods.Goods;
import com.example.back_jiwuquang_api.pojo.goods.GoodsAction;
import com.example.back_jiwuquang_api.pojo.goods.GoodsSku;
import com.example.back_jiwuquang_api.repository.goods.GoodsActionMapper;
import com.example.back_jiwuquang_api.repository.goods.GoodsMapper;
import com.example.back_jiwuquang_api.repository.goods.GoodsSkuMapper;
import com.example.back_jiwuquang_api.util.RedisUtil;
import com.example.back_jiwuquang_api.util.Result;
import com.example.back_jiwuquang_api.vo.goods.GoodsCollectVO;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        // 查询商品是否存在
        if (goodsMapper.selectById(gid) == null) {
            return Result.fail(Result.LINK_NULL_ERR, "商品不存在！");
        }
        // 2、添加
        GoodsAction action = new GoodsAction().setGoodsId(gid).setUserId(userId).setType(GOODS_ACTION_COLLECT_TYPE);
        int count = goodsActionMapper.insert(action);
        if (count <= 0) {
            return Result.fail(Result.INSERT_ERR, "添加失败！");
        }
        return Result.ok("添加成功！", count);
    }

    /**
     * 获取商品收藏列表
     * @param userId 用户id
     * @return Result
     */
    public Result getGoodsCollect(String userId) {
        LambdaQueryWrapper<GoodsAction> qw = new LambdaQueryWrapper<>();
        qw.eq(GoodsAction::getUserId, userId);
        qw.eq(GoodsAction::getType, GOODS_ACTION_COLLECT_TYPE);
        qw.orderByDesc(GoodsAction::getCreateTime);
        IPage<GoodsAction> page = goodsActionMapper.selectJoinList(GoodsCollectVO.class,qw);
        return Result.ok("获取成功！", page);
    }
}
