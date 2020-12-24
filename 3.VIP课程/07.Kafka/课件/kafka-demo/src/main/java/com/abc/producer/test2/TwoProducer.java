package com.abc.producer.test2;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class TwoProducer {
    // 第一个泛型：当前生产者所生产消息的key
    // 第二个泛型：当前生产者所生产的消息本身
    private KafkaProducer<Integer, String> producer;

    public TwoProducer() {
        Properties properties = new Properties();
        // 指定kafka集群
        properties.put("bootstrap.servers", "127.0.0.1:9092,127.0.0.1:9093,127.0.0.1:9094");
        // 指定key与value的序列化器
        properties.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");


        this.producer = new KafkaProducer<Integer, String>(properties);
    }

    public void sendMsg() {
        // 创建消息记录（包含主题、消息本身）  (String topic, V value)
        // ProducerRecord<Integer, String> record = new ProducerRecord<>("cities", "tianjin");
        // 创建消息记录（包含主题、key、消息本身）  (String topic, K key, V value)
        // ProducerRecord<Integer, String> record = new ProducerRecord<>("cities", 1, "tianjin");
        // 创建消息记录（包含主题、partition、key、消息本身）  (String topic, Integer partition, K key, V value)

            ProducerRecord<Integer, String> record = new ProducerRecord<>("cities", 1, 1, "tianjin");
            producer.send(record, (metadata, ex) -> {
                System.out.println("ok");
                System.out.println("topic = " + metadata.topic());
                System.out.println("partition = " + metadata.partition());
                System.out.println("offset = " + metadata.offset());
            });


//        producer.send(record, new Callback() {
//            @Override
//            public void onCompletion(RecordMetadata metadata, Exception exception) {
//                System.out.println("ok");
//                System.out.println("topic = " + metadata.topic());
//                System.out.println("partition = " + metadata.partition());
//                System.out.println("offset = " + metadata.offset());
//            }
//        });



    }
}


















