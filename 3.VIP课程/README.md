+ 设计模式

+ JVM
  + jvm参数
  + jvm工具
  + 字节码指令
  
+ redis
  
  + redission
  
+ mysql
  + undolog
    + insert undo log可以直接删除，压根没有老版本如何删除
  + 雪花算法
  + mycat
  
+ JUC
  
  + #### CompletableFuture
  
+ rocketmq

  + 顺序消息`MessageListenerOrderly`

  + 广播消息注意事项

    + 消息发送到1个队列，还是多个队列

  + 延时消息源码

    课件中`第一步：修改消息Topic名称和队列信息`下面内容

  + 同步双重写入机制
  
  + 事务消息的生产者 ID 不能与其他类型消息的生产者 ID 共享。与其他类型的消息不同，事务消息允许反向查询、MQ服务器能通过它们的生产者 ID 查询到消费者
  
  + rocketmq-spring-boot-starter
  
    + 事务消息和想要的不一样





+ 高并发调优方法
  + JVM调优，减少`FULL GC`次数和时间，提高吞吐量
  + 线程池调优
  + 数据库索引优化，增加缓存，提高响应速度
  + 