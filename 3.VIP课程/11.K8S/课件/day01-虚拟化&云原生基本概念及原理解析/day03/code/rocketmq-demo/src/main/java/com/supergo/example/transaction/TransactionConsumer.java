package com.supergo.example.transaction;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName TransactionConsumer
 * @Description
 * @Author hubin
 * @Date 2020/7/5 21:59
 * @Version V1.0
 **/
public class TransactionConsumer {
    public static void main(String[] args) throws InterruptedException, MQClientException {

        // 实例化消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name");

        // 设置NameServer的地址
        consumer.setNamesrvAddr("192.168.66.66:9876");
        consumer.subscribe("transaction_topic_A","*");
        // 注册回调实现类来处理从broker拉取回来的消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                Optional.ofNullable(msgs).orElse(Collections.emptyList()).forEach(m -> {
                    String orderId = m.getKeys();
                    System.err.println("监听到下单消息,orderId: " + orderId + ", 商品服务减库存");
                });
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动消费者实例
        consumer.start();
        System.out.printf("TransactionConsumer Started.%n");
    }
}

