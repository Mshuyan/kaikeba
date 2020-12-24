package com.abc.consumer.test1;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class SomeConsumer  {
    private KafkaConsumer<Integer, String> consumer;

    public SomeConsumer() {
        // 两个参数：
        // 1)指定当前消费者名称
        // 2)指定消费过程是否会被中断


        Properties properties = new Properties();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"KafkaConsumerTest");
        String brokers = "127.0.0.1:9092,127.0.0.1:9093,127.0.0.1:9094";
        // 指定kafka集群
        properties.put("bootstrap.servers", brokers);
        // 指定消费者组ID
        properties.put("group.id", "cityGroup1");
        // 开启自动提交，默认为true
        properties.put("enable.auto.commit", "true");
        // 设置一次poll()从broker读取多少条消息
        properties.put("max.poll.records", "500");
        // 指定自动提交的超时时限，默认5s
        properties.put("auto.commit.interval.ms", "1000");
        // 指定消费者被broker认定为挂掉的时限。若broker在此时间内未收到当前消费者发送的心跳，则broker
        // 认为消费者已经挂掉。默认为10s
        properties.put("session.timeout.ms", "30000");
        // 指定两次心跳的时间间隔，默认为3s，一般不要超过session.timeout.ms的 1/3
        properties.put("heartbeat.interval.ms", "10000");
        // 当kafka中没有指定offset初值时，或指定的offset不存在时，从这里读取offset的值。其取值的意义为：
        // earliest:指定offset为第一条offset
        // latest: 指定offset为最后一条offset
        properties.put("auto.offset.reset", "earliest");
        // 指定key与value的反序列化器
        properties.put("key.deserializer",
                "org.apache.kafka.common.serialization.IntegerDeserializer");
        properties.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");



        this.consumer = new KafkaConsumer<Integer, String>(properties);
    }

    public void doWork() {
        // 订阅消费主题
        // consumer.subscribe(Collections.singletonList("cities"));

        consumer.subscribe(Arrays.asList("cities", "test"));

        // 从broker获取消息。参数表示，若buffer中没有消息，消费者等待消费的时间。
        // 0，表示没有消息什么也不返回
        // >0，表示当时间到后仍没有消息，则返回空
        while (true) {
            ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofMillis(100));
            for(ConsumerRecord record : records) {
                System.out.println("topic = " + record.topic());
                System.out.println("partition = " + record.partition());
                System.out.println("key = " + record.key());
                System.out.println("value = " + record.value());
            }
        }

    }
}
