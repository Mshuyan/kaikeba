package com.supergo.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueByConfig;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.Collections;
import java.util.List;

/**
 * Created by on 2019/9/9.
 */
public class RocketMQConsumerDemo {

    public static void main(String[] args) throws Exception{

        //创建消费者
        DefaultMQPushConsumer consumer =
                new DefaultMQPushConsumer("group-java");
        //设置broker 地址
        consumer.setNamesrvAddr("192.168.66.66:9876");


        consumer.subscribe("topicB", "*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {

            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

                for (MessageExt messageExt : list) {

                    System.out.println("接受消息：队列ID==>"+messageExt.getQueueId() + " 消息Id:" + messageExt.getMsgId());
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();

       //System.in.read();

    }

}


