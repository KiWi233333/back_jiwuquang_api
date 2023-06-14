package com.example.back_jiwuquang_api.service.orders;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.back_jiwuquang_api.dto.orders.*;
import com.example.back_jiwuquang_api.enums.BillsStatus;
import com.example.back_jiwuquang_api.enums.OrdersStatus;
import com.example.back_jiwuquang_api.pojo.goods.Goods;
import com.example.back_jiwuquang_api.pojo.goods.GoodsSku;
import com.example.back_jiwuquang_api.pojo.orders.Orders;
import com.example.back_jiwuquang_api.pojo.orders.OrdersDelivery;
import com.example.back_jiwuquang_api.pojo.orders.OrdersItem;
import com.example.back_jiwuquang_api.pojo.pay.UserBills;
import com.example.back_jiwuquang_api.pojo.pay.UserWallet;
import com.example.back_jiwuquang_api.pojo.sys.UserAddress;
import com.example.back_jiwuquang_api.repository.goods.GoodsMapper;
import com.example.back_jiwuquang_api.repository.goods.GoodsSkuMapper;
import com.example.back_jiwuquang_api.repository.orders.OrdersItemMapper;
import com.example.back_jiwuquang_api.repository.orders.OrdersMapper;
import com.example.back_jiwuquang_api.repository.pay.UserWalletMapper;
import com.example.back_jiwuquang_api.repository.sys.UserAddressMapper;
import com.example.back_jiwuquang_api.service.pay.UserBillsService;
import com.example.back_jiwuquang_api.service.sys.UserWalletService;
import com.example.back_jiwuquang_api.util.RedisUtil;
import com.example.back_jiwuquang_api.util.Result;
import com.example.back_jiwuquang_api.vo.orders.CancelOrdersVO;
import com.example.back_jiwuquang_api.vo.orders.OrderInfoVO;
import com.example.back_jiwuquang_api.vo.orders.OrderItemVO;
import com.example.back_jiwuquang_api.vo.user.RefundWalletVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.example.back_jiwuquang_api.domain.constant.OrdersConstant.*;
import static com.example.back_jiwuquang_api.domain.constant.UserConstant.USER_WALLET_KEY;

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
     * @param page   页码
     * @param size   个数
     * @param dto    SelectOrderDTO
     * @param userId 用户id
     * @param status 状态：0:待付款，1:未发货，2:已发货，3:待收货，4:已收货，5:已评价，6:已取消，7:已超时取消',
     * @return Result
     */
    public Result getOrderPageByDTO(int page, int size, SelectOrderDTO dto, String userId, Integer status) {
        IPage<OrderInfoVO> pageList;
        try {
            pageList = (IPage<OrderInfoVO>) redisUtil.hGet(ORDERS_PAGES_KEY + userId, status + ":" + page + ":" + size + ":" + dto);
            if (pageList != null) return Result.ok(pageList);
        } catch (Exception e) {
            log.warn("select error {}", e.getMessage());
            return Result.fail(Result.SELECT_ERR, "查询失败！");
        }
        // 过滤
        if (status != null && (status < 0 || status > 7)) {
            return Result.fail(Result.PARAM_ERR, "状态码错误，状态码为0-7 ！");
        }
        // 查询
        pageList = ordersMapper.selectOrderInfoPage(page, size, dto, userId, status);
        // Result
        if (pageList != null && pageList.getRecords().size() > 0) {
            redisUtil.hPut(ORDERS_PAGES_KEY + userId, status + ":" + page + ":" + size + ":" + dto, pageList);
        }
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
        OrderInfoVO orderInfoVO;
        // 1、redis
        try {
            orderInfoVO = (OrderInfoVO) redisUtil.hGet(ORDERS_MAPS_KEY + userId, id);
            if (orderInfoVO != null) return Result.ok("获取成功！", orderInfoVO);
        } catch (Exception e) {
            log.warn("select error {}", e.getMessage());
        }
        // 2、sql
        orderInfoVO = ordersMapper.selectOrderInfo(userId, id);
        if (orderInfoVO != null) {
            redisUtil.hPut(ORDERS_MAPS_KEY + userId, id, orderInfoVO);
        }
        return Result.ok("获取成功！", orderInfoVO);
    }

    /********************* 添加 提交 *************************/
    @Autowired
    GoodsSkuMapper goodsSkuMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    UserAddressMapper userAddressMapper;
    @Autowired
    UserWalletService userWalletService;
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
            int newStock = skuList.get(i).getStock() - insertOrderDTO.getItems().get(i).getQuantity();
            if (newStock < 0) {
                log.warn("提交订单失败，{}", newStock);
                throw new RuntimeException("提交订单失败，部分无库存！");
            }
            // 运费
            Goods goods = goodsMapper.selectOne(new LambdaQueryWrapper<Goods>().eq(Goods::getId, skuList.get(i).getGoodsId()));
            costPrice.add(goods.getPostage() != null ? goods.getPostage() : costPrice);
            // 计算原价格
            costPrice = costPrice.add(skuList.get(i).getPrice().multiply(BigDecimal.valueOf(insertOrderDTO.getItems().get(i).getQuantity())));// 计算原总价
            // 更新库存 --
            updateCount += goodsSkuMapper.reduceSkuStock(skuList.get(i).getId(), insertOrderDTO.getItems().get(i).getQuantity());// 更新库存
        }
        if (updateCount < skuIds.size()) {
            throw new RuntimeException("提交订单失败，暂无库存！");
        }
        // 优惠卷


        //      3)计算总价 = 原总价 - 优惠价格
        finalPrice = costPrice.subtract(reducePrice);
        // 4、生成订单
        Orders orders = new Orders().setUserId(userId).setAddressId(insertOrderDTO.getAddressId()).setRemark(insertOrderDTO.getRemark()).setStatus(0).setTotalPrice(finalPrice);
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
            ordersItemList.add(InsertOrderItemDTO.toOrdersItem(itemDTO).setOrdersId(orders.getId())
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
        redisUtil.delete(ORDERS_PAGES_KEY + userId);// 订单详情
        redisUtil.hDelete(ORDERS_MAPS_KEY + userId, orders.getId());
//        onDelayQuene();//
        return Result.ok("提交成功，请在30分钟内付款！", orders.getId());
    }

    /********************* 修改订单 *************************/


    /**
     * 修改订单 (状态为0 1 可修改)
     * 订单状态，0表示待付款，1:已付款，2:已发货，3:已收货，4:已评价，5:已取消，6:已超时取消，7:发起退款，8:退款成功并取消
     *
     * @param id     订单id
     * @param userId 用户id
     * @param dto    参数
     * @return Result
     */
    public Result updateOrderByDTO(String id, String userId, UpdateOrderDTO dto) {
        if (userAddressMapper.selectOne(new LambdaQueryWrapper<UserAddress>().select(UserAddress::getId).eq(UserAddress::getUserId, userId).eq(UserAddress::getId, dto.getAddressId()).last("limit 1")) == null) {
            return Result.fail(Result.NULL_ERR, "修改的地址不存在！");
        }
        Orders orders = new Orders().setAddressId(dto.getAddressId()).setRemark(dto.getRemark());
        if (ordersMapper.update(orders, new LambdaQueryWrapper<Orders>().in(Orders::getStatus, 0, 1).eq(Orders::getId, id).eq(Orders::getUserId, userId)) < 0) {
            return Result.fail(Result.INSERT_ERR, "修改失败！");
        }
        redisUtil.delete(ORDERS_PAGES_KEY + userId);
        redisUtil.hDelete(ORDERS_MAPS_KEY + userId, id);
        return Result.ok("修改成功！", null);
    }


    /**
     * 支付订单
     *
     * @param id          订单id
     * @param userId      用户id
     * @param payOrderDTO 支付订单参数
     * @return Result
     */
    public Result payOrderDTO(String id, String userId, PayOrderDTO payOrderDTO) throws RuntimeException {
        Result res;
        // 枚举支付方式
        switch (payOrderDTO.getType()) {
            case 0:
                res = payOrderByWallet(id, userId, payOrderDTO);
                break;
            /****/
            default:
                res = Result.fail(Result.PARAM_ERR, "无该支付方式");
                break;
        }
        return res;
    }

    @Autowired
    UserBillsService billsService;

    /**
     * 1）钱包支付
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public Result payOrderByWallet(String id, String userId, PayOrderDTO payOrderDTO) {
        // 1、sql query 订单信息
        OrderInfoVO orderInfoVO = (OrderInfoVO) getOrderAllInfo(userId, id).getData();
        if (orderInfoVO == null) {
            return Result.fail(Result.LINK_NULL_ERR, "订单不存在！");
        }
        if (orderInfoVO.getStatus() != 0) {
            return Result.fail(Result.UPDATE_ERR, "订单已经支付！");
        }
        // 2、获取价格
        BigDecimal totalPrice = orderInfoVO.getTotalPrice();
        // 3、sql query 钱包
        UserWallet wallet = (UserWallet) userWalletService.getUserWalletById(userId).getData();
        // 1）积分
        if (wallet.getPoints() < payOrderDTO.getPoints()) {
            return Result.fail(Result.UPDATE_ERR, "支付失败，积分不足！");
        }
        BigDecimal spendPrice = totalPrice.subtract(BigDecimal.valueOf(payOrderDTO.getPoints() / 100.0));
        // 2）代金卷
        if (StringUtils.isNotBlank(payOrderDTO.getVoucherId())) {// 不为空
            BigDecimal voucherPrice = new BigDecimal(0);
            spendPrice = spendPrice.subtract(voucherPrice);
        }
        // 比较 <
        if (wallet.getBalance().compareTo(spendPrice) < 0) {
            return Result.fail(Result.UPDATE_ERR, "支付失败，余额不足！");
        }
        // 3、修改用户钱包
        int flag = userWalletMapper.reduceWallet(userId, spendPrice, BigDecimal.valueOf(payOrderDTO.getPoints()));
        if (flag <= 0) {
            throw new RuntimeException("支付失败！");
        }
        // 4、修改用户代金卷
        /**修改用户代金卷**/

        // 生成账单
        // 1)余额
        billsService.saveBills(userId, 0, BillsStatus.SHOP_OUT.getVal(), id, 0, spendPrice, null);
        // 2)积分
        if (payOrderDTO.getPoints() != 0) {
            billsService.saveBills(userId, 0, BillsStatus.POINT_OUT.getVal(), id, 1, BigDecimal.valueOf(payOrderDTO.getPoints()), null);
        }
        if (payOrderDTO.getVoucherId() != null) {
            /**用户代金卷消费**/
        }

        // 5、修改订单
        if (ordersMapper.updateById(new Orders().setId(id).setStatus(OrdersStatus.PAID.getVal()).setSpendPrice(spendPrice).setPaidTime(new Date())) <= 0) {
            throw new RuntimeException("支付失败，订单状态错误！");
        }

        // 清空缓存
        redisUtil.delete(ORDERS_PAGES_KEY + userId);// 订单列表
        redisUtil.delete(USER_WALLET_KEY + userId); // 钱包
        redisUtil.hDelete(ORDERS_MAPS_KEY + userId, id);// 订单详情
        return Result.ok("支付成功！", spendPrice);
    }

    /**
     * 取消订单
     *
     * @param userId 用户id
     * @param id     订单id
     * @return Result
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public Result cancelOrderById(String userId, String id) {
        CancelOrdersVO cancelOrdersVO = new CancelOrdersVO();
        // 1、查看是否存在（待付款）订单
        OrderInfoVO orders = ordersMapper.selectOrderInfo(userId, id, OrdersStatus.UN_PAID.getVal());
        if (orders == null) {
            return Result.fail(Result.SELECT_ERR, "查询无该订单！");
        }
        // 2、sql取消订单
        int flag = ordersMapper.updateById(new Orders().setId(id).setStatus(OrdersStatus.CANCELED.getVal()));
        if (flag < 0) {
            return Result.fail(Result.UPDATE_ERR, "取消失败！");
        }
        int upCount = 0;
        for (OrderItemVO p : orders.getOrdersItems()) {
            // 3、恢复库存
            upCount += goodsSkuMapper.addSkuStock(p.getSkuId(), p.getQuantity());
            // 4、退回商品优惠卷
            /****/
        }

        if (upCount < orders.getOrdersItems().size()) {
            throw new RuntimeException("取消失败，库存错误！");
        }
        // 5、redis删除缓存
        redisUtil.delete(ORDERS_PAGES_KEY + userId);
        redisUtil.hDelete(ORDERS_MAPS_KEY + userId, id);
        cancelOrdersVO.setId(id);
        return Result.ok("取消成功！", cancelOrdersVO);
    }


    /**
     * 确认收货
     *
     * @param userId  用户id
     * @param orderId 订单orderId
     * @return Result
     */
    public Result deliveredOrderById(String userId, String orderId) {
        // 1、查看是否存订单是否存在！（待收货）
        OrderInfoVO orders = ordersMapper.selectOrderInfo(userId, orderId, OrdersStatus.DELIVERED.getVal());
        if (orders == null) {
            return Result.fail(Result.SELECT_ERR, "查无发货订单！");
        }
        // 2、sql确认收货
        int flag = ordersMapper.updateById(new Orders().setId(orderId).setStatus(OrdersStatus.RECEIVED.getVal()));
        if (flag < 0) {
            return Result.fail(Result.UPDATE_ERR, "确认收货失败！");
        }
        // 3、redis删除缓存
        redisUtil.delete(ORDERS_PAGES_KEY + userId);
        redisUtil.hDelete(ORDERS_MAPS_KEY + userId, orderId);
        return Result.ok("确认成功！", null);
    }

    /**
     * 申请退款
     *
     * @param userId 用户id
     * @param id     订单id
     * @return Result
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public Result sendRefundOrderById(String userId, String id) {
        // 1、查看是否存在（付款（未发货）：1，已经到货并签收不满意）：3 订单   || status = 1、3
        OrderInfoVO orders = ordersMapper.selectOrderInfo(userId, id, OrdersStatus.PAID.getVal(), OrdersStatus.RECEIVED.getVal());
        if (orders == null) {
            return Result.fail(Result.SELECT_ERR, "查询无该订单！");
        }
        // 2、sql发起退款订单
        int flag = ordersMapper.updateById(new Orders().setId(id).setStatus(OrdersStatus.REFUND.getVal()));
        if (flag < 0) {
            return Result.fail(Result.UPDATE_ERR, "申请退款失败！");
        }

        // 4、退款成功
        redisUtil.delete(ORDERS_PAGES_KEY + userId);// 订单分页缓存
        redisUtil.hDelete(ORDERS_MAPS_KEY + userId, id);// 订单信息缓存
        return Result.ok("申请成功，请等待处理！", null);
    }


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
        // 清空缓存
        redisUtil.delete(ORDERS_PAGES_KEY + userId);
        redisUtil.hDelete(ORDERS_MAPS_KEY + userId, id);
        return Result.ok("删除成功！", mainCount);
    }


    /******************管理员**********************/

    /**
     * 管理员
     *
     * @param page 页码
     * @param size 个数
     * @param dto  SelectAllUserOrderDTO
     * @return Result
     */
    public Result getAdminOrderPageByDTO(int page, int size, SelectAllUserOrderDTO dto) {
        IPage<OrderInfoVO> pageList = null;
        try {
            pageList = (IPage<OrderInfoVO>) redisUtil.get(ORDERS_PAGES_KEY + ":" + page + ":" + size + ":" + dto);
            if (pageList != null) {
                redisUtil.expire(ORDERS_PAGES_KEY + ":" + page + ":" + size + ":" + dto, 24, TimeUnit.HOURS);
                return Result.ok(pageList);
            }
        } catch (Exception e) {
            log.warn("select error {}", e.getMessage());
            return Result.fail(Result.SELECT_ERR, "查询失败！");
        }
        // 过滤
        if (dto.getStatus() != null && (dto.getStatus() < OrdersStatus.MIN.getVal() || dto.getStatus() > 7)) {
            return Result.fail(Result.PARAM_ERR, "状态码错误，状态码为0-7 ！");
        }
        // 查询
        pageList = ordersMapper.selectOrderInfoPage(page, size, dto);
        // Result
        if (pageList.getRecords().size() > 0) {
            redisUtil.set(ORDERS_PAGES_KEY + ":" + page + ":" + size + ":" + dto, pageList, 36, TimeUnit.HOURS);
        }
        return Result.ok(pageList);
    }


    /**
     * 同意退款
     *
     * @param userId  用户id
     * @param orderId 订单orderId
     * @return Result
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public Result refundOrderById(String userId, String orderId) {
        // 1、查看是否发起退款和验证订单是否存在！
        OrderInfoVO orders = ordersMapper.selectOrderInfo(userId, orderId, OrdersStatus.PAID.getVal(), OrdersStatus.REFUND.getVal());
        if (orders == null) {
            return Result.fail(Result.SELECT_ERR, "查询无该订单！");
        }
        // 2、退还金额和积分等
        // 1）余额和积分
        List<UserBills> billsList = billsService.getBillsOutByOrder(userId, orderId);
        BigDecimal price = new BigDecimal(0);
        BigDecimal points = new BigDecimal(0);
        for (UserBills bills : billsList) {
            if (bills.getCurrencyType() == 0) {// 余额
                price = price.add(bills.getAmount());
            } else if (bills.getCurrencyType() == 1) {// 积分
                points = points.add(bills.getAmount());
            }
        }
        if (userWalletMapper.addWallet(userId, price, points) < 0) {// 钱包刷新
            throw new RuntimeException("钱款返回失败！");
        }

        /**2）代金卷 **/


        // 3、添加收入账单
        if (!billsService.saveBills(userId, 1, BillsStatus.SHOP_IN.getVal(), orderId, 0, price, null) ||
                !billsService.saveBills(userId, 1, BillsStatus.POINT_IN.getVal(), orderId, 1, points, null)
        ) {
            throw new RuntimeException("新的账单生成失败！");
        }
        // 4、修改订单状态 为已经退款成功
        int flag = ordersMapper.updateById(new Orders().setId(orderId)
                .setStatus(OrdersStatus.REFUND_SUCCESS.getVal()));
        if (flag < 0) {
            return Result.fail(Result.UPDATE_ERR, "同意退款失败！");
        }
        // 4、退款成功
        redisUtil.delete(ORDERS_PAGES_KEY + userId);// 订单分页缓存
        redisUtil.hDelete(ORDERS_MAPS_KEY + userId, orderId);// 订单信息缓存
        redisUtil.delete(USER_WALLET_KEY + userId);// 钱包信息缓存
        return Result.ok("退款成功，请查看钱包！", new RefundWalletVO().setPrice(price).setPoints(points));
    }


    /****************** 发货模块 **********************/
    @Autowired
    OrdersDeliveryService deliveryService;

    /**
     * 获取发货信息
     *
     * @param userId  用户id
     * @param orderId 订单id
     * @return Result
     */
    public Result getOrderDeliveryByOrderId(String userId, String orderId) {
        // 1、service
        OrdersDelivery delivery = deliveryService.selectByOrderId(orderId);
        if (delivery == null) {
            return Result.fail(Result.SELECT_ERR, "订单还没有发货信息！");
        }
        // 2、success
        return Result.ok("获取成功！", delivery);
    }

    /**
     * 添加发货
     *
     * @param userId  用户id
     * @param orderId 订单id
     * @param orderId 订单id
     * @return Result
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public Result addDeliveryByOrderId(String userId, String orderId, DeliveryDTO dto) {
        // 1、查询订单状态
        // 1)缓存
        Orders orders = (Orders) redisUtil.hGet(ORDERS_MAPS_KEY + userId, orderId);
        if (orders == null || !orders.getStatus().equals(OrdersStatus.PAID.getVal())) {
            // 2)sql
            orders = ordersMapper.selectOne(new LambdaQueryWrapper<Orders>()
                    .select(Orders::getId)
                    .eq(Orders::getId, orderId)
                    .eq(Orders::getId, orderId).eq(Orders::getStatus, OrdersStatus.PAID.getVal()));
        }
        if (orders == null) {
            return Result.fail(Result.SELECT_ERR, "发货失败，订单状态已更新！");
        }
        // 2、插入发货表
        int count = deliveryService.insertDelivery(dto.setOrdersId(orderId));
        if (count <= 0) {
            return Result.fail(Result.INSERT_ERR, "发货失败，快递信息错误！");
        }
        // 3、修改订单状态
        if (ordersMapper.update(
                new Orders().setStatus(OrdersStatus.DELIVERED.getVal()),
                new LambdaQueryWrapper<Orders>()
                        .eq(Orders::getStatus, OrdersStatus.PAID)
                        .eq(Orders::getId, orderId)
                        .eq(Orders::getUserId, userId)) < 0
        ) {
            throw new RuntimeException("订单状态修改失败！");
        }
        // result
        return Result.ok("添加发货成功！", count);
    }


}
