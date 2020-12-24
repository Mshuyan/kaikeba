package com.abc.group;

public class FetchHashCode {
    public static void main(String[] args) {
        int num = Math.abs("shanghai".hashCode()) % 50;
        System.out.println("当前consumer group的offset保存在如下分区：" + num);
    }
}




