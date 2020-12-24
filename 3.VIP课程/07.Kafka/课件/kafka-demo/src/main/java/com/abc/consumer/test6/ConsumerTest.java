package com.abc.consumer.test6;

public class ConsumerTest {
    /**
     * 验证消费者拦截器
     * @param args
     */
    public static void main(String[] args) {
        SomeConsumer consumer = new SomeConsumer();
        consumer.doWork();
    }
}


