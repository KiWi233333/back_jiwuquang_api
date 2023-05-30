package com.example.back_jiwuquang_api.service.orders;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.back_jiwuquang_api.dto.orders.DeliveryDTO;
import com.example.back_jiwuquang_api.dto.orders.InsertOrderCommentDTO;
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

import java.util.List;

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

    /********************* 添加 *************************/
    /**
     * 添加订单评价
     *
     * @param userId  用户id
     * @param dtoList List<InsertOrderCommentDTO>
     * @return Result
     */
    public Result addOrdersCommentByOrderItemId(String userId, List<InsertOrderCommentDTO> dtoList) {


        return Result.ok("发表评价成功！！", null);
    }


}
