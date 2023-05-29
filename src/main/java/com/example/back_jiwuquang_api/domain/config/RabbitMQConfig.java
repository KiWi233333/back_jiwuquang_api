package com.example.back_jiwuquang_api.domain.config;

import com.example.back_jiwuquang_api.domain.constant.RabbitMQConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * RabbitMQ配置类
 *
 * @className: RabbitMQConfig
 * @author: Kiwi23333
 * @description: RabbitMQ配置类
 * @date: 2023/5/28 16:15
 */
@Configuration
public class RabbitMQConfig {
    /**
     * 订单队列
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue orderQueue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", 60000);
        arguments.put("x-dead-letter-exchange", RabbitMQConstant.ORDER_DLX_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", RabbitMQConstant.ORDER_DLX_ROUTING_KEY);
        Queue queue = new Queue(RabbitMQConstant.ORDER_QUEUE, true, false, false, arguments);
        return queue;
    }

    /**
     * 订单交换机
     *
     * @return {@link Exchange}
     */
    @Bean
    public Exchange orderExchange() {
        return new DirectExchange(RabbitMQConstant.ORDER_EXCHANGE, true, false, null);
    }

    /**
     * 订单路由键
     *
     * @return {@link Binding}
     */
    @Bean
    public Binding orderRouting() {
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with(RabbitMQConstant.ORDER_ROUTING_KEY).noargs();
    }

    /**
     * 订单死信队列
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue orderDlxQueue() {
        Queue queue = new Queue(RabbitMQConstant.ORDER_DLX_QUEUE, true, false, false);
        return queue;
    }

    /**
     * 订单死信交换机
     *
     * @return {@link Exchange}
     */
    @Bean
    public Exchange orderDlxExchange() {
        return new DirectExchange(RabbitMQConstant.ORDER_DLX_EXCHANGE, true, false, null);
    }

    /**
     * 订单死信路由键
     *
     * @return {@link Binding}
     */
    @Bean
    public Binding orderDlxRouting() {
        return BindingBuilder.bind(orderDlxQueue()).to(orderDlxExchange()).with(RabbitMQConstant.ORDER_DLX_ROUTING_KEY).noargs();
    }
}
