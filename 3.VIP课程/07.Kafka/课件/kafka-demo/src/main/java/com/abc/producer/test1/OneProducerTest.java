package com.abc.producer.test1;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class OneProducerTest {

    /**
     * 验证 异步发送  消息
     * @param args
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        OneProducer producer = new OneProducer();
        producer.sendMsg();
        System.in.read();
    }
}



