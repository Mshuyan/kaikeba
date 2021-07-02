package com.supergo.thread;

/**
 * Created by on 2019/12/11.
 */
public class ThreadOne implements Runnable {
    int num = 10;
    @Override
    public synchronized void run() {

        for(int i=0;i<10;i++){
            num--;
            System.out.println("打印数字:"+Thread.currentThread()+"=="+num);
            }
        try {
            Thread.sleep(3000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
