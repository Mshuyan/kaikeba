package com.supergo.example.transaction;

import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName OrderTransactionProducer
 * @Description
 * @Author hubin
 * @Date 2020/7/6 18:33
 * @Version V1.0
 **/
public class OrderTransactionProducer {

    public static void main(String[] args) throws Exception {
        TransactionMQProducer producer = new TransactionMQProducer();
        producer.setNamesrvAddr("192.168.66.66:9876");
        producer.setProducerGroup("transaction_group_A");

        //自定义线程池,执行事务操作
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 50, 10L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(20), (Runnable r) -> new Thread("Order Transaction Massage Thread"));
        producer.setExecutorService(executor);

        //设置事务消息监听器
        producer.setTransactionListener(new OrderTransactionListener());

        producer.start();

        System.err.println("OrderService Start");

        for (int i = 0;i < 10;i++){
            String orderId = UUID.randomUUID().toString();
            String payload = "下单,orderId: " + orderId;
            String tags = "Tag";
            Message message = new Message("transaction_topic_A", tags, orderId, payload.getBytes(RemotingHelper.DEFAULT_CHARSET));

            //发送事务消息
            TransactionSendResult result = producer.sendMessageInTransaction(message, orderId);
            System.err.println("发送事务消息,发送结果: " + result);
        }
    }

}

