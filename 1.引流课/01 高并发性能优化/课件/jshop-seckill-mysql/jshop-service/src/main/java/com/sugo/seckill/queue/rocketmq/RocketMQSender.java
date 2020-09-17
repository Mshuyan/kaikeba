package com.sugo.seckill.queue.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class RocketMQSender {

	private static final String producerGroup = "seckillGroup";
	private static final String namesrvAddr = "192.168.66.66:9876";
	private DefaultMQProducer producer;

	@PostConstruct
	public void initProducer() {
		producer = new DefaultMQProducer(producerGroup);
		producer.setNamesrvAddr(namesrvAddr);
		producer.setRetryTimesWhenSendFailed(3);
		try {
			producer.start();
			System.out.println("[Producer 已启动]");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String send(String topic, String tags, String msg) {
		SendResult result = null;
		try {
			Message message = new Message(topic, tags, msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
			result = producer.send(message);
			System.out.println("[Producer] msgID(" + result.getMsgId() + ") " + result.getSendStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{\"MsgId\":\"" + result.getMsgId() + "\"}";
	}

	@PreDestroy
	public void shutDownProducer() {
		if (producer != null) {
			producer.shutdown();
		}
	}

}
