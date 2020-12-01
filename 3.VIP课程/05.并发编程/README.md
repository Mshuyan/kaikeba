# 概念

## JUC

+ `JUC`是`java.util.concurrent`包得缩写

+ `java.util.concurrent`是JDK提供得并发工具包，主要包括如下3个包：

  + `java.util.concurrent`

    并发编程工具类

  + `java.util.concurrent.atomic`

    并发编程原子类

  + `java.util.concurrent.locks`

    并发编程锁

## JMM

+ `JMM`是java内存模型`java memory model`得缩写

+ 多线程访问同一个份数据时，这份数据是存储在`主内存`（堆）中得，每个线程有一个`工作内存`（栈），每个线程访问这个数据时，会将数据从主内存拷贝到工作内存，修改后立即同步给主内存

  ![image-20201201144435021](assets/image-20201201144435021.png) 

+ `JMM`中比较重要得3个概念：

  + 原子性

    一个操作不可分割

  + 可见性

    每个线程对主内存得修改立即通知其他线程

  + 有序性

    指令不会被重排

# volatile

> `volatile`具备2个特性：
>
> + 可见性
> + 有序性
>
> 不具备原子性

## 可见性

### 测试

+ 根据`JMM`，两个线程都将主内存中变量读取到工作内存后，一个线程修改变量的值后立即写回主内存，但是另一个线程并不知道这个变量已经被修改

+ demo

  ```java
  public class VolatileDemo1 {
      public static int number = 0;
  
      public static void main(String[] args) {
          new Thread(()->{
              try {
                  // 让主线程先读取主内存中变量值
                  sleep(20);
              } catch (InterruptedException ignore) {}
              // 修改并写回主内存
              number += 1;
          },"test").start();
  
          // 因为主线程不知道主内存中变量已被修改，会死在这
          while(number == 0){
          }
          System.out.println(Thread.currentThread().getName() + "\t int类型的number最终值：" + number);
      }
  }
  ```

+ `number`使用`volatile`修饰即可解决

### 原理

+ `volatile`变量被修改并写回主内存后，会立即通知其他线程重新加载该变量的值

### synchronized

+ 上述代码在`while`循环中加入`System.out.println();`后，即使不使用`volatile`关键字，也不存在可见性问题了
+ 原因是`System.out.println();`中使用了`synchronized`
+ `synchronized`关键字有2个作用：
  + 进入代码块之前，先清空工作内存中共享变量，重新从主内存加载
  + 代码块加上同步锁

## 有序性



## 原子性



## 应用



