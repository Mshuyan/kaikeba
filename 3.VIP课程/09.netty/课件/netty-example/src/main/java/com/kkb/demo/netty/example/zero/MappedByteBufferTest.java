package com.kkb.demo.netty.example.zero;

import org.junit.Test;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class MappedByteBufferTest {
    private final static String CONTENT = "Zero copy implemented by MappedByteBuffer";
    private final static String FILE_NAME = "/Users/hadoop/Desktop/mmap.txt";
    private final static String CHARSET = "UTF-8";

    /**
     * 写文件数据：打开文件通道 fileChannel 并提供读权限、写权限和数据清空权限，
     * 通过 fileChannel 映射到一个可写的内存缓冲区 mappedByteBuffer，
     * 将目标数据写入 mappedByteBuffer，通过 force() 方法把缓冲区更改的内容强制写入本地文件。
     *
     */
    @Test
    public void writeToFileByMappedByteBuffer() {
        Path path = Paths.get(FILE_NAME);

        byte[] bytes = CONTENT.getBytes(Charset.forName(CHARSET));
        try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ,
                StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, bytes.length);
            if (mappedByteBuffer != null) {
                mappedByteBuffer.put(bytes);
                mappedByteBuffer.force();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读文件数据：打开文件通道 fileChannel 并提供只读权限，
     * 通过 fileChannel 映射到一个只可读的内存缓冲区 mappedByteBuffer，
     * 读取 mappedByteBuffer 中的字节数组即可得到文件数据
     */
    @Test
    public void readFromFileByMappedByteBuffer() {
        Path path = Paths.get(FILE_NAME);
        int length = CONTENT.getBytes(Charset.forName(CHARSET)).length;
        try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ)) {
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, length);
            if (mappedByteBuffer != null) {
                byte[] bytes = new byte[length];
                mappedByteBuffer.get(bytes);
                String content = new String(bytes, StandardCharsets.UTF_8);
                System.out.println(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
