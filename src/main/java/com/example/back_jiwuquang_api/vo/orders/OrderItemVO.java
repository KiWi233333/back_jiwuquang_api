package com.example.back_jiwuquang_api.vo.orders;

import com.baomidou.mybatisplus.annotation.*;
import com.example.back_jiwuquang_api.pojo.goods.Goods;
import com.example.back_jiwuquang_api.pojo.goods.GoodsSku;
import com.example.back_jiwuquang_api.vo.EventGoodsVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.yulichang.annotation.FieldMapping;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单项目VO
 *
 * @className: UserInfoVo
 * @author: Kiwi23333
 * @description: 订单项目VO
 * @date: 2023/5/1 2:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderItemVO {

    private String skuId;

    private Integer quantity;

    private BigDecimal reducePrice;

    private BigDecimal finalPrice;

    private String activityId;

    private String shopId;

    private OrderGoodsVO goods;

    private OrderGoodsSkuVO goodsSku;
}
