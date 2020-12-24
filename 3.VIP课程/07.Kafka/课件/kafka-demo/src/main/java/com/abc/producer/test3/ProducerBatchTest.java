package com.abc.producer.test3;

import java.io.IOException;

public class ProducerBatchTest {

    /**
     * 验证 批量发送消息
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        SomeProducerBatch producer = new SomeProducerBatch();
        producer.sendMsg();
        System.in.read();
    }
}



