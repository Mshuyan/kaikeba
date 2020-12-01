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



## 有序性



## 原子性



## 应用



