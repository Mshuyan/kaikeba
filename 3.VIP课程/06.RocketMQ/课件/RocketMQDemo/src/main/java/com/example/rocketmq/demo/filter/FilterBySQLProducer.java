package com.example.rocketmq.demo.filter;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class FilterBySQLProducer {
    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new
                DefaultMQProducer("please_rename_unique_group_name");
        // Specify name server addresses.
        producer.setNamesrvAddr("localhost:9876");
        producer.start();
        String[] tags = {"TagA","TagB","TagC","TagD"};
        for (int i = 0; i < 10; i++) {
            try {
                String tag = tags[i % tags.length];
                //构建消息
                Message msg = new Message("TopicTest" /* Topic */,
                        tag /* Tag */,
                        ("RocketMQ消息测试，消息的TAG="+tag+  ", 属性 age = " + i + ", == " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
                );
                msg.putUserProperty("age", i+"");
                SendResult sendResult = producer.send(msg);
                System.out.printf("%s%n", sendResult);
            } catch (Exception e) {
                e.printStackTrace();
//                Thread.sleep(1000);
            }
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
