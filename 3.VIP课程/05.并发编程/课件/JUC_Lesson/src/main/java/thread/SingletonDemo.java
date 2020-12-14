package thread;

/**
 * 单例设计模式的安全问题
 * 常见的DCL（Double Check Lock）双端检查模式虽然加了同步，但是在多线程下依然会有线程安全问题。
 */
public class SingletonDemo {
    private static volatile SingletonDemo instance = null;

    private SingletonDemo() {
        System.out.println(Thread.currentThread().getName() +"\t SingletonDemo构造方法执行了");
    }

    public static SingletonDemo getInstance(){
        if (instance == null) {
            synchronized (SingletonDemo.class){
                if (instance == null) {
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        //main线程操作
        // System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
        // System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
        // System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());

        //多线程操作
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                SingletonDemo.getInstance();
            },Thread.currentThread().getName()).start();
        }

    }
}
