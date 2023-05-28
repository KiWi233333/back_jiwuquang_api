package com.example.back_jiwuquang_api.repository.orders;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.back_jiwuquang_api.dto.orders.SelectAllUserOrderDTO;
import com.example.back_jiwuquang_api.dto.orders.SelectOrderDTO;
import com.example.back_jiwuquang_api.enums.OrdersStatus;
import com.example.back_jiwuquang_api.pojo.goods.Goods;
import com.example.back_jiwuquang_api.pojo.goods.GoodsSku;
import com.example.back_jiwuquang_api.pojo.orders.Orders;
import com.example.back_jiwuquang_api.pojo.orders.OrdersItem;
import com.example.back_jiwuquang_api.pojo.sys.UserAddress;
import com.example.back_jiwuquang_api.repository.SpiceBaseMapper;
import com.example.back_jiwuquang_api.vo.orders.OrderInfoVO;
import com.example.back_jiwuquang_api.vo.orders.OrderItemVO;
import com.github.yulichang.base.MPJBaseMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;

@Mapper
public interface OrdersMapper extends SpiceBaseMapper<Orders>, MPJBaseMapper<Orders> {


    /**
     * 获取用户订单(分页)
     *
     * @param page           页码
     * @param size           个数
     * @param selectOrderDTO DTO
     * @param userId         用户id
     * @param status         状态：0:待付款，1:已付款，2:已发货，3:待收货，4:已收货，5:已评价，6:已取消，7:已超时取消',
     * @return Result
     */
    default IPage<OrderInfoVO> selectOrderInfoPage(int page, int size, SelectOrderDTO selectOrderDTO, String userId, Integer status) {

        MPJLambdaWrapper<Orders> qw = new MPJLambdaWrapper<>();
        // 订单id查询
        if (selectOrderDTO.getId() != null) {
            qw.like(Orders::getId, selectOrderDTO.getId());
        }

        // 状态查询
        if (status != null) {
            qw.eq(Orders::getStatus, status);
        }
        // 用户id
        if (StringUtils.isNotBlank(userId)) {
            qw.eq(Orders::getUserId, userId);
        }


        // 店铺id查询
        if (selectOrderDTO.getShopId() != null) {
            qw.like(OrdersItem::getShopId, selectOrderDTO.getShopId());
        }

        // 关键字查询
        if (StringUtils.isNotBlank(selectOrderDTO.getName())) {
            qw.like(Goods::getName, selectOrderDTO.getName());
            qw.like(Goods::getDescription, selectOrderDTO.getName());
        }

        // 时间筛选
        if (selectOrderDTO.getStartTime() != null || selectOrderDTO.getEndTime() != null) {
            if (selectOrderDTO.getStartTime().getTime() - selectOrderDTO.getEndTime().getTime() <= 0) {
                return new Page<>(page, size);
            }
            qw.between(Orders::getCreateTime, selectOrderDTO.getStartTime(), selectOrderDTO.getEndTime());
        }

        return selectOrderPageByQw(page, size, qw);
    }

    default IPage<OrderInfoVO> selectOrderInfoPage(int page, int size, SelectAllUserOrderDTO dto) {

        MPJLambdaWrapper<Orders> qw = new MPJLambdaWrapper<>();
        // 订单id查询
        if (dto.getId() != null) {
            qw.like(Orders::getId, dto.getId());
        }
        // 状态查询
        if (dto.getStatus() != null) {
            qw.eq(Orders::getStatus, dto.getStatus());
        }

        // 用户id
        if (StringUtils.isNotBlank(dto.getUserId())) {
            qw.eq(Orders::getUserId, dto.getUserId());
        }

        // 店铺id查询
        if (dto.getShopId() != null) {
            qw.like(OrdersItem::getShopId, dto.getShopId());
        }

        // 销售额筛选
        if (dto.getPriceSort() != null && (dto.getPriceSort().equals(0) || dto.getPriceSort().equals(1))) {
            if (dto.getPriceSort().equals(1)) {// 降序
                qw.orderByDesc(Orders::getTotalPrice);
            } else {// 升序
                qw.orderByAsc(Orders::getTotalPrice);
            }
        }

        // 时间筛选
        if (dto.getStartTime() != null || dto.getEndTime() != null) {
            if (dto.getStartTime().getTime() - dto.getEndTime().getTime() <= 0) {
                return new Page<>(page, size);
            }
            qw.between(Orders::getCreateTime, dto.getStartTime(), dto.getEndTime());
        }
        return selectOrderPageByQw(page, size, qw);
    }


    // 分页查询 （通用方法，分页结果后缀）
    default IPage<OrderInfoVO> selectOrderPageByQw(int page, int size, MPJLambdaWrapper<Orders> qw) {
        // sql
        qw.select(
                        Orders::getId,
                        Orders::getUserId,
                        Orders::getAddressId,
                        Orders::getRemark,
                        Orders::getPaidTime,
                        Orders::getSpendPrice,
                        Orders::getTotalPrice,
                        Orders::getStatus,
                        Orders::getCreateTime,
                        Orders::getUpdateTime
                )
                .selectCollection(OrdersItem.class, OrderInfoVO::getOrdersItems, map -> map
                        .result(OrdersItem::getSkuId)
                        .result(OrdersItem::getQuantity)
                        .result(OrdersItem::getReducePrice)
                        .result(OrdersItem::getFinalPrice)
                        .result(OrdersItem::getActivityId)
                        .result(OrdersItem::getShopId)
                        .result(OrdersItem::getCouponId)
                        .association(Goods.class, OrderItemVO::getGoods)
                        .association(GoodsSku.class, OrderItemVO::getGoodsSku)
                )
                .leftJoin(OrdersItem.class, OrdersItem::getOrdersId, Orders::getId)
                .leftJoin(GoodsSku.class, GoodsSku::getId, OrdersItem::getSkuId)
                .leftJoin(Goods.class, Goods::getId, GoodsSku::getGoodsId);
        // 分页器
        Page<OrderInfoVO> pages = new Page<>(page, size);
        // result
        return this.selectJoinPage(pages, OrderInfoVO.class, qw);
    }

    /**
     * 查询订单详细信息
     *
     * @param userId   用户id
     * @param id       订单id
     * @param statuses 状态集合
     * @return OrderInfoVO
     */
    default OrderInfoVO selectOrderInfo(String userId, String id, Integer... statuses) {
        MPJLambdaWrapper<Orders> qw = new MPJLambdaWrapper<>();
        // sql
        // 订单属性
        qw.select(
                        Orders::getId,
                        Orders::getUserId,
                        Orders::getRemark,
                        Orders::getPaidTime,
                        Orders::getSpendPrice,
                        Orders::getTotalPrice,
                        Orders::getStatus,
                        Orders::getCreateTime,
                        Orders::getUpdateTime
                )
                // 商品和商品规格属性
                .selectCollection(OrdersItem.class, OrderInfoVO::getOrdersItems, map -> map
                        .result(OrdersItem::getSkuId)
                        .result(OrdersItem::getQuantity)
                        .result(OrdersItem::getReducePrice)
                        .result(OrdersItem::getFinalPrice)
                        .result(OrdersItem::getActivityId)
                        .result(OrdersItem::getShopId)
                        .association(Goods.class, OrderItemVO::getGoods)
                        .association(GoodsSku.class, OrderItemVO::getGoodsSku)
                )
                // 地址信息
                .selectAssociation(UserAddress.class, OrderInfoVO::getUserAddress)
                .leftJoin(UserAddress.class, UserAddress::getId, Orders::getAddressId)
                .leftJoin(OrdersItem.class, OrdersItem::getOrdersId, Orders::getId)
                .leftJoin(GoodsSku.class, GoodsSku::getId, OrdersItem::getSkuId)
                .leftJoin(Goods.class, Goods::getId, GoodsSku::getGoodsId);
        // 2、用户id
        qw.eq(Orders::getUserId, userId)
                .eq(Orders::getId, id).last("limit 1");
        // 状态
        if (statuses != null) {
            qw.in(Orders::getStatus, statuses);
        }
        // 3、result
        return this.selectJoinOne(OrderInfoVO.class, qw);
    }

    /**
     * 获取默认订单信息
     *
     * @param userId 用户id
     * @param id     订单id
     * @return Result
     */
    default OrderInfoVO selectOrderInfo(String userId, String id) {
        return this.selectOrderInfo(userId, id, null);
    }
}
