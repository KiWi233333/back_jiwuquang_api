package com.example.back_jiwuquang_api.repository.orders;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.back_jiwuquang_api.dto.orders.SelectOrderDTO;
import com.example.back_jiwuquang_api.pojo.goods.Goods;
import com.example.back_jiwuquang_api.pojo.goods.GoodsSku;
import com.example.back_jiwuquang_api.pojo.orders.Orders;
import com.example.back_jiwuquang_api.pojo.orders.OrdersItem;
import com.example.back_jiwuquang_api.repository.SpiceBaseMapper;
import com.example.back_jiwuquang_api.vo.orders.OrderInfoVO;
import com.example.back_jiwuquang_api.vo.orders.OrderItemVO;
import com.github.yulichang.base.MPJBaseMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;

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

        // 店铺id查询
        if (selectOrderDTO.getShopId() != null) {
            qw.like(OrdersItem::getShopId, selectOrderDTO.getShopId());
        }
        // 时间筛选
        if (selectOrderDTO.getStartTime() != null || selectOrderDTO.getEndTime() != null) {
            if (selectOrderDTO.getStartTime().getTime() - selectOrderDTO.getEndTime().getTime() > 0) {
                return null;
            }
            qw.between(Orders::getCreateTime, selectOrderDTO.getStartTime(), selectOrderDTO.getEndTime());
        }
        // sql
        qw.select(
                Orders::getId,
                Orders::getUserId,
                Orders::getAddressId,
                Orders::getRemark,
                Orders::getOrdersTime,
                Orders::getPaidTime,
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
        // 用户id
        qw.eq(Orders::getUserId, userId);
        Page<OrderInfoVO> pages = new Page<>(page, size);
        // result
        return this.selectJoinPage(pages, OrderInfoVO.class, qw);
    }


    default OrderInfoVO selectOrderInfo(String userId, String id) {
        MPJLambdaWrapper<Orders> qw = new MPJLambdaWrapper<>();
        // sql
        qw.select(
                Orders::getId,
                Orders::getUserId,
                Orders::getAddressId,
                Orders::getRemark,
                Orders::getOrdersTime,
                Orders::getPaidTime,
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
                        .association(Goods.class, OrderItemVO::getGoods)
                        .association(GoodsSku.class, OrderItemVO::getGoodsSku)
                )
                .leftJoin(OrdersItem.class, OrdersItem::getOrdersId, Orders::getId)
                .leftJoin(GoodsSku.class, GoodsSku::getId, OrdersItem::getSkuId)
                .leftJoin(Goods.class, Goods::getId, GoodsSku::getGoodsId);
        // 2、用户id
        qw.eq(Orders::getUserId, userId)
                .eq(Orders::getId, id).last("limit 1");
        // 3、result
        return this.selectJoinOne(OrderInfoVO.class, qw);
    }
}
