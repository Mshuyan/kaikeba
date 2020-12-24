package com.abc.producer.test5;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class FiveProducerTest {

    /**
     * 验证 生产者拦截器
     * @param args
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        FiveProducer producer = new FiveProducer();
        producer.sendMsg();
        System.in.read();
    }
}



