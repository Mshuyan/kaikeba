package com.abc.producer.test1;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class OneProducer {

    private static final long EXPIRE_INTERVAL = 10 * 1000;

    // 第一个泛型：当前生产者所生产消息的key
    // 第二个泛型：当前生产者所生产的消息本身
    private KafkaProducer<Integer, String> producer;

    public OneProducer() {
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


        properties.put("partitioner.class","com.abc.producer.MyPartitioner");

        /**
         * 请求超时
         * * ==max.request.size==
         *   * 这个参数用来控制发送出去的消息的大小，默认是1048576字节，也就1mb
         *   * 这个一般太小了，很多消息可能都会超过1mb的大小，所以需要自己优化调整，把他设置更大一些（企业一般设置成10M）
         * * ==request.timeout.ms==
         *   * 这个就是说发送一个请求出去之后，他有一个超时的时间限制，默认是30秒
         *   * 如果30秒都收不到响应，那么就会认为异常，会抛出一个TimeoutException来让我们进行处理
         *
         *重试乱序
         * max.in.flight.requests.per.connection
         *
         * * 每个网络连接已经发送但还没有收到服务端响应的请求个数最大值
         * 消息重试是可能导致消息的乱序的，因为可能排在你后面的消息都发送出去了，你现在收到回调失败了才在重试，此时消息就会乱序，
         * 所以可以使用“max.in.flight.requests.per.connection”参数设置为1，这样可以保证producer必须把一个请求发送的数据发送成功了再发送后面的请求。避免数据出现乱序
         */

        this.producer = new KafkaProducer<Integer, String>(properties);
//        ProducerRecord<Integer, String> record3 = new ProducerRecord<>("cities", "tianjin");

    }

    public void sendMsg() throws ExecutionException, InterruptedException {
        // 创建消息记录（包含主题、消息本身）  (String topic, V value)
        // ProducerRecord<Integer, String> record = new ProducerRecord<>("cities", "tianjin");
        // 创建消息记录（包含主题、key、消息本身）  (String topic, K key, V value)
        // ProducerRecord<Integer, String> record = new ProducerRecord<>("cities", 1, "tianjin");
        // 创建消息记录（包含主题、partition、key、消息本身）  (String topic, Integer partition, K key, V value)


       ProducerRecord<Integer, String> record = new ProducerRecord<>("cities", 0, 1, "hangzhou");
        producer.send(record);


        producer.close();
    }
}


















