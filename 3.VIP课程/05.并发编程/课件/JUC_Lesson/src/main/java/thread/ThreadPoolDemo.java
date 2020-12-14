package thread;


import java.util.concurrent.*;

/**
 * 线程池代码演示
 */


public class ThreadPoolDemo {
    public static void main(String[] args) {
        //System.out.println("=======Fixed Thread Pool========");
        //一个池子有5个工作线程，类似银行有5个受理窗口
        //threadPoolTask( Executors.newFixedThreadPool(5) );

        //System.out.println("======Single Thread Pool=========");
        // //一个池子有1个工作线程，类似银行有1个受理窗口
        //threadPoolTask( Executors.newSingleThreadExecutor() );

        //System.out.println("=====Cached Thread Pool=======");
        // //不定量线程，一个池子有N个工作线程，类似银行有N个受理窗口
        //threadPoolTask( Executors.newCachedThreadPool() );


        System.out.println("=====Custom Thread Pool=======");
        threadPoolTask( new ThreadPoolExecutor(
                2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy()
        ));
    }

    private static void threadPoolTask(ExecutorService threadPool) {
        //模拟有10个顾客来办理业务
        try {
            for (int i = 1; i <= 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName()+"\t办理业务");
                    try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) {e.printStackTrace(); }
                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}



