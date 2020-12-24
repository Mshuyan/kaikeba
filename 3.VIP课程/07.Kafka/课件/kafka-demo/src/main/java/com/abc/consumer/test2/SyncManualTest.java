package com.abc.consumer.test2;

public class SyncManualTest {
    public static void main(String[] args) {
        SyncManualConsumer consumer = new SyncManualConsumer();
//        consumer.doWork();
        //每个分区单独处理
//        consumer.doWorkAllPartition();
        //指定分区消息
        consumer.doWorkPartition();
    }
}


