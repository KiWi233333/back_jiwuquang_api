package com.example.back_jiwuquang_api.service.orders;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.back_jiwuquang_api.dto.orders.InseDeliveryDTO;
import com.example.back_jiwuquang_api.pojo.orders.OrdersDelivery;
import com.example.back_jiwuquang_api.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import com.example.back_jiwuquang_api.repository.orders.OrdersDeliveryMapper;
import com.example.back_jiwuquang_api.repository.orders.OrdersItemMapper;
import com.example.back_jiwuquang_api.repository.orders.OrdersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.back_jiwuquang_api.domain.constant.OrdersConstant.ORDERS_DELIVERY_MAPS_KEY;

@Service
@Slf4j
public class OrdersDeliveryService {

    @Autowired
    OrdersDeliveryMapper deliveryMapper;
    @Autowired
    OrdersMapper ordersMapper;
    @Autowired
    OrdersItemMapper ordersItemMapper;
    @Autowired
    RedisUtil redisUtil;

    /******************** 查询  *********************/
    /**
     * 获取订单发货信息
     *
     * @param orderId 订单id
     * @return Result Result
     */
    public OrdersDelivery selectByOrderId(String orderId) {
        OrdersDelivery delivery = (OrdersDelivery) redisUtil.hGet(ORDERS_DELIVERY_MAPS_KEY, orderId);
        if (delivery != null) {
            return delivery;
        }
        delivery = deliveryMapper.selectOne(new LambdaQueryWrapper<OrdersDelivery>().eq(OrdersDelivery::getOrdersId, orderId));
        if (delivery != null) {
            redisUtil.hPut(ORDERS_DELIVERY_MAPS_KEY, orderId, delivery);
        }
        return delivery;
    }

    /********************* 添加 *************************/
    public int insertDelivery(InseDeliveryDTO dto) {
        // sql
        OrdersDelivery delivery = InseDeliveryDTO.toOrdersDelivery(dto);
        int count = deliveryMapper.insert(delivery);
        // 缓存
        if (count != 0) redisUtil.hPut(ORDERS_DELIVERY_MAPS_KEY, dto.getOrdersId(), delivery);
        return count;
    }

    /********************* 修改 **************************/


    /********************* 删除 *************************/


}
