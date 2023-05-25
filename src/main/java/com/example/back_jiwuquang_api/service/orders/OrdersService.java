package com.example.back_jiwuquang_api.service.orders;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.back_jiwuquang_api.dto.orders.InsertOrderDTO;
import com.example.back_jiwuquang_api.dto.orders.InsertOrderItemDTO;
import com.example.back_jiwuquang_api.dto.orders.SelectOrderDTO;
import com.example.back_jiwuquang_api.pojo.goods.Goods;
import com.example.back_jiwuquang_api.pojo.goods.GoodsSku;
import com.example.back_jiwuquang_api.pojo.orders.Orders;
import com.example.back_jiwuquang_api.pojo.orders.OrdersItem;
import com.example.back_jiwuquang_api.pojo.sys.UserAddress;
import com.example.back_jiwuquang_api.repository.goods.GoodsSkuMapper;
import com.example.back_jiwuquang_api.repository.orders.OrdersItemMapper;
import com.example.back_jiwuquang_api.repository.orders.OrdersMapper;
import com.example.back_jiwuquang_api.repository.sys.UserAddressMapper;
import com.example.back_jiwuquang_api.util.RedisUtil;
import com.example.back_jiwuquang_api.util.Result;
import com.example.back_jiwuquang_api.vo.orders.OrderInfoVO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class OrdersService {

    @Autowired
    OrdersMapper ordersMapper;
    @Autowired
    OrdersItemMapper ordersItemMapper;
    @Autowired
    RedisUtil redisUtil;

    /** ******************* 查询 *********************** **/

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
    public Result getOrderPageByDTO(int page, int size, SelectOrderDTO selectOrderDTO, String userId, Integer status) {
        // 过滤
        if (status != null && (status < 0 || status > 7)) {
            return Result.fail(Result.PARAM_ERR, "状态码错误，状态码为0-7 ！");
        }
        // 查询
        IPage<OrderInfoVO> pageList = ordersMapper.selectOrderInfoPage(page, size, selectOrderDTO, userId, status);
        // Result
        return Result.ok(pageList);
    }


    /**
     * 缓存 完成的订单
     */
    public Result getOrderDonePageByDTO(int page, int size, SelectOrderDTO selectOrderDTO, String userId, Integer status) {
        // 查询
        IPage<OrderInfoVO> pageList = ordersMapper.selectOrderInfoPage(page, size, selectOrderDTO, userId, status);
        // Result
        return Result.ok(pageList);
    }


    /**
     * 获取订单详细信息
     *
     * @param userId 用户id
     * @param id     订单id
     * @return Result
     */
    public Result getOrderAllInfo(String userId, String id) {
        return Result.ok("获取成功！", ordersMapper.selectOrderInfo(userId, id));
    }

    /********************* 添加 提交 *************************/
    @Autowired
    GoodsSkuMapper goodsSkuMapper;
    @Autowired
    UserAddressMapper userAddressMapper;

    /**
     * 添加订单
     *
     * @param insertOrderDTO 参数
     * @param userId         用户id
     * @return Result
     */
    @Transactional(rollbackFor = Exception.class)
    public Result addOrderByDTO(InsertOrderDTO insertOrderDTO, String userId) {
        // 1、是否存在地址
        UserAddress address = userAddressMapper.selectById(insertOrderDTO.getAddressId());
        if (address == null || !address.getUserId().equals(userId)) {
            return Result.fail(Result.SELECT_ERR, "地址不存在！");
        }
        // 2、商品规格是否有库存
        List<String> skuList = new ArrayList<>();
        for (InsertOrderItemDTO p : insertOrderDTO.getItems()) {
            skuList.add(p.getSkuId());
        }
        List<GoodsSku> list = goodsSkuMapper.selectList(new LambdaQueryWrapper<GoodsSku>().select(GoodsSku::getStock).gt(GoodsSku::getStock, 0).in(GoodsSku::getId, skuList));
        if (list.size() <= skuList.size()) {
            return Result.fail(Result.SELECT_ERR, "部分商品下架或库存不足！");
        }
        // 3、减少库存
        // 4、生成订单

        return Result.ok("提交成功！", list);
    }


    /********************* 删除 取消 *************************/


    /**
     * 删除订单
     *
     * @param userId 用户id
     * @param id     订单id
     * @return Result
     */
    @Transactional(rollbackFor = Exception.class)
    public Result deleteOrderById(String userId, String id) {
        Orders orders = ordersMapper.selectOne(new LambdaQueryWrapper<Orders>().select(Orders::getStatus).eq(Orders::getUserId, userId).eq(Orders::getId, id));
        // 1、查询
        if (orders == null) return Result.fail(Result.NULL_ERR, "删除失败，无该订单！");
        // 判断状态
        Set<Integer> set = new HashSet<>();
        set.add(5);// 5表示已评价，6表示已取消，7表示已超时取消'
        set.add(6);
        set.add(7);
        if (!set.contains(orders.getStatus())) {
            log.warn("删除订单，状态不正确，可能有风险！o_id:{}", id);
            return Result.fail(Result.DELETE_ERR, "删除失败，该订单还无法删除！");
        }
        // 2、删除(子订单)
        int count = ordersItemMapper.delete(new LambdaQueryWrapper<OrdersItem>().eq(OrdersItem::getOrdersId, id));
        if (count <= 0) {
            log.error("删除失败，数据不一致！o_id:{}", id);
            throw new RuntimeException("删除失败，数据不一致！");
        }
        // 3、删除订单
        int mainCount = ordersMapper.delete(new LambdaQueryWrapper<Orders>().eq(Orders::getId, id));
        if (mainCount <= 0) {
            log.error("删除失败，数据不一致！o_id:{}", id);
            throw new RuntimeException("删除失败，数据不一致！");
        }
        return Result.ok("删除成功！", mainCount);
    }

}
