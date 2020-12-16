# 介绍

## MQ作用

+ 异步解耦
+ 流量消峰
+ 顺序收发
+ 分布式事务一致性

## MQ缺点

+ 不能完全替代RPC
+ 系统可用性降低
+ 系统复杂度提高
+ 一致性问题

# 安装

## 源码安装

+ 从[github](https://github.com/apache/rocketmq/releases)获取源码

+ `maven`编译

  ```mvn
  mvn -Prelease-all -Dcheckstyle.skip=true -DskipTests clean install -U
  ```

+ 在`distribution`模块的`target/rocketmq-4.7.1`目录下获取编译结果

+ 启动

  `rocketmq`依赖`jdk1.8`，系统中必须安装`jdk1.8`

  + 启动`name server`

    + 注册中心

    + 启动命令
  
      ```sh
    $ nohup sh bin/mqnamesrv >/dev/null 2>&1 &
      ```

    + 查看日志
  
      ```sh
    $ tail -f ~/logs/rocketmqlogs/namesrv.log
      ```

  + 启动`broker`

    + 消息队列节点
  
    + 启动命令
    
    ```sh
      # -n 指定注册中心地址
  $ nohup sh bin/mqbroker -c conf/broker.conf -n localhost:9876 >/dev/null 2>&1 &
    ```
  
    + 查看日志
  
      ```sh
    $ tail -f ~/logs/rocketmqlogs/broker.log
      ```
    
  
+ 关闭

  + 关闭`name server`

    ```sh
    $ bin/mqshutdown namesrv
    ```

  + 关闭`broker`

    ```sh
    $ bin/mqshutdown broker
    ```

* maven依赖

  ```xml
  <dependency>
      <groupId>org.apache.rocketmq</groupId>
      <artifactId>rocketmq-client</artifactId>
      <version>4.7.1</version>
  </dependency>
  ```

## 控制台安装

+ [下载源码](https://github.com/apache/rocketmq-externals)，不要下载`release`版本，那是`1.0.0`版本的，源码是`2.0.0`版本

+ 解压进入`rocketmq-console`目录，执行如下命令编译

  ```
  mvn clean package -Dmaven.test.skip=true
  ```

+ 创建目录，拷贝文件

  ```sh
  $ mkdir -p /usr/local/rocketmq-console/data
  $ cp /mnt/c/Users/43422/Desktop/rocketmq-externals-master/rocketmq-console/target/rocketmq-console-ng-2.0.0.jar /usr/local/rocketmq-console
  $ cp /mnt/c/Users/43422/Desktop/rocketmq-externals-master/rocketmq-console/src/main/resources/*.properties /usr/local/rocketmq-console
  ```

+ 修改`application.properties`

  ```properties
  #这个填写自己的nameserver的地址
  rocketmq.config.namesrvAddr=127.0.0.1:9876
  #rocketmq-console的数据目录，默认为 /tmp/rocketmq-console/data
  rocketmq.config.dataPath=/usr/local/rocketmq-console/data
  #开启认证登录功能，默认为false
  rocketmq.config.loginRequired=true
  ```

+ 修改`user.properties`

  ```properties
  # 该文件支持热修改，即添加和修改用户时，不需要重新启动console
  # 格式， 每行定义一个用户， username=password[,N]  #N是可选项，可以为0 (普通用户)； 1 （管理员）  
  
  #定义管理员 
  admin=admin,1
  
  #定义普通用户
  user1=user1
  user2=user2
  ```

+ 启动

  ```sh
  nohup java -jar rocketmq-console-ng-2.0.0.jar &
  ```

+ 默认端口`8080`，查看页面

# 服务端

## 文件结构

+ `benchmark`

  性能测试工具

  + `consumer.sh`  ：测试消费者能力
  + `producer.sh`  ：测试普通MQ生产者能力
  + `runclass.sh`  
  + `tproducer.sh`：测试事务MQ生产者能力

+ `bin`

  工具脚本

  ```sh
  README.md         mqadmin.cmd         mqbroker.numanode3  play.cmd       setcache.sh
  cachedog.sh       mqbroker            mqnamesrv           play.sh        startfsrv.sh
  cleancache.sh     mqbroker.cmd        mqnamesrv.cmd       runbroker.cmd  tools.cmd
  cleancache.v1.sh  mqbroker.numanode0  mqshutdown          runbroker.sh   tools.sh
  dledger           mqbroker.numanode1  mqshutdown.cmd      runserver.cmd
  mqadmin           mqbroker.numanode2  os.sh               runserver.sh
  ```

  + `mqbroker`：mq节点启动脚本
  + `mqnamesrv`：注册中心启动脚本
  + `mqshutdown`：停止脚本

+ `conf`

  配置文件

  ```sh
  2m-2s-async  2m-noslave   dledger             logback_namesrv.xml  plain_acl.yml
  2m-2s-sync   broker.conf  logback_broker.xml  logback_tools.xml    tools.yml
  ```

  + `broker.conf`：`broker`节点配置文件

+ `lib`

  依赖库

## 修改JVM参数

+ `name server`

  修改`bin/runserver.sh`文件

+ `broker`

  修改`bin/runbroker.sh`文件

## broker服务配置

### 配置方法

broker服务端配置有2种方式：

+ 修改`conf/broker.conf`文件
+ 直接在启动命令上添加参数

### 参数

+ `autoCreateTopicEnable`
  + 是否开启自动创建topic
  + 默认值`true`

# 基本概念

## 生产者组

+ `producerGroup`



## 消费者组

+ `consumerGroup`



## 主题

+ `topic`



## 标签

+ `tag`



## 键

+ `key`

  

## 消费模式

+ 分类
  + 集群模式（默认）
    + 一条消息只被一个消费者消费
    + 所有消费者使用公用的偏移量获取队列中的消息
  + 广播模式
    + 一条消息可以被同一个消费者组中的多个消费者消费
    + 每个消费者使用自己的偏移量获取队列中的消息
    + 参见[广播消息](#广播消息) 

## 重置消费位点



# 消息样例

## 普通消息

### 生产

生产者发送消息共有3种方式：

+ SYNC

  + 同步发送，同步处理`broker`响应结果

  + code

    重点在于`15`行`producer.send(msg)`:

    ```java
    // 指定生产者分组，创建生产者
    DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
    // 指定 name server 地址
    producer.setNamesrvAddr("localhost:9876");
    // 开始生产
    producer.start();
    for (int i = 0; i < 10; i++) {
        //创建1条消息，指定 topic tag 消息体
        Message msg = new Message("TopicTest" /* Topic */,
                                  "TagA" /* Tag */,
                                  ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                                 );
        //指定 key
        msg.setKeys("key-"+i);
        SendResult sendResult = producer.send(msg);
        System.out.printf("%s%n", sendResult);
    }
    //关闭生产者
    producer.shutdown();
    ```

+ ASYNC

  + 单独线程异步发送，异步处理`broker`响应结果

  + code

    重点在于`20`行`producer.send(msg, new SendCallback() {})`：

    ```java
    // 指定生产者分组，创建生产者
    DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
    // 指定 name server 地址
    producer.setNamesrvAddr("localhost:9876");
    // 开始生产
    producer.start();
    // 设置异步发送失败重试次数
    producer.setRetryTimesWhenSendAsyncFailed(0);
    
    int messageCount = 10;
    final CountDownLatch countDownLatch = new CountDownLatch(messageCount);
    for (int i = 0; i < messageCount; i++) {
        try {
            final int index = i;
            // 创建1条消息，指定 topic tag keys 消息体
            Message msg = new Message("TopicTest",
                                      "TagA",
                                      "OrderID188",
                                      ("Hello world "+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    countDownLatch.countDown();
                    System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
                }
    
                @Override
                public void onException(Throwable e) {
                    countDownLatch.countDown();
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    countDownLatch.await(5, TimeUnit.SECONDS);
    producer.shutdown();
    ```

+ ONEWAY

  + 单独线程异步发送，不管`broker`的响应结果

  + code

    重点在于`14`行`producer.sendOneway(msg);`

    ```java
    // 指定生产者分组，创建生产者
    DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
    // 指定 name server 地址
    producer.setNamesrvAddr("localhost:9876");
    // 开始生产
    producer.start();
    for (int i = 0; i < 10; i++) {
        // 创建1条消息，指定 topic tag 消息体
        Message msg = new Message("TopicTest" /* Topic */,
                                  "TagA" /* Tag */,
                                  ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                                 );
        producer.sendOneway(msg);
    }
    System.out.println("send finish");
    // 等待发送完成
    Thread.sleep(5000);
    producer.shutdown();
    ```

+ 3种类型对比

  | 发送方式 | 发送TPS | 结果反馈 | 可靠性   |
  | -------- | ------- | -------- | -------- |
  | 同步发送 | 快      | 有       | 不丢失   |
  | 异步发送 | 快      | 有       | 不丢失   |
  | 单项发送 | 最快    | 无       | 可能丢失 |

### 消费

+ code

  ```java
  // 指定消费者分组，创建消费者
  DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
  // 指定 name server 地址
  consumer.setNamesrvAddr("localhost:9876");
  // 订阅topic
  consumer.subscribe("TopicTest", // topic
                     "*"); // 匹配哪些tag
  // 注册监听器
  consumer.registerMessageListener(new MessageListenerConcurrently() {
      @Override
      public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,ConsumeConcurrentlyContext context) {
          for (MessageExt msg:msgs){
              System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), new String(msg.getBody()));
          }
  		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
      }
  });
  
  // 启动消费者
  consumer.start();
  ```

## 顺序消息

+ 生产者

  + 通过`MessageQueueSelector`，将需要保证顺序的消息，按照代码规则，分配到同一个队列中

  + demo

    ```java
    SendResult sendResult = producer.send(msg, // 消息
                                          // 队列选择器
                                          new MessageQueueSelector() {
                                              @Override
                                              // 队列集合  消息  send方法传入的可变参数列表
                                              public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                                                  // 转换回订单id
                                                  Integer id = (Integer) arg;
                                                  // 哈希取模
                                                  int index = id % mqs.size();
                                                  // 返回要使用的队列
                                                  return mqs.get(index);
                                              }
                                          // 可变参数列表传入订单id
                                          }, orderId);
    ```

+ 消费者

  + 使用`MessageListenerOrderly`消息监听器，通过并发锁保证一个队列中的全部消息，必须由同一个消费者进行消费

  + demo

    ```java
    consumer.registerMessageListener(new MessageListenerOrderly() {
                AtomicLong consumeTimes = new AtomicLong(0);
    
                @Override
                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs,
                                                           ConsumeOrderlyContext context) {
                    context.setAutoCommit(false);
                    for (MessageExt me : msgs) {
                        System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + new String(me.getBody()) + "%n");
                    }
                    this.consumeTimes.incrementAndGet();
                    if ((this.consumeTimes.get() % 2) == 0) {
                        return ConsumeOrderlyStatus.SUCCESS;
                    } else if ((this.consumeTimes.get() % 3) == 0) {
                        return ConsumeOrderlyStatus.ROLLBACK;
                    } else if ((this.consumeTimes.get() % 4) == 0) {
                        return ConsumeOrderlyStatus.COMMIT;
                    } else if ((this.consumeTimes.get() % 5) == 0) {
                        context.setSuspendCurrentQueueTimeMillis(3000);
                        return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                    }
                    return ConsumeOrderlyStatus.SUCCESS;
                }
            });
    ```

## 广播消息

+ 一个队列中的一条消息可以被同一个消费者组中的消费者同时消费

+ 在消费者中指定广播模式

  ```java
  DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group2");
  consumer.setNamesrvAddr("localhost:9876");
  consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
  //设置广播模式（默认集群模式）
  consumer.setMessageModel(MessageModel.BROADCASTING);
  consumer.subscribe("TopicTest", "TagA || TagC || TagD");
  consumer.registerMessageListener(new MessageListenerConcurrently() {
      @Override
      public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
          System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + msgs + "%n");
          return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
      }
  });
  consumer.start();
  ```

+ 注意事项

  + 广播消费模式下不支持顺序消息。
  + 广播消费模式下不支持重置消费位点。
  + 每条消息都需要被相同订阅逻辑的多台机器处理。
  + 广播模式下服务端不维护消费进度，消费进度在客户端维护(offset)
  + 广播模式下，消息队列 RocketMQ 版保证每条消息至少被每台客户端消费一次，但是并不会重投消费失败的消息，因此业务方需要关注消费失败的情况。
  + 广播模式下，客户端每一次重启都会从最新消息消费。客户端在被停止期间发送至服务端的消息将会被自动跳过，请谨慎选择。
  + 广播模式下，每条消息都会被大量的客户端重复处理，因此推荐尽可能使用集群模式

## 延时消息

+ `rocketmq`仅支持固定延时等级的延时

  ```
  1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
  ```

  延时等级可以通过配置文件修改都有哪些等级

+ 实现原理

  + 所有延时消息发送后都存储在同一个`topic`（`SCHEDULE_TOPIC_XXXX`）中，根据延时level的个数创建对应数量的队列
  + `ScheduleMessageService`线程定时轮询`SCHEDULE_TOPIC_XXXX`中的消息，如果消息到时了，则将消息发送到原来指定的`topic`中，就能立即被消费者消费

+ `SCHEDULE_TOPIC_XXXX`主题是内置的，不允许进行订阅

+ demo

  ```java
  Message message = new Message("TopicTest", ("Hello scheduled message " + i).getBytes());
  //注意这里的3指的不是3s，而是等级
  message.setDelayTimeLevel(3);
  producer.send(message);
  ```

## 批量消息

+ 批量发送消息时只能发送到相同的`topic`

+ `Message`的`waitStoreMsgOK`属性需要相同

+ 不能批量发送延时消息，事务消息

+ 批量发送一次消息不能大于4M

+ demo

  ```java
  public class BatchProducer {
      public static void main(String[] args) throws Exception {
          DefaultMQProducer producer = new
                  DefaultMQProducer("please_rename_unique_group_name");
          producer.setNamesrvAddr("localhost:9876");
          producer.start();
          String topic = "TopicTest";
          List<Message> messages = new ArrayList<>();
          messages.add(new Message(topic, "TagA", "OrderID001", "Hello world 0".getBytes()));
          messages.add(new Message(topic, "TagA", "OrderID002", "Hello world 1".getBytes()));
          messages.add(new Message(topic, "TagA", "OrderID003", "Hello world 2".getBytes()));
          // 分割成多个小于4M的集合
          ListSplitter splitter = new ListSplitter(messages);
          while (splitter.hasNext()) {
              try {
                  List<Message> listItem = splitter.next();
                  SendResult sendResult = producer.send(listItem);
                  System.out.printf("%s%n", sendResult);
              } catch (Exception e) {
                  e.printStackTrace();
              }
          }
          producer.shutdown();
      }
  }
  
  class ListSplitter implements Iterator<List<Message>> {
      private final List<Message> messages;
      private int currIndex;
  
      public ListSplitter(List<Message> messages) {
          this.messages = messages;
      }
  
      @Override
      public boolean hasNext() {
          return currIndex < messages.size();
      }
  
      @Override
      public List<Message> next() {
          int nextIndex = currIndex;
          int totalSize = 0;
          for (; nextIndex < messages.size(); nextIndex++) {
              Message message = messages.get(nextIndex);
              int tmpSize = message.getTopic().length() + message.getBody().length;
              Map<String, String> properties = message.getProperties();
              for (Map.Entry<String, String> entry : properties.entrySet()) {
                  tmpSize += entry.getKey().length() + entry.getValue().length();
              }
              //for log overhead
              tmpSize = tmpSize + 20;
              int sizeLimit = 1024 * 1024 * 4;
              if (tmpSize > sizeLimit) {
                  //it is unexpected that single message exceeds the sizeLimit
                  //here just let it go, otherwise it will block the splitting process
                  if (nextIndex - currIndex == 0) {
                      //if the next sublist has no element, add this one and then break, otherwise just break
                      nextIndex++;
                  }
                  break;
              }
              if (tmpSize + totalSize > sizeLimit) {
                  break;
              } else {
                  totalSize += tmpSize;
              }
          }
          List<Message> subList = messages.subList(currIndex, nextIndex);
          currIndex = nextIndex;
          return subList;
      }
  }
  ```

## 过滤消息

消息过滤有2种方式：

+ `tag`过滤

  + 生产者生产消息时为消息指定`tag`
  + 消费者订阅消息时，通过表达式筛选`tag`过滤出自己想消费的消息
  + demo参见[普通消息](#普通消息)

+ `sql`表达式过滤

  + 生产消息时通过`putUserProperty`方法为`Message`设置属性

  + 消费者订阅消息时，通过sql条件过滤

  + demo

    + 生产

      ```java
      Message msg = new Message("TopicTest" /* Topic */,
                                tag /* Tag */,
                                ("RocketMQ消息测试，消息的TAG="+tag+  ", 属性 age = " + i + ", == " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
      msg.putUserProperty("age", i+"");
      SendResult sendResult = producer.send(msg);
      ```

    + 订阅

      ```java
      DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name");
      consumer.setNamesrvAddr("localhost:9876");
      // 通过sql过滤
      consumer.subscribe("TopicTest", MessageSelector.bySql("age between 0 and 6"));
      ```

## 事务消息

+ 半事务消息

  消息发送到服务端，但是处于`暂不投递`状态，需要进行二次确认才能被投递

  处于`暂不投递`状态的消息称为`半事务消息`

+ 消息回查

  服务端发现有长时间未确认的`半事务消息`，时，会主动向消息生产者询问消息状态

+ 注意事项

  + 不支持延时消息，批量消息
  + 默认最大检查次数为15次，可以通过`broker`配置文件中`transactionCheckMax`参数修改，超过次数被丢失
  + 事务消息超时时间
    + 全局超时时间：可以通过`broker`配置文件中`transactionTimeout `参数设置
    + 发送消息时也可以通过设置`CHECK_IMMUNITY_TIME_IN_SECONDS`属性指定，优先于全局配置
  + 消息可能丢失，如需保证消息绝对不丢失，需要使用`同步的双重写入机制`
  + 事务消息的生产者 ID 不能与其他类型消息的生产者 ID 共享。与其他类型的消息不同，事务消息允许反向查询、MQ服务器能通过它们的生产者 ID 查询到消费者

# 源码解读

## 消息监听器

+ 对应接口为`MessageListener`

+ 有两个实现类

  + `MessageListenerOrderly`

    消息顺序消费的监听器

  + `MessageListenerConcurrently`

    消息并发消费的监听器

## 消息实体类

+ 发送消息使用`Message`实体类
+ 消费消息使用`MessageExt`实体类，他是`Message`的子类

