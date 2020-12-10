package thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目：三个售票员   卖出   30张票
 *
 */
class Ticket{//资源类
    //票
    private int number = 30;

    Lock lock = new ReentrantLock();

    public void saleTicket(){

        try{
            //获取锁
            lock.lock();

            if (number > 0) {
                System.out.println(Thread.currentThread().getName()+"\t卖出第："+(number--)+"\t还剩下："+number);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放锁
            lock.unlock();
        }


    }
}

public class SaleTicketDemo {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(()->{ for (int i = 1; i <= 30 ; i++) ticket.saleTicket(); }, "A").start();
        new Thread(()->{ for (int i = 1; i <= 30 ; i++) ticket.saleTicket(); }, "B").start();
        new Thread(()->{ for (int i = 1; i <= 30 ; i++) ticket.saleTicket(); }, "C").start();
    }
}
