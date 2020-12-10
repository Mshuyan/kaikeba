package thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁/递归锁
 */

class PhonePlus implements Runnable{

    //Synchronized Test
    public synchronized void sendEmail(){
        System.out.println(Thread.currentThread().getName()+"\t"+"sendEmail");
        sendSMS();
    }

    public synchronized void sendSMS(){
        System.out.println(Thread.currentThread().getName()+"\t"+"sendSMS");
    }

    //ReenTrantLock Test
    Lock lock = new ReentrantLock();
    public void method1(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t"+"method1");
            method2();
        } finally {
            lock.unlock();
        }
    }

    public void method2() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t"+"method2");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        method1();
    }
}

public class ReentrantLockDemo {
    public static void main(String[] args) {
        PhonePlus phonePlus = new PhonePlus();

        new Thread(()->{
            phonePlus.sendEmail();
        }, "t1").start();

        new Thread(()->{
            phonePlus.sendEmail();
        }, "t2").start();

        Thread t3 = new Thread(phonePlus);
        Thread t4 = new Thread(phonePlus);
        t3.start();
        t4.start();
    }
}
