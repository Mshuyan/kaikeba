package volatiles;

public class NonVolatileDemo {
    public static boolean stop = false;//任务是否停止,普通变量
    public static void main(String[] args) throws Exception {
        Thread thread1 = new Thread(() -> {
            while (!stop) { //stop=false，不满足停止条件，继续执行
                // do someting          
            }
            System.out.println("stop=true，满足停止条件。" +"停止时间：" + System.currentTimeMillis());
        });
        thread1.start();
        Thread.sleep(100);//保证主线程修改stop=true，在子线程启动后执行。        
        stop = true; //true        
        System.out.println("主线程设置停止标识 stop=true。" +"设置时间：" + System.currentTimeMillis());
    }
}
