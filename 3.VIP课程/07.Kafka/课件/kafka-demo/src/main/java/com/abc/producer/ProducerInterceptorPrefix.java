package com.abc.producer;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;


/**
 * 生产者拦截器
 */
public class ProducerInterceptorPrefix implements ProducerInterceptor<String,String> {

    private volatile  long sendSuccess = 0;

    private volatile  long sendFailure = 0;

    /**
     * KafkaProducer在将消息序列化和计算分区之前会调用生产者拦截器的onSend()方法来对消息进行
     * 相应的定制化操作。一般来说最好不要修改ProducerRecord的topic、key和partition等消息。
     * 如果要修改，需确保对其有准确性的判断，否则会预想的效果出现偏差。
     * @param record
     * @return
     */

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {

        String modefiedValue = "prefix1-" + record.value();
        return new ProducerRecord<>(record.topic(),
                record.partition(),record.timestamp(),record.key(),
                modefiedValue,record.headers());
    }

    /**
     * Kafka会在消息被应答(Acknowledgement)之前或消息发送失败时调用生产者onAcknowledgement方法，
     * 优先于用户设定的Callback之前执行。这个方法运行在Producer的I/O线程中，所以这个方法中实现的代码逻辑越简单越好
     * 否则会影响消息的发送速度。
     * @param metadata
     * @param exception
     */
    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        if(exception==null){
            sendSuccess++;
        }else {
            sendFailure++;
        }
    }

    /**
     * close()方法主要用于关闭拦截器时执行一些资源的清理工作。
     * 在这3个方法中抛出的异常都会被记录到日志中，但不会再向上传递
     */
    @Override
    public void close() {
        double successRatio = (double) sendSuccess /(sendFailure + sendSuccess);
        System.out.println("[INFO] 发送成功率="+String.format("%f",successRatio * 100)+"%");
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
