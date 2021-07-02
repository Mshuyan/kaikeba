package com.supergo.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueByConfig;
import org.apache.rocketmq.client.consumer.store.OffsetStore;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.Collections;
import java.util.List;

/**
 * Created by on 2019/9/9.
 */
public class RocketMQConsumer {

    public static void main(String[] args) throws Exception{

        //创建消费者
        DefaultMQPushConsumer consumer =
                new DefaultMQPushConsumer("E");
        //设置broker 地址
        consumer.setNamesrvAddr("192.168.66.66:9876");

        consumer.subscribe("jackhu-01", "*");

        //设置消费策略
        consumer.setAllocateMessageQueueStrategy(new AllocateMessageQueueByConfig(){
            {
                this.setMessageQueueList(Collections.singletonList(new MessageQueue(){{
                    this.setQueueId(0);
                    this.setTopic("jackhu");
                    this.setBrokerName("jackhu-a");
                }}));
            }
        });

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

                for (MessageExt messageExt : list) {

                    System.out.println("接受消息："+messageExt.getQueueId());
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();

//        System.in.read();

    }

}


