package thread;

import java.util.concurrent.TimeUnit;

/**
 * volatile可以保证 有序性，也就是防止 指令重排序。
 *
 * 单线程环境里面确保程序最终执行结果和代码顺序执行的结果一致；
 * 处理器在进行重排序时必须要考虑指令之间的数据依赖性；
 * 多线程环境中线程交替执行，由于编译器优化重排的存在，两个线程中使用的变量能否保证一致性是无法确定的，结果无法预测。
 */
public class ResortSeqDemo {

    int a=0;
    boolean flag=false;
    /*
    多线程下flag=true可能先执行，还没走到a=1就被挂起。
    其它线程进入method02的判断，修改a的值=5，而不是6。
     */
    public void method01(){
        a=1;
        flag=true;
    }
    public void method02(){
        if (flag){
            a+=5;
            System.out.println("*****retValue: "+a);
        }
    }

    public static void main(String[] args) {
        ResortSeqDemo resortSeq = new ResortSeqDemo();

        new Thread(()->{resortSeq.method01();},"ThreadA").start();
        new Thread(()->{resortSeq.method02();},"ThreadB").start();
    }
}
