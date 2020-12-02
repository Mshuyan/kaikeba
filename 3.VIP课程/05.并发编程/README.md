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

## JNI

+ 全称`java native interface`
+ java和C语言实现的本地方法的交互规范

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

  `volatile`变量被修改并写回主内存后，会立即通知其他线程重新加载该变量的值

### synchronized

+ 上述代码在`while`循环中加入`System.out.println();`后，即使不使用`volatile`关键字，也不存在可见性问题了
+ 原因是`System.out.println();`中使用了`synchronized`
+ `synchronized`关键字有2个作用：
  + 进入代码块之前，先清空工作内存中共享变量，重新从主内存加载
  + 代码块加上同步锁

## 有序性

+ cpu执行指令时，会根据cpu使用情况，对cpu指令进行重排，指令重排的原则保证了单线程情况下，不会影响执行结果；但是多线程情况下，可能会影响代码执行结果

### 测试

+ demo

  ```java
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
              System.out.println("*****最终值a: "+a);
          }
      }
  
      public static void main(String[] args) {
          ResortSeqDemo resortSeq = new ResortSeqDemo();
  
          new Thread(()->{resortSeq.method01();},"ThreadA").start();
          new Thread(()->{resortSeq.method02();},"ThreadB").start();
      }
  }
  ```

+ 通过给`a`和`flag`加上`volatile`可以禁止指令重排

### 单例模式应用

+ 在单例模式中`instance = new SingletonDemo();`这行代码会被编译成多行字节码指令执行，并可能存在指令重排

  + 正常顺序
    + 申请内存空间
    + 执行初始化流程
    + 引用赋值给`instance`变量
  + 重排后
    + 申请内存空间
    + 引用赋值给`instance`变量
    + 执行初始化流程

+ 重排后的指令在多线程环境下，如果一个线程将引用赋值给`instance`变量后，将cpu让给另外一个线程执行，这个线程发现`instance`变量不为空，调用对象中的方法将抛出异常，因为对象还没来得及初始化

+ 此处需要使用`volatile`修饰`instance`变量，来禁止`instance`变量赋值上面的代码排序到他下面执行

+ demo

  ```java
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
  }
  ```

## 原子性

+ 有些代码虽然是一行，但是编译成字节码后分为多行执行（如`i++`），因为这些操作分为多行执行，并不具备原子性，就会导致代码执行出错

+ demo

  ```java
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
  ```

  上面的`NUMBER++`编译成字节码后如下：

  ```java
   9 getstatic #17 <thread/VolatileDemo2.numner>
  12 iconst_1
  13 iadd
  14 putstatic #17 <thread/VolatileDemo2.numner>
  ```

  当两个线程同时获取变量值之后，都基于这个值进行`+1`操作，然后写回主内存，我们期望的结果是加了2，但此时只加了1

+ 解决

  + 这个问题无法通过`volatile`解决，他不具备原子性
  + 我们可以将`NUMBER`定义为`AtomicInteger`解决，这是1个原子类

## 内存屏障

### 介绍

`volatile`变量在读写时，分别会在这行代码上下增加两道内存屏障，这些内存屏障实际是`CPU指令`

+ `volatile`写

  ![image-20201202002315372](assets/image-20201202002315372.png) 

  + `StoreStore`
    + 加在`volatile`变量写操作指令**前**
    + 以这道屏障为界，上面的**普通写**不可以跑到屏障的下面，下面的**`volatile`写**不可以跑到屏障的上面
  + `StoreLoad`
    + 加在`volatile`变量写操作指令**后**
    + 以这道屏障为界，上面的**`volatile`写**不可以跑到屏障的下面，下面的**`volatile`读写**不可以跑到屏障的上面

+ `volatile`读

  ![image-20201202002418350](assets/image-20201202002418350.png) 

  + `LoadLoad`
    + 加在`volatile`变量读操作指令**后**
    + 以这道屏障为界，上面的**`volatile`读**不可以跑到屏障的下面，下面的**普通读**不可以跑到屏障的上面
  + `LoadStore`
    + 加在`volatile`变量读操作指令**后**
    + 以这道屏障为界，上面的**`volatile`读**不可以跑到屏障的下面，下面的**所有写**不可以跑到屏障的上面

### 作用

`volatile`是通过内存屏障实现的，内存屏障的作用就是`volatile`的作用：

+ 禁止指令重排

  禁止内存屏障前后的指令重新排序

+ 保证内存可见性

  强制将CPU缓存刷出到内存

# CAS

## 介绍

+ 全称`Compare And Swap`，比较交换
+ 这是一条CPU并发原语，功能如下：
  + 判断主内存中的值是否与期望值相同，相同则修改为另一个值，不同则不断重试
  + 这个过程是原子操作

## Unsafe类

+ 该类中的方法都是实现`CAS`操作的本地方法
+ java中所有原子类底层调用的都是这个类中的本地方法实现