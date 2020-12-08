package thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行。
 * 但是，如果有一个线程想去写共享资料来，就不应该再有其他线程可以对该资源进行读或写
 * 小总结：
 * 		读-读 能共存
 * 		读-写 不能共存
 * 		写-写 不能共存
 */

//共享资源 Word
class MyCache{
    //缓存更新快，需要volatile关键字修饰，保证可见性，不保证原子性，一个线程修改后，通知其他线程
    private volatile Map<String, Object> map = new ConcurrentHashMap<>();

    //读写锁
    ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();

    //存(写操作)
    public void put(String key, Object value){
        rwlock.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t 正在写入：" + key);

            //模拟下耗时
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) {e.printStackTrace(); }

            map.put(key, value);
            System.out.println(Thread.currentThread().getName() +"\t 写入完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwlock.writeLock().unlock();
        }
    }

    //取
    public Object get(String key){
        Object result = null;

        rwlock.readLock().lock();

        try{
            System.out.println(Thread.currentThread().getName()+"\t 正在读取：" + key);

            //模拟下耗时
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) {e.printStackTrace(); }

            result = map.get(key);
            System.out.println(Thread.currentThread().getName() +"\t 读取完成");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwlock.readLock().unlock();
        }
        return result;
    }
}


public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache cache = new MyCache();

        //写
        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;
            new Thread(()->{
                cache.put(tempInt+"", tempInt+"");
            }, String.valueOf(i)).start();
        }

        //读
        for (int i = 1; i <= 5; i++) {
            final int tempInt=i;
            new Thread(()->{
                cache.get(tempInt+"");
            }, String.valueOf(i)).start();
        }

    }
}
