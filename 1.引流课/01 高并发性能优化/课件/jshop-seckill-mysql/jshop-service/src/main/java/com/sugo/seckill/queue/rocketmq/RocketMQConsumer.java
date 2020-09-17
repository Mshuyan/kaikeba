package com.sugo.seckill.queue.rocketmq;


import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class RocketMQConsumer {

	private static final String consumerGroup = "seckillGroup";
	private static final String namesrvAddr = "192.168.66.66:9876";

	@Bean
	public DefaultMQPushConsumer defaultMQPushConsumer() {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
		consumer.setNamesrvAddr(namesrvAddr);
		try {
			//广播模式消费
			//consumer.setMessageModel(MessageModel.BROADCASTING);
			consumer.subscribe("TopicTest1", "*");

			// 如果是第一次启动，从队列头部开始消费
			// 如果不是第一次启动，从上次消费的位置继续消费
			consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
			consumer.registerMessageListener((MessageListenerConcurrently) (list, context) -> {
				try {
					for (MessageExt messageExt : list) {
						String messageBody = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
						System.out.println("[Consumer] msgID(" + messageExt.getMsgId() + ") msgBody : " + messageBody);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return ConsumeConcurrentlyStatus.RECONSUME_LATER;
				}
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			});
			consumer.start();
			System.out.println("[Consumer 已启动]");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return consumer;
	}
}
