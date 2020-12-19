package com.example.rocketmq.demo.commitLog;

import org.apache.rocketmq.common.message.MessageDecoder;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class CommitLogRead {

    public static void main(String[] args) throws Exception {
        String filePath = "/Users/hadoop/store/commitlog/00000000000000000000";

        ByteBuffer buffer = read(filePath);
        List<MessageExt> messageList = new ArrayList<>();
        while (true){
            MessageExt message = MessageDecoder.decode(buffer);
            if (message==null){
                break;
            }
            if(messageList.size()>=10){

                break;
            }
            messageList.add(message);
        }
        System.out.println(messageList.size());
        for (MessageExt ms:messageList) {
            if(ms.getBody()==null)continue;
            System.out.println("主题:"+ms.getTopic()+" 消息:"+
                    new String(ms.getBody())+"队列ID:"+ms.getQueueId()+" 存储地址:"+ms.getStoreHost());
        }
        System.out.println("==end==");

    }
    public static ByteBuffer read(String path)throws Exception{
        File file = new File(path);
        FileInputStream fin = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        fin.read(bytes);
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        return buffer;
    }


}
