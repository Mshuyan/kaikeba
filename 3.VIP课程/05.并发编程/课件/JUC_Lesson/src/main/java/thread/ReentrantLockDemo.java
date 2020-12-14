package thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ReentrantLockDemo {
    public static void main(String[] args) {
        PhonePlus phonePlus = new PhonePlus();

        syncTest(phonePlus);
    }

    private static void syncTest(PhonePlus phonePlus) {
        new Thread(()->{
            phonePlus.sendSMS();
        }, "t1").start();

        new Thread(()->{
            phonePlus.sendSMS();
        }, "t2").start();

        Thread t3 = new Thread(phonePlus);
        Thread t4 = new Thread(phonePlus);
        t3.start();
        t4.start();
    }
}

class PhonePlus implements Runnable {
    //Synchronized TEST

    public synchronized void sendSMS() {
        System.out.println(Thread.currentThread().getId() + "\t" + "sendSMS()");
        sendEmail();
    }

    public synchronized void sendEmail() {
        System.out.println(Thread.currentThread().getId() + "\t" + "sendEmail()");
    }

    //Reentrant TEST
    Lock lock = new ReentrantLock();

    public void method1() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getId() + "\t" + "method1()");
            method2();
        } finally {
            lock.unlock();
        }
    }

    public void method2() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getId() + "\t" + "method2()");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        method1();
    }
}
