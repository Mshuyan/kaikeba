package thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * `CountDownLatch`是减，而`CyclicBarrier`是加，理解了`CountDownLatch`，`CyclicBarrier`就很容易。
 *
 * 比如召集7颗龙珠才能召唤神龙
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        // 7个线程准备好后，执行参数2的回调
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()->{
            System.out.println("====== Ready~ GO !!!");
        });

        for (int i = 1; i <= 7; i++) {
            // 7个线程陆续启动
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + "\t就位");
                try {
                    // 7个线程进入等待状态
                    cyclicBarrier.await();
                    // 7个线程准备好后，参数2的回调方法执行结束后，各个线程同时被唤醒，继续执行下面的操作
                    System.out.println(Thread.currentThread().getName() + " run");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, String.valueOf("选手" + i)).start();
        }
    }
}
