package com.abc.producer.test6;




public class SixProducerTest {

    /**
     * 验证消费者拦截器
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        SixeProducer producer = new SixeProducer();
        producer.sendMsg();
        System.in.read();
    }
}



