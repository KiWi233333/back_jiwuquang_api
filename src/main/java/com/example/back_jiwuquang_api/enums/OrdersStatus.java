package com.example.back_jiwuquang_api.enums;

import lombok.Getter;

/**
 * 描述
 *
 * @className: UserStatus
 * @author: Author作者
 * @description: TODO描述
 * @date: 2023/4/11 16:12
 */
@Getter
public enum OrdersStatus {

    /**
     * 订单状态
     * 0:待付款，
     * 1:已付款，
     * 2:已发货，
     * 3:已收货，
     * 4:已评价，
     * 5:已取消，
     * 6:已超时取消，
     * 7:发起退款，
     * 8:退款成功并取消，
     */
    UN_PAID("待付款", 0),// 0:待付款
    PAID("已付款", 1),// 1:已付款
    DELIVERED("已发货", 2),// 2:已发货
    RECEIVED("已收货", 3),// 3:已收货
    COMMENTED("已评价", 4),// 4:已评价
    CANCELED("已取消", 5),// 5:已取消
    DELAY_CANCELED("已超时取消", 6),// 6:已超时取消
    REFUND("发起退款", 7),// 7:发起退款
    REFUND_SUCCESS("退款成功并取消", 8),// 8:退款成功并取消
    MIN("范围0", 0),//
    MAX("范围8", 8);
    private final String key;
    private final Integer val;

    private OrdersStatus(String key, Integer val) {
        this.key = key;
        this.val = val;
    }



}
