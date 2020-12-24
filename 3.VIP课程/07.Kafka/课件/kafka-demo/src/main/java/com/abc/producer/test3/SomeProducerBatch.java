package com.abc.producer.test3;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;


import java.util.Properties;

public class SomeProducerBatch {
    // 第一个泛型：当前生产者所生产消息的key
    // 第二个泛型：当前生产者所生产的消息本身
    private KafkaProducer<Integer, String> producer;

    public SomeProducerBatch() {
        Properties properties = new Properties();
        // 指定kafka集群
        properties.put("bootstrap.servers", "127.0.0.1:9092,127.0.0.1:9093,127.0.0.1:9094");
        // 指定key与value的序列化器
        properties.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // 指定生产者每1024条消息向broker发送一次
        properties.put("batch.size", 1024);
        // 指定生产者每50ms向broker发送一次
        properties.put("linger.ms", 50);

        this.producer = new KafkaProducer<Integer, String>(properties);
    }

    public void sendMsg() {
        for(int i=0; i<10; i++) {
            String str = "beijing-"+i;
            ProducerRecord<Integer, String> record = new ProducerRecord<>("cities", 0,str);
            int k = i;

            producer.send(record, (metadata, ex) -> {
                System.out.println("i = " + k);
                System.out.println("topic = " + metadata.topic());
                System.out.println("partition = " + metadata.partition());
                System.out.println("offset = " + metadata.offset());
            });
        }
    }

}


















