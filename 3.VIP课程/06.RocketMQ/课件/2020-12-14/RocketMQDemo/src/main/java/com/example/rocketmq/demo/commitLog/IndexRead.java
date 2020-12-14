package com.example.rocketmq.demo.commitLog;

import java.nio.ByteBuffer;

public class IndexRead {
    public static void main(String[] args) throws Exception {

        //index索引文件的路径
        String path = "/Users/hadoop/store/index/20201124154930530";
        ByteBuffer buffer = CommitLogRead.read(path);
        //该索引文件中包含消息的最小存储时间
        long beginTimestamp = buffer.getLong();
        //该索引文件中包含消息的最大存储时间
        long endTimestamp = buffer.getLong();
        //该索引文件中包含消息的最大物理偏移量(commitlog文件偏移量)
        long beginPhyOffset = buffer.getLong();
        //该索引文件中包含消息的最大物理偏移量(commitlog文件偏移量)
        long endPhyOffset = buffer.getLong();
        //hashslot个数
        int hashSlotCount = buffer.getInt();
        //Index条目列表当前已使用的个数
        int indexCount = buffer.getInt();
        //==============================================//
        //500万个hash槽，每个槽占4个字节，存储的是index索引
        for (int i=0;i<5000000;i++){
            buffer.getInt();
        }
        //==============================================//
        //2000万个index条目
        for (int j=0;j<20000000;j++){
            //消息key的hashcode
            int hashcode = buffer.getInt();
            //消息对应的偏移量
            long offset = buffer.getLong();
            //消息存储时间和第一条消息的差值
            int timedif = buffer.getInt();
            //该条目的上一条记录的index索引
            int pre_no = buffer.getInt();
        }
        System.out.println("postion:"+buffer.position()+",capacity:"+buffer.capacity());
        System.out.println(buffer.position()==buffer.capacity());
    }


}
