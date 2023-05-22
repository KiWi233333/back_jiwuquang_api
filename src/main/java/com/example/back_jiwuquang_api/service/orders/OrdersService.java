package com.example.back_jiwuquang_api.service.orders;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.back_jiwuquang_api.dto.orders.SelectOrderDTO;
import com.example.back_jiwuquang_api.pojo.goods.Goods;
import com.example.back_jiwuquang_api.pojo.goods.GoodsSku;
import com.example.back_jiwuquang_api.pojo.orders.Orders;
import com.example.back_jiwuquang_api.pojo.orders.OrdersItem;
import com.example.back_jiwuquang_api.repository.orders.OrdersItemMapper;
import com.example.back_jiwuquang_api.repository.orders.OrdersMapper;
import com.example.back_jiwuquang_api.util.RedisUtil;
import com.example.back_jiwuquang_api.util.Result;
import com.example.back_jiwuquang_api.vo.orders.OrderInfoVO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersService {

    @Autowired
    OrdersMapper ordersMapper;
    @Autowired
    OrdersItemMapper ordersItemMapper;
    @Autowired
    RedisUtil redisUtil;


    /**
     * 获取用户订单(分页)
     *
     * @param page           页码
     * @param size           个数
     * @param selectOrderDTO DTO
     * @param userId         用户id
     * @return Result
     */
    public Result getOrderPageByDTO(int page, int size, SelectOrderDTO selectOrderDTO, String userId) {
        // 缓存
//        IPage<OrderInfoVO> pageList = null;
        // 查询
        IPage<OrderInfoVO> pageList = ordersMapper.selectOrderInfoPage(page, size, selectOrderDTO, userId);
        // Result
        return Result.ok(pageList);
    }
}
