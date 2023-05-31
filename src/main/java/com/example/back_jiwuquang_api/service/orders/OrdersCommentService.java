package com.example.back_jiwuquang_api.service.orders;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.back_jiwuquang_api.dto.orders.DeliveryDTO;
import com.example.back_jiwuquang_api.dto.orders.InsertOrderCommentDTO;
import com.example.back_jiwuquang_api.pojo.orders.Orders;
import com.example.back_jiwuquang_api.pojo.orders.OrdersComment;
import com.example.back_jiwuquang_api.pojo.orders.OrdersDelivery;
import com.example.back_jiwuquang_api.pojo.orders.OrdersItem;
import com.example.back_jiwuquang_api.repository.orders.OrdersCommentMapper;
import com.example.back_jiwuquang_api.repository.orders.OrdersDeliveryMapper;
import com.example.back_jiwuquang_api.repository.orders.OrdersItemMapper;
import com.example.back_jiwuquang_api.repository.orders.OrdersMapper;
import com.example.back_jiwuquang_api.util.RedisUtil;
import com.example.back_jiwuquang_api.util.Result;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.back_jiwuquang_api.domain.constant.OrdersConstant.ORDERS_COMMENT_MAPS_KEY;
import static com.example.back_jiwuquang_api.domain.constant.OrdersConstant.ORDERS_DELIVERY_MAPS_KEY;

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
//        MPJLambdaWrapper<OrdersItem> qw = new MPJLambdaWrapper<OrdersItem>()
//                .eq(OrdersItem::getOrdersId, orderId)
//                .leftJoin(OrdersComment.class, OrdersItem::getId, OrdersComment::getOrdersItemId);
//        ordersCommentMapper.selectJoinList(OrdersComment.class,qw);
        if (commentList == null) {
            return Result.ok("子订单评价不存在！", null);
        }
        return Result.ok("查询成功！", commentList);
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
