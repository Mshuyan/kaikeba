package thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABA问题
 */
public class ABADemo {


    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    //带有时间戳的原子引用 (共享内存值100， 版本号为1)
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {
        System.out.println("========ABA问题的产生=========");

        new Thread(()->{
            //CAS
            atomicReference.compareAndSet(100, 111);
            //CAS
            atomicReference.compareAndSet(111, 100);

        }, "t1").start();

        new Thread(()->{
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) {e.printStackTrace(); }

            //CAS
            //System.out.println(atomicReference.compareAndSet(100, 2020) +"\t"+atomicReference.get());

        }, "t2").start();


        System.out.println("=========ABA问题的解决===========");
        new Thread(()->{
            //获取第一次的版本号
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t第一次版本号"+ stamp);

            //CAS
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) {e.printStackTrace(); }
            atomicStampedReference.compareAndSet(
                    100,
                    111,
                    atomicStampedReference.getStamp(),
                    atomicStampedReference.getStamp()+1

            );
            System.out.println(Thread.currentThread().getName()+"\t第二次版本号："+ atomicStampedReference.getStamp());

            //CAS
            atomicStampedReference.compareAndSet(
                    111,
                    100,
                    atomicStampedReference.getStamp(),
                    atomicStampedReference.getStamp()+1
            );
            System.out.println(Thread.currentThread().getName()+"\t第三次版本号："+ atomicStampedReference.getStamp());


        }, "t3").start();


        new Thread(()->{
            //获取第一次的版本号
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t第一次版本号"+ stamp);

            //CAS
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) {e.printStackTrace(); }
            boolean result = atomicStampedReference.compareAndSet(
                    100,
                    2020,
                    stamp,
                    stamp + 1
            );
            System.out.println(
                    Thread.currentThread().getName()
                    +"\t修改是否成功："+result
                    +"\t当前最新的版本号："+atomicStampedReference.getStamp()
                    +"\t当前最新的值："+atomicStampedReference.getReference()
            );


        }, "t4").start();

    }
}
