package thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.Thread.sleep;

/**
 * 多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行。
 * 但是，如果有一个线程想去写共享资料，就不应该再有其他线程可以对该资源进行读或写
 * 小总结：
 * 		读-读 能共存
 * 		读-写 不能共存
 * 		写-写 不能共存
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();

        new Thread(()->{
            lock.lock();
            try {
                conditionA.await();
            }catch (Exception e){}
            finally {
                lock.unlock();
            }
        },"A").start();

        new Thread(()->{
            lock.lock();
            try {
                conditionB.await();
            }catch (Exception e){}
            finally {
                lock.unlock();
            }
        },"B").start();

        sleep(1000);
        // 先唤醒A
        conditionA.signal();
        // 再唤醒B
        conditionB.signal();



        MyCache cache = new MyCache();

        //写
        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;
            new Thread(()->{
                cache.put(tempInt + "", tempInt + "");
            }, String.valueOf(i)).start();
        }

        //读
        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;
            new Thread(()->{
                cache.get(tempInt+"");
            }, String.valueOf(i)).start();
        }
    }
}

class MyCache{
    //缓存更新快，需要用volatile修饰，保证可见性，不保证原子性，一个线程修改后，通知更新
    private Map<String, Object> map = new HashMap<>();
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void put(String key, Object value){
        rwLock.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t正在写入: "+ key);
            //模拟网络传输
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) {e.printStackTrace(); }

            map.put(key, value);
            System.out.println(Thread.currentThread().getName()+"\t写入完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public Object get(String key){
        Object result = null;

        rwLock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName() + "\t正在读取:" + key);
            //模拟网络传输
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) {e.printStackTrace(); }

            result = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t读取完成： "+result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwLock.readLock().unlock();
        }
        return result;
    }
}
