package com.example.back_jiwuquang_api.service.orders;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.back_jiwuquang_api.dto.orders.DeliveryDTO;
import com.example.back_jiwuquang_api.dto.orders.InsertOrderCommentDTO;
import com.example.back_jiwuquang_api.pojo.orders.OrdersComment;
import com.example.back_jiwuquang_api.pojo.orders.OrdersDelivery;
import com.example.back_jiwuquang_api.repository.orders.OrdersCommentMapper;
import com.example.back_jiwuquang_api.repository.orders.OrdersDeliveryMapper;
import com.example.back_jiwuquang_api.repository.orders.OrdersItemMapper;
import com.example.back_jiwuquang_api.repository.orders.OrdersMapper;
import com.example.back_jiwuquang_api.util.RedisUtil;
import com.example.back_jiwuquang_api.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.back_jiwuquang_api.domain.constant.OrdersConstant.ORDERS_DELIVERY_MAPS_KEY;

@Service
@Slf4j
public class OrdersCommentService {

    @Autowired
    OrdersCommentMapper ordersCommentMapper;
    @Autowired
    OrdersMapper ordersMapper;
    @Autowired
    RedisUtil redisUtil;


    /******************** 查询  *********************/
    /**
     * 查询订单评价
     *
     * @param userId      用户id
     * @param orderItemId 订单单项id
     **/
    public Result getOrderCommentsByOrderId(String userId, String orderItemId) {
        LambdaQueryWrapper<OrdersComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrdersComment::getUserId, userId);
        queryWrapper.eq(OrdersComment::getOrdersItemId, orderItemId);
        List<OrdersComment> ordersCommentList = ordersCommentMapper.selectList(queryWrapper);
        if (ordersCommentList.size() == 0) {
            return Result.ok("订单评价不存在！", null);
        }
        return Result.ok("查询成功！", ordersCommentList);
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
        // 3、删除缓存
//        redisUtil.hDelete(GOODS_COMMENT_MAPS_KEY);

        return Result.ok("发表评价成功！", null);
    }


}
