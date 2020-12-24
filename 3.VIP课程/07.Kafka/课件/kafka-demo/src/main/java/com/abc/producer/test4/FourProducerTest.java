package com.abc.producer.test4;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class FourProducerTest {


    /**
     * 验证 同步发送(异步阻塞发送)
     * @param args
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        FourProducer producer = new FourProducer();
        producer.sendMsg();
        System.in.read();
    }
}



