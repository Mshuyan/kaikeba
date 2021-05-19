package com.kkb.demo.netty.example.nio;

import java.nio.IntBuffer;

/**
 * 这里我们首先写入两个 int 值, 此时 capacity = 10, position = 2, limit = 10.
 * 然后我们调用 flip 转换为读模式, 此时 capacity = 10, position = 0, limit = 2;
 */
public class BasicBuffer {

    public static void main(String[] args) {

        IntBuffer intBuffer = IntBuffer.allocate(10);
        intBuffer.put(10);
        intBuffer.put(101);
        System.err.println("Write mode: ");
        System.err.println("\tCapacity: " + intBuffer.capacity());
        System.err.println("\tPosition: " + intBuffer.position());
        System.err.println("\tLimit: " + intBuffer.limit());

        intBuffer.flip();
        System.err.println("Read mode: ");
        System.err.println("\tCapacity: " + intBuffer.capacity());
        System.err.println("\tPosition: " + intBuffer.position());
        System.err.println("\tLimit: " + intBuffer.limit());
    }
}
