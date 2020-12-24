package com.abc.producer;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

//todo:需求：自定义kafka的分区函数
public class MyPartitioner implements Partitioner{
    /**
     * 通过这个方法来实现消息要去哪一个分区中
     * @param topic
     * @param key
     * @param bytes
     * @param value
     * @param bytes1
     * @param cluster
     * @return
     */
    public int partition(String topic, Object key, byte[] bytes, Object value, byte[] bytes1, Cluster cluster) {
            //获取topic分区数
            int partitions = cluster.partitionsForTopic(topic).size();

            //key.hashCode()可能会出现负数 -1 -2 0 1 2
            //Math.abs 取绝对值
            return Math.abs(key.hashCode()% partitions);

    }

    public void close() {

    }

    public void configure(Map<String, ?> map) {

    }
}
