package thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile关键字是Java提供的一种轻量级同步机制。
 * <p>
 * 它能够保证 可见性 和 有序性
 * 但是不能保证 原子性
 * 禁止指令重排(编辑器优化的重排、指定并行的重排、内存系统的重排)
 */
class MyData {
    // int number = 0;
    volatile int number = 0;
    AtomicInteger atomicInteger=new AtomicInteger();

    public void addPlusPlus(){
        number++;
    }

    public void addAtomic(){
        atomicInteger.getAndIncrement();
    }

    public void setTo60() {
        this.number = 60;
    }
}

public class VolatileDemo {
    public static void main(String[] args) {
        //volatileVisibilityDemo();
        atomicDemo();
    }

    private static void atomicDemo() {
        System.out.println("原子性测试");
        MyData myData=new MyData();
        for (int i = 1; i <= 20; i++) {
            new Thread(()->{
                for (int j = 0; j <1000 ; j++) {
                    myData.addPlusPlus();
                    myData.addAtomic();
                }
            },String.valueOf(i)).start();
        }
        while (Thread.activeCount()>2){
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName()+"\t int类型最终number值: "+myData.number);
        System.out.println(Thread.currentThread().getName()+"\t AtomicInteger类型最终number值: "+myData.atomicInteger);
    }


    //volatile可以保证可见性，及时通知其它线程主物理内存的值已被修改
    private static void volatileVisibilityDemo() {
        System.out.println("可见性测试");
        MyData myData = new MyData();//资源类
        //启动一个线程操作共享数据
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 执行");
            try {
                TimeUnit.SECONDS.sleep(3);
                myData.setTo60();
                System.out.println(Thread.currentThread().getName() + "\t 更新number值: " + myData.number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "ThreadA").start();
        while (myData.number == 0) {
            //main线程持有共享数据的拷贝，一直为0
        }
        System.out.println(Thread.currentThread().getName() + "\t main获取number值: " + myData.number);
    }

}
