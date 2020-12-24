package com.abc.consumer.test4;

import java.io.IOException;

public class SyncAsyncManualTest {
    public static void main(String[] args) throws IOException {
        SyncAsyncManualConsumer consumer = new SyncAsyncManualConsumer();
        consumer.doWork();
        System.in.read();
    }
}


