package com.example.back_jiwuquang_api.service.orders;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.back_jiwuquang_api.dto.orders.InsertOrderDTO;
import com.example.back_jiwuquang_api.dto.orders.InsertOrderItemDTO;
import com.example.back_jiwuquang_api.dto.orders.SelectOrderDTO;
import com.example.back_jiwuquang_api.pojo.goods.GoodsSku;
import com.example.back_jiwuquang_api.pojo.orders.Orders;
import com.example.back_jiwuquang_api.pojo.orders.OrdersItem;
import com.example.back_jiwuquang_api.pojo.pay.UserWallet;
import com.example.back_jiwuquang_api.pojo.sys.UserAddress;
import com.example.back_jiwuquang_api.repository.goods.GoodsSkuMapper;
import com.example.back_jiwuquang_api.repository.orders.OrdersItemMapper;
import com.example.back_jiwuquang_api.repository.orders.OrdersMapper;
import com.example.back_jiwuquang_api.repository.pay.UserWalletMapper;
import com.example.back_jiwuquang_api.repository.sys.UserAddressMapper;
import com.example.back_jiwuquang_api.util.RedisUtil;
import com.example.back_jiwuquang_api.util.Result;
import com.example.back_jiwuquang_api.vo.orders.OrderInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    @Autowired
    UserWalletMapper userWalletMapper;

    /**
     * 添加订单
     *
     * @param insertOrderDTO 参数
     * @param userId         用户id
     * @return Result
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public Result addOrderByDTO(InsertOrderDTO insertOrderDTO, String userId) {
        // 1、是否存在地址
        UserAddress address = userAddressMapper.selectOne(new LambdaQueryWrapper<UserAddress>().eq(UserAddress::getUserId, userId).eq(UserAddress::getId, insertOrderDTO.getAddressId()));
        if (address == null || !address.getUserId().equals(userId)) {
            return Result.fail(Result.SELECT_ERR, "地址不存在！");
        }
        // 2、商品规格是否有库存
        List<String> skuIds = new ArrayList<>();
        for (InsertOrderItemDTO p : insertOrderDTO.getItems()) {
            skuIds.add(p.getSkuId());
        }
        //      查询商品规格list
        List<GoodsSku> skuList = goodsSkuMapper.selectList(new LambdaQueryWrapper<GoodsSku>().gt(GoodsSku::getStock, 0).in(GoodsSku::getId, skuIds));
        if (skuList.size() < skuIds.size()) {
            return Result.fail(Result.SELECT_ERR, "部分商品下架或库存不足！");
        }
        // 3、减少库存sql
        BigDecimal costPrice = new BigDecimal(0); // 原价
        BigDecimal reducePrice = new BigDecimal(0);// 优惠
        BigDecimal finalPrice; // 最终金钱
        int updateCount = 0;
        //      1)计算原总价、更新库存
        for (int i = 0; i < skuList.size(); i++) {
            int quantity = skuList.get(i).getStock() - insertOrderDTO.getItems().get(i).getQuantity();
            if (quantity < 0) {
                log.warn("提交订单失败，{}", quantity);
                throw new RuntimeException("提交订单失败，部分无库存！");
            }
            // 计算价格
            costPrice = costPrice.add(skuList.get(i).getPrice().multiply(BigDecimal.valueOf(insertOrderDTO.getItems().get(i).getQuantity())));// 计算原总价
            updateCount += goodsSkuMapper.updateById(new GoodsSku().setId(skuList.get(i).getId()).setStock(quantity));// 更新库存
        }
        if (updateCount < skuIds.size()) {
            throw new RuntimeException("提交订单失败，暂无库存！");
        }
        //      2)计算优惠、使用优惠
        if (insertOrderDTO.getPoints() != null && insertOrderDTO.getPoints() > 0) {
            UserWallet wallet = userWalletMapper.selectOne(new LambdaQueryWrapper<UserWallet>().eq(UserWallet::getUserId,userId).last("limit 1"));
        }
        // 优惠卷

        //      3)计算总价 = 原总价 - 优惠价格
        finalPrice = costPrice.subtract(reducePrice);
        // 4、生成订单
        Orders orders = new Orders()
                .setUserId(userId)
                .setAddressId(insertOrderDTO.getAddressId())
                .setRemark(insertOrderDTO.getRemark())
                .setStatus(0)
                .setTotalPrice(finalPrice);
        if (ordersMapper.insert(orders) <= 0) {
            return Result.fail(Result.INSERT_ERR, "提交订单失败！");
        }
        // 5、构造订单项目实体类
        List<OrdersItem> ordersItemList = new ArrayList<>();
        for (int i = 0; i < insertOrderDTO.getItems().size(); i++) {
            InsertOrderItemDTO itemDTO = insertOrderDTO.getItems().get(i);
            // 总价
            BigDecimal reduceItem = new BigDecimal(0);
            BigDecimal costItem = skuList.get(i).getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity()));
            ordersItemList.add(InsertOrderItemDTO.toOrdersItem(itemDTO)
                    .setOrdersId(orders.getId())
                    // 优惠价
                    .setReducePrice(reduceItem)
                    // 子订单总价
                    .setFinalPrice(costItem.subtract(reduceItem)));
        }
        // 6、插入订单项
        if (ordersItemMapper.insertBatchSomeColumn(ordersItemList) < skuIds.size()) {
            throw new RuntimeException("插入订单项目错误！");
        }

        // 7、提交成功
        return Result.ok("提交成功！", null);
    }

    /********************* 修改订单 *************************/


    /********************* 删除 取消 *************************/


    /**
     * 删除订单
     *
     * @param userId 用户id
     * @param id     订单id
     * @return Result
     */
    @Transactional(rollbackFor = RuntimeException.class)
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
