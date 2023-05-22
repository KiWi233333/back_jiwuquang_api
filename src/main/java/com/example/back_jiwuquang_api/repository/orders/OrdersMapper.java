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
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface OrdersMapper extends SpiceBaseMapper<Orders>, MPJBaseMapper<Orders> {


    /**
     * 获取用户订单(分页)
     *
     * @param page           页码
     * @param size           个数
     * @param selectOrderDTO DTO
     * @param userId         用户id
     * @return Result
     */
    default IPage<OrderInfoVO> selectOrderInfoPage(int page, int size, SelectOrderDTO selectOrderDTO, String userId) {

        MPJLambdaWrapper<Orders> qw = new MPJLambdaWrapper<>();
        // 工具id查询
        if (selectOrderDTO.getId() != null) {
            qw.eq(Orders::getId, selectOrderDTO.getId()).last("limit 1");
        }
        // 时间筛选
        if (selectOrderDTO.getStartTime() != null || selectOrderDTO.getEndTime() != null) {
            if (selectOrderDTO.getStartTime().getTime() - selectOrderDTO.getEndTime().getTime() > 0) {
                return null;
            }
            qw.between(Orders::getCreateTime, selectOrderDTO.getStartTime(), selectOrderDTO.getEndTime());
        }
        qw.select(
                        Orders::getId,
                        Orders::getUserId,
                        Orders::getAddressId,
                        Orders::getOrdersTime,
                        Orders::getPaidTime,
                        Orders::getTotalPrice,
                        Orders::getStatus,
                        Orders::getCreateTime,
                        Orders::getUpdateTime
                )
//                .select(
//                OrdersItem::getSkuId,
//                OrdersItem::getQuantity,
//                OrdersItem::getReducePrice,
//                OrdersItem::getFinalPrice,
//                OrdersItem::getActivityId )
                .selectAssociation(OrdersItem.class, OrderInfoVO::getOrderItemVOList, map -> map
                        .result(OrdersItem::getSkuId)
                        .result(OrdersItem::getQuantity)
                        .result(OrdersItem::getReducePrice)
                        .result(OrdersItem::getFinalPrice)
                        .result(OrdersItem::getActivityId))
                .selectAssociation(Goods.class, Goods::getId, map -> map
                        .result(Goods::getName))
                .selectAssociation(GoodsSku.class, GoodsSku::getId, map -> map
                        .result(GoodsSku::getDescription)
                        .result(GoodsSku::getImage)
                        .result(GoodsSku::getColor)
                        .result(GoodsSku::getCombo)
                        .result(GoodsSku::getSize))
//                .select(
//                        GoodsSku::getDescription,
//                        GoodsSku::getImage,
//                        GoodsSku::getColor,
//                        GoodsSku::getCombo,
//                        GoodsSku::getSize)
                .innerJoin(OrdersItem.class, OrdersItem::getOrdersId, Orders::getId)
                .innerJoin(GoodsSku.class, GoodsSku::getId, OrdersItem::getSkuId)
                .innerJoin(Goods.class, Goods::getId, GoodsSku::getGoodsId)
                .groupBy(OrdersItem::getOrdersId);
        // 用户id
        qw.eq(Orders::getUserId, userId);
        Page<OrderInfoVO> pages = new Page<>(page, size);
        IPage<OrderInfoVO> pageList = this.selectJoinPage(pages, OrderInfoVO.class, qw); // 调用Mapper接口方法进行分页查询
        return pageList;
    }


}
