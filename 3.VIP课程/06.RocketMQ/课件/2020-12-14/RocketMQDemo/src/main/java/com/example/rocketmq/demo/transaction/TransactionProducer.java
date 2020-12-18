package com.example.rocketmq.demo.transaction;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.TimeUnit;

public class TransactionProducer {
    public static void main(String[] args) throws Exception {
        //1.创建消息生产者producer，并制定生产者组名
        TransactionMQProducer producer = new TransactionMQProducer("GroupTransaction");
        //2.指定nameserver地址
        producer.setNamesrvAddr("172.17.102.46:9876;172.17.102.46:9877");
        //3.添加事务监听器
        producer.setTransactionListener(new TransactionListener() {
            /**
             * 在该方法执行本地事务
             * @param msg
             * @param arg
             * @return
             */
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                System.out.println("executeLocal:"+msg.getTags());
                if(StringUtils.equals("TAGA",msg.getTags())){
                    return LocalTransactionState.COMMIT_MESSAGE;
                }else if(StringUtils.equals("TAGB",msg.getTags())){
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }else if(StringUtils.equals("TAGC",msg.getTags())){
                    return LocalTransactionState.UNKNOW;
                }
                return LocalTransactionState.UNKNOW;
            }

            /**
             * 该方法用于MQ进行消息的回查
             * @param msg
             * @return
             */
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                System.out.println("checkLocalTransaction:"+msg.getTags());
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });

        //4.启动producer
        producer.start();
        String[] tags = {"TAGA","TAGB","TAGC"};
        for (int i = 0; i < 3; i++) {
            //5.创建消息对象，制定topic、tag 和 消息体
            Message msg = new Message("TransactionTopic", tags[i],
                    ("Hello RocketMQ: " + tags[i]).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );

            //6.发送消息
            SendResult sendResult = producer.sendMessageInTransaction(msg,null);
            //7.获取发送状态
            SendStatus sendStatus = sendResult.getSendStatus();
            System.out.printf("发送结果:%s%n", sendStatus);
            TimeUnit.SECONDS.sleep(1);
        }
        //8.关闭生产者producer
//        producer.shutdown();
    }
}
