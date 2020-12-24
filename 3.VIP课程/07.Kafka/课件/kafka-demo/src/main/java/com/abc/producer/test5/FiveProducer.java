package com.abc.producer.test5;

import com.abc.producer.ProducerInterceptorPrefix;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class FiveProducer {

    private static final long EXPIRE_INTERVAL = 10 * 1000;

    // 第一个泛型：当前生产者所生产消息的key
    // 第二个泛型：当前生产者所生产的消息本身
    private KafkaProducer<Integer, String> producer;

    public FiveProducer() {
        Properties properties = new Properties();
        // 指定kafka集群
        properties.put("bootstrap.servers", "127.0.0.1:9092,127.0.0.1:9093,127.0.0.1:9094");
        // 指定key与value的序列化器
        properties.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");


        properties.put("acks", "all");// 记录完整提交，最慢的但是最大可能的持久化
//        properties.put("retries", 3);// 请求失败重试的次数
        properties.put(ProducerConfig.RETRIES_CONFIG, 3);
//        properties.put("retry.backoff.ms", 100);// 两次重试之间的时间间隔 默认为100ms
        properties.put("batch.size", 16384);// batch的大小  producer将试图批处理消息记录，以减少请求次数。这将改善client与server之间的性能。默认是16384Bytes，即16kB，也就是一个batch满了16kB就发送出去
        //如果batch太小，会导致频繁网络请求，吞吐量下降；如果batch太大，会导致一条消息需要等待很久才能被发送出去，而且会让内存缓冲区有很大压力，过多数据缓冲在内存里。

        properties.put("linger.ms", 1);// 默认情况即使缓冲区有剩余的空间，也会立即发送请求，设置一段时间用来等待从而将缓冲区填的更多，单位为毫秒，producer发送数据会延迟1ms，可以减少发送到kafka服务器的请求数据
        properties.put("buffer.memory", 33554432);// 提供给生产者缓冲内存总量,设置发送消息的缓冲区，默认值是33554432，就是32MB.
        // 如果发送消息出去的速度小于写入消息进去的速度，就会导致缓冲区写满，此时生产消息就会阻塞住，所以说这里就应该多做一些压测，尽可能保证说这块缓冲区不会被写满导致生产行为被阻塞住



        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, ProducerInterceptorPrefix.class.getName());

        this.producer = new KafkaProducer<Integer, String>(properties);

    }

    public void sendMsg() throws ExecutionException, InterruptedException {


       ProducerRecord<Integer, String> record = new ProducerRecord<>("cities", "shanghai");
        producer.send(record);


        producer.close();
    }
}


















