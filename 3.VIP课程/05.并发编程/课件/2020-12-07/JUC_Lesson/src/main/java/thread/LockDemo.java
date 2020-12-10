package thread;

import java.util.concurrent.TimeUnit;

/**
 * 1. 标准访问，请问先打印邮件还是短信？ 邮件
 * 2. 邮件方法暂停4秒钟，请问先打印邮件还是短信？ 邮件
 *      对象锁
 *      一个对象里面如果有多个synchronized方法，某一个时刻内，只要一个线程去调用其中的一个synchronized方法了，
 *      其他的线程都只能等待，换句话说，某一个时刻内，只能有唯一一个线程去访问这些synchronized方法，
 *      锁的是当前对象this，被锁定后，其他的线程都不能进入到当前对象的其他的synchronized方法
 *
 * 3. 新增一个普通方法hello（），请问先打印邮件还是hello？
 * 4. 两部手机，请问先打印邮件还是短信？
 *
 * 5. 两个静态同步方法，同一部手机，请问先打印邮件还是短信？
 * 6. 两个静态同步方法，2部手机，请问先打印邮件还是短信？
 *
 * 7. 1个普通同步方法，1个静态同步方法，1部手机，请问先打印邮件还是短信？
 * 8. 1个普通同步方法，1个静态同步方法，2部手机，请问先打印邮件还有短信？
 */
class Phone{
    public synchronized void sendEmail(){
        try { TimeUnit.SECONDS.sleep(4); } catch (InterruptedException e) {e.printStackTrace(); }
        System.out.println("==========sendEmail");
    }

    public static synchronized void sendMessage(){
        System.out.println("======sendMessage");
    }

    public void hello(){
        System.out.println("say hello");
    }


}

public class LockDemo {
    public static void main(String[] args) {
        Phone phone = new Phone();
        Phone phone2 = new Phone();

        new Thread(()->{
            phone.sendEmail();
        }, "t1").start();

        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) {e.printStackTrace(); }
        
        new Thread(()->{
            phone2.sendMessage();
        }, "t2").start();

        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) {e.printStackTrace(); }
        /*
        new Thread(()->{
            phone.hello();
        }, "t3").start();
        */
    }
}
