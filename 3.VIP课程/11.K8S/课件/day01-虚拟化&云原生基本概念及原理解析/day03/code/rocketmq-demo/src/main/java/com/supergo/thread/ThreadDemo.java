package com.supergo.thread;

/**
 * Created by on 2019/12/11.
 */
public class ThreadDemo {

    public static void main(String[] args) {
        ThreadOne oneThread = new ThreadOne();

        //创建线程
        Thread t1 = new Thread(oneThread);
        Thread t2 = new Thread(oneThread);

        t1.start();
        t2.start();

    }
}
