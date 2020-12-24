package com.abc.producer.test2;

import java.io.IOException;

public class TwoProducerTest {

    /**
     * 验证 异步回调发送
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        TwoProducer producer = new TwoProducer();
        producer.sendMsg();
        System.in.read();
    }
}



