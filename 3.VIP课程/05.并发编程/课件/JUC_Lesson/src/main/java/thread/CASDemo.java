package thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS的全称为Compare-And-Swap，比较并交换，是一种很重要的同步思想。它是一条CPU并发原语。
 * 它的功能是判断主内存某个位置的值是否为跟期望值一样，相同就进行修改，否则一直重试，直到一致为止。这个过程是原子的。
 */
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger=new AtomicInteger(5);
        System.out.println(atomicInteger.compareAndSet(5, 2020)+"\t 当前数据值 : "+ atomicInteger.get());
        //修改失败
        System.out.println(atomicInteger.compareAndSet(5, 1024)+"\t 当前数据值 : "+ atomicInteger.get());
    }
}
