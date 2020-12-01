package thread;

import static java.lang.Thread.sleep;

public class VolatileDemo2 {
    public static volatile int NUMBER = 0;

    public static void main(String[] args) {
        for(int i=0; i<20; i++) {
            new Thread(() -> {
                for(int j=0; j<1000; j++){
                    NUMBER++;
                }
            }, "test"+ i).start();
        }

        while(Thread.activeCount() > 2){ }
        System.out.println(Thread.currentThread().getName() + "\t int类型的number最终值：" + NUMBER);
    }
}
