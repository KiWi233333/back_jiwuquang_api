package com.example.back_jiwuquang_api.service.orders;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.back_jiwuquang_api.domain.annotation.Auth;
import com.example.back_jiwuquang_api.dto.orders.InsertOrderCommentDTO;
import com.example.back_jiwuquang_api.pojo.goods.Goods;
import com.example.back_jiwuquang_api.pojo.goods.GoodsSku;
import com.example.back_jiwuquang_api.pojo.orders.OrdersComment;
import com.example.back_jiwuquang_api.pojo.orders.OrdersItem;
import com.example.back_jiwuquang_api.pojo.sys.User;
import com.example.back_jiwuquang_api.repository.goods.GoodsSkuMapper;
import com.example.back_jiwuquang_api.repository.orders.OrdersCommentMapper;
import com.example.back_jiwuquang_api.repository.orders.OrdersItemMapper;
import com.example.back_jiwuquang_api.repository.orders.OrdersMapper;
import com.example.back_jiwuquang_api.util.RedisUtil;
import com.example.back_jiwuquang_api.util.Result;
import com.example.back_jiwuquang_api.vo.orders.OrdersCommentVO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.back_jiwuquang_api.domain.constant.OrdersConstant.ORDERS_COMMENT_MAPS_KEY;
import static com.example.back_jiwuquang_api.domain.constant.OrdersConstant.ORDERS_COMMENT_PAGES_KEY;

@Service
@Slf4j
public class OrdersCommentService {

    @Autowired
    OrdersCommentMapper ordersCommentMapper;
    @Autowired
    OrdersMapper ordersMapper;
    @Autowired
    OrdersItemMapper ordersItemMapper;
    @Autowired
    RedisUtil redisUtil;


    /******************** 查询  *********************/


    /**
     * 查询子订单评价
     *
     * @param userId      用户id
     * @param orderItemId 订单单项id
     **/
    public Result getOrderCommentByOrderItemId(String userId, String orderItemId) {
        // 1、查询缓存
        OrdersComment comment = (OrdersComment) redisUtil.hGet(ORDERS_COMMENT_MAPS_KEY, orderItemId);
        if (comment != null) {
            return Result.ok(comment);
        }
        // 2、查询数据库
        LambdaQueryWrapper<OrdersComment> qw = new LambdaQueryWrapper<>();
        qw.eq(OrdersComment::getUserId, userId);
        qw.eq(OrdersComment::getOrdersItemId, orderItemId);
        comment = ordersCommentMapper.selectOne(qw);
        if (comment == null) {
            return Result.ok("子订单评价不存在！", null);
        }
        return Result.ok(comment);
    }

    /**
     * 查询总订单评价
     *
     * @param userId  用户id
     * @param orderId 订单id
     * @return Result
     */
    public Result getOrderCommentsByOrderId(String userId, String orderId) {
        // 1、查询缓存
        List<OrdersComment> commentList = (List<OrdersComment>) redisUtil.hGet(ORDERS_COMMENT_MAPS_KEY, orderId);
        if (commentList != null) {
            return Result.ok("查询成功！", commentList);
        }
        // 2、查询数据库
        MPJLambdaWrapper<OrdersComment> qw = new MPJLambdaWrapper<OrdersComment>()
                .eq(OrdersItem::getOrdersId, orderId)
                .eq(OrdersComment::getUserId, userId)
                .selectAll(OrdersComment.class)
                .leftJoin(OrdersItem.class, OrdersItem::getId, OrdersComment::getOrdersItemId);
        commentList = ordersCommentMapper.selectJoinList(OrdersComment.class, qw);
        if (commentList == null) {
            return Result.ok("子订单评价不存在！", null);
        } else {
            // 缓存
            redisUtil.hPut(ORDERS_COMMENT_MAPS_KEY, orderId, commentList);
        }
        return Result.ok("查询成功！", commentList);
    }


    /**
     * 查询评价详情
     *
     * @param id 评价id
     * @return Result
     */
    public Result getCommentListByCommentId(String id) {
//        MPJAbstractLambdaWrapper qw = new MPJAbstractLambdaWrapper<OrdersCommentVO>()
//
//
//        ordersCommentMapper.selectJoinOne(qw)

        return Result.ok(null);
    }


    @Autowired
    GoodsSkuMapper goodsSkuMapper;

    /**
     * 获取商品的评价分页表
     *
     * @param page 页码
     * @param size 个数
     * @param gid  商品id
     * @return Result<IPage < OrdersCommentVO>>
     */
    public Result getCommentsByGId(int page, int size, String gid) {
        // 1、查询缓存
        IPage<OrdersCommentVO> commentsPages = (IPage<OrdersCommentVO>) redisUtil.hGet(ORDERS_COMMENT_PAGES_KEY + page + size, gid);
        if (commentsPages != null) {
            return Result.ok("查询成功！", commentsPages);
        }
        // 2、查询数据库
        MPJLambdaWrapper<OrdersComment> qw = new MPJLambdaWrapper<OrdersComment>()
                .eq(GoodsSku::getGoodsId,gid) // 商品id
                .select(User::getNickname,User::getAvatar)
                .selectAll(OrdersComment.class)
                .leftJoin(GoodsSku.class,GoodsSku::getId,OrdersComment::getSkuId)
                .leftJoin(User.class, User::getId, OrdersComment::getUserId);
        commentsPages = ordersCommentMapper.selectJoinPage(new Page<OrdersCommentVO>(page, size), OrdersCommentVO.class, qw);


        // 3、缓存
        if (commentsPages != null) {
            redisUtil.hPut(ORDERS_COMMENT_PAGES_KEY + page + size, gid, commentsPages);
        }
        return Result.ok("获取成功！", commentsPages);
    }


    /********************* 添加 *************************/
    /**
     * 添加订单评价
     *
     * @param userId  用户id
     * @param dtoList List<InsertOrderCommentDTO>
     * @return Result
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public Result addOrdersCommentByOrderItemId(String userId, List<InsertOrderCommentDTO> dtoList) {
        // 1、生成数据
        List<OrdersComment> paramList = dtoList.stream().map(p -> InsertOrderCommentDTO.toOrdersComment(p, userId)).collect(Collectors.toList());
        // 2、批量添加评论
        int count = ordersCommentMapper.insertBatchSomeColumn(paramList);
        if (count != paramList.size()) {
            throw new RuntimeException("部分订单评价添加失败！");
        }
        // 3、删除评价缓存
        redisUtil.hDelete(ORDERS_COMMENT_MAPS_KEY, userId);
        return Result.ok("发表评价成功！", null);
    }

}
