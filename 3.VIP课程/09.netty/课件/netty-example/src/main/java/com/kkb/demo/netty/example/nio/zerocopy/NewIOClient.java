package com.kkb.demo.netty.example.nio.zerocopy;

import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;

import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {
   private static String filename = "/Users/hadoop/Desktop/JVM.zip";

    /**
     * 使用 NIO 零拷贝方式传递（transferTo）一个大文件
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 7001));
        //得到一个文件channel
        FileChannel fileChannel = new FileInputStream(filename).getChannel();
        //准备发送
        long startTime = System.currentTimeMillis();

        //transferTo 底层使用到零拷贝
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送的总的字节数 = " + transferCount + " 耗时: " + (System.currentTimeMillis() - startTime));

        //关闭
        fileChannel.close();
    }

    /**
     * 使用传统的 IO 方法传递一个大文件
     * @param args
     * @throws Exception
     */
    public static void main3(String[] args) throws Exception {
        File file = new File(filename);
        RandomAccessFile raf = new RandomAccessFile(file, "rw");

        byte[] arr = new byte[(int) file.length()];
        raf.read(arr);

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 7001));
        //准备发送
        long startTime = System.currentTimeMillis();
        socketChannel.socket().getOutputStream().write(arr);
        System.out.println("发送的总的字节数 = " + arr.length + " 耗时: " + (System.currentTimeMillis() - startTime));

    }
}
