package com.example.rocketmq.demo.simple;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageClientExt;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class MessageTest {

    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new
                DefaultMQProducer("please_rename_unique_group_name");
        // Specify name server addresses.
        producer.setNamesrvAddr("localhost:9876");
        //Launch the instance.
        producer.start();
        //根据UniqueKey查询
        String offsetMsgId = "0A14275400002A9F00000000001F268E";

        MessageExt msg = producer.viewMessage("TopicTest", offsetMsgId);
//打印结果：这里仅输出Unique Key与offsetMsgId
        MessageClientExt msgExt= (MessageClientExt) msg;
        System.out.println("Unique Key:"+msgExt.getMsgId()//即UNIQUE_KEY
                +"\noffsetMsgId:"+msgExt.getOffsetMsgId());
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
