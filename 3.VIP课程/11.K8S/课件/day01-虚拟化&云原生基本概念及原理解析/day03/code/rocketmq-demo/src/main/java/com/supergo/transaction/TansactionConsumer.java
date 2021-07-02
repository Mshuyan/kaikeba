package com.supergo.transaction;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by on 2020/4/13.
 */
public class TansactionConsumer {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new
                        DefaultMQPushConsumer("HAOKE_CONSUMER");
        consumer.setNamesrvAddr("172.16.55.185:9876");
        // 订阅topic，接收此Topic下的所有消息
        consumer.subscribe("pay_topic", "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                              ConsumeConcurrentlyContext context) {
            for (MessageExt msg : msgs) {
            try {
                System.out.println(new String(msg.getBody(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
           }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
}
}
