package thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class VolatileDemo1 {
    public static Integer numner = 0;

    @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
    public static void main(String[] args) {
        new Thread(()->{
            try {
                sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            numner += 1;
        },"test").start();

        while(numner == 0){
            synchronized (VolatileDemo1.class){
            }
        }

        System.out.println(Thread.currentThread().getName() + "\t int类型的number最终值：" + numner);
    }

}
