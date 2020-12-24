package com.abc.consumer.test2;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.*;

public class SyncManualConsumer  {
    private KafkaConsumer<Integer, String> consumer;

    public SyncManualConsumer() {
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

        // 开启手动提交
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        // 指定自动提交的超时时限，默认5s
        // properties.put("auto.commit.interval.ms", "1000");

        // 指定一次poll()读取多少条消息，默认500
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500);

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
        consumer.subscribe(Collections.singletonList("cities"));
        // 从broker摘取消费。参数表示，若buffer中没有消费，消费者等待消费的时间。
        // 0，表示没有消息什么也不返回
        // >0，表示当时间到后仍没有消息，则返回空
        while(true){
            ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofMillis(1000));
            for(ConsumerRecord record : records) {
                System.out.println("topic2 = " + record.topic());
                System.out.println("partition2 = " + record.partition());
                System.out.println("key2 = " + record.key());
                System.out.println("value2 = " + record.value());
                // 如果业务执行成功，手动同步提交。反之，不执行提交，达到失败的场景，可以进行重试的效果
                consumer.commitSync();


//            consumer.commitAsync();

            }
        }

    }

    //分区消费
    public void doWorkAllPartition() {
        consumer.subscribe(Collections.singletonList("cities"));
        while(true){
            ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofMillis(1000));

            //每个Partition单独处理
            for(TopicPartition partition:records.partitions()){

                System.out.println("=======partition="+partition+" start================");

                List<ConsumerRecord<Integer,String>> pRecord =records.records(partition);

                for(ConsumerRecord record : pRecord) {
                    System.out.println("topic3 = " + record.topic());
                    System.out.println("partition3 = " + record.partition());
                    System.out.println("key3 = " + record.key());
                    System.out.println("value3 = " + record.value());
                    consumer.commitSync();
                }

                long lastOffset = pRecord.get(pRecord.size()-1).offset();
                //单个Partition的中的offset提交，并且进行提交
                Map<TopicPartition, OffsetAndMetadata> offset = new HashMap<>();
                offset.put(partition,new OffsetAndMetadata(lastOffset+1));
                consumer.commitSync(offset);

                System.out.println("=======partition="+partition+" end================");
            }


        }

    }

    public void doWorkPartition() {
        TopicPartition p0 = new TopicPartition("cities",0);
        TopicPartition p1 = new TopicPartition("cities",1);
        TopicPartition p2 = new TopicPartition("cities",2);

//        consumer.seek(p0,100); //重置offset，比如可以保存在Redis

        consumer.assign(Arrays.asList(p0));
        while(true){
            ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofMillis(1000));

            //每个Partition单独处理
            for(TopicPartition partition:records.partitions()){

                System.out.println("=======part="+partition+" start================");



                  /*
                        1、接收到record信息以后，去令牌桶中拿取令牌
                        2、如果获取到令牌，则继续业务处理
                        3、如果获取不到令牌， 则pause等待令牌
                        4、当令牌桶中的令牌足够， 则将consumer置为resume状态
                     */
                 // consumer.pause(Arrays.asList(p0)); //消费暂停
                 // consumer.resume(Arrays.asList(p0)); //消费继续

                List<ConsumerRecord<Integer,String>> pRecord =records.records(partition);

                for(ConsumerRecord record : pRecord) {
                    System.out.println("topic3 = " + record.topic());
                    System.out.println("partition3 = " + record.partition());
                    System.out.println("key3 = " + record.key());
                    System.out.println("value3 = " + record.value());
                    consumer.commitSync();
                }

                long lastOffset = pRecord.get(pRecord.size()-1).offset();
                //单个Partition的中的offset提交，并且进行提交
                Map<TopicPartition, OffsetAndMetadata> offset = new HashMap<>();
                offset.put(partition,new OffsetAndMetadata(lastOffset+1));
                consumer.commitSync(offset);

                System.out.println("=======part="+partition+" end================");
            }


        }

    }
}
