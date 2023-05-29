package com.example.back_jiwuquang_api.domain.constant;

/**
 * 描述
 *
 * @className: RabbitConstants
 * @author: Kiwi23333
 * @description: TODO描述
 * @date: 2023/5/28 16:21
 */
public class RabbitMQConstant {

    /**
     * 订单相关
     **/
    public final static String ORDER_DLX_EXCHANGE = "order:delay:dlx";// 交换机名称

    public static final String ORDER_DLX_ROUTING_KEY = "order:dlx:routing:key";// 交换机routingKey
    public static final String ORDER_QUEUE = "order:auto:cancel:queue";// 订单队列名称
    public static final String ORDER_ROUTING_KEY = "order:routing:routing:key";// routingKey
    public static final String ORDER_DLX_QUEUE = "order:dlx:queue";
    public static final String ORDER_EXCHANGE = "order:exchange";
}
