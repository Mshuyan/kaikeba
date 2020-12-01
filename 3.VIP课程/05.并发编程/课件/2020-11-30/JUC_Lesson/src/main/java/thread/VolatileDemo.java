package thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile关键字是Java提供的一种轻量级同步机制。
 *
 * 它能够保证 可见性 和 有序性
 * 但是不能保证 原子性
 * 禁止指令重排(编辑器优化的重排、指定并行的重排、内存系统的重排)
 */
class MyData{
    //int number = 0;
    volatile int number = 0;

    AtomicInteger atomicInteger = new AtomicInteger();

    public void setTo60(){
        this.number = 60;
    }

    public void addPlusPlus(){
        number++;
    }

    public void addAtomic(){
        atomicInteger.getAndIncrement();
    }

}

public class VolatileDemo {
    public static void main(String[] args) {
        //volatileVisibilityDemo();
        
        atomicDemo();
    }

    /**
     * 原子性操作
     */
    private static void atomicDemo() {
        System.out.println("原子性测试");
        MyData myData = new MyData();

        //创建20个线程
        for (int i = 0; i <20; i++) {
            new Thread(()->{
                //让每个线程对变量number ++ ，1000遍
                for (int j = 0; j < 1000; j++) {
                    myData.addPlusPlus();
                    myData.addAtomic();
                }
            },String.valueOf(i)).start();
        }

        while(Thread.activeCount() > 2){ //main, gc
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName() + "\t int类型的number最终值：" + myData.number);
        System.out.println(Thread.currentThread().getName() + "\t atomicInteger类型的最终值：" + myData.atomicInteger);
    }

    /**
     * 演示可见性操作
     */
    private static void volatileVisibilityDemo() {
        System.out.println("演示可见性操作");

        MyData myData = new MyData();

        //启动一个线程，操作共享数据
        new Thread(()->{
            System.out.println(Thread.currentThread().getName() +"执行了");
            try {
                TimeUnit.SECONDS.sleep(3);
                myData.setTo60();
                System.out.println(Thread.currentThread().getName()+"\t 更新number的值:" +myData.number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"ThreadA").start();


        //main主线程
        while(myData.number == 0){
            //main线程持有共享数据的拷贝，一直为0
        }
        System.out.println(Thread.currentThread().getName()+"\t main线程获取number值： " + myData.number);
    }
}
