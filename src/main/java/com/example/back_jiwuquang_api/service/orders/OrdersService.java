package com.example.back_jiwuquang_api.service.orders;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
     * 获取全部订单(分页)
     *
     * @param page           页码
     * @param size           个数
     * @param selectOrderDTO DTO
     * @param userId         用户id
     * @param status         状态：0:待付款，1:未发货，2:已发货，3:待收货，4:已收货，5:已评价，6:已取消，7:已超时取消',
     * @return Result
     */
    public Result getOrderPageByDTO(int page, int size, SelectOrderDTO selectOrderDTO, String userId,Integer status) {
        // 查询
        IPage<OrderInfoVO> pageList = ordersMapper.selectOrderInfoPage(page, size, selectOrderDTO, userId,status);
        // Result
        return Result.ok(pageList);
    }


    /**
     * 缓存 完成的订单
     */
    public Result getOrderDonePageByDTO(int page, int size, SelectOrderDTO selectOrderDTO, String userId,Integer status) {
        // 查询
        IPage<OrderInfoVO> pageList = ordersMapper.selectOrderInfoPage(page, size, selectOrderDTO, userId,status);
        // Result
        return Result.ok(pageList);
    }

    /**
     * 删除订单
     * @param userId 用户id
     * @param id 订单id
     * @return Result
     */
    public Result deleteOrderById(String userId, String id) {
        //
        int count1 = ordersMapper.delete(new LambdaQueryWrapper<Orders>().eq(Orders::getId,id).eq(Orders::getStatus,0));
        if (count1<=0) {
            Result.fail(Result.DELETE_ERR,"删除失败！");
        }


        return Result.ok("删除成功！");
    }
}
