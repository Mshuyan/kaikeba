package com.example.rocketmq.demo.commitLog;

import org.apache.rocketmq.common.message.MessageDecoder;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.File;
import java.nio.ByteBuffer;

public class ConsumerMessageRead {

    public static void main(String[] args) throws Exception {

        //consumerqueue根目录
        String consumerPath = "/Users/hadoop/store/consumequeue";
        //commitlog目录
        String commitLogPath = "/Users/hadoop/store/commitlog/00000000001073741824";
        //读取commitlog文件内容
        ByteBuffer commitLogBuffer = CommitLogRead.read(commitLogPath);

        //遍历consumerqueue目录下的所有文件
        File file = new File(consumerPath);
        File[] files = file.listFiles();
        for (File f:files) {
            if (f.isDirectory()){
                File[] listFiles = f.listFiles();
                for (File queuePath:listFiles) {
                    String path = queuePath+"/00000000000000000000";
                    //读取consumerqueue文件内容
                    ByteBuffer buffer = CommitLogRead.read(path);
                    while (true){
                        //读取消息偏移量和消息长度
                        long offset = (int) buffer.getLong();
                        int size = buffer.getInt();
                        long code = buffer.getLong();
                        if (size==0){
                            break;
                        }
                        //根据偏移量和消息长度，在commitloh文件中读取消息内容
                        MessageExt message = getMessageByOffset(commitLogBuffer,offset,size);
                        if (message!=null){
                            System.out.println("消息主题:"+message.getTopic()+" MessageQueue:"+
                                    message.getQueueId()+" 消息体:"+new String(message.getBody()));
                        }
                    }
                }
            }
        }
    }
    public static MessageExt getMessageByOffset(ByteBuffer commitLog,long offset,int size) throws Exception {
        ByteBuffer slice = commitLog.slice();
        slice.position((int)offset);
        slice.limit((int) (offset+size));
        MessageExt message = MessageDecoder.decode(slice);
        return message;
    }



}
