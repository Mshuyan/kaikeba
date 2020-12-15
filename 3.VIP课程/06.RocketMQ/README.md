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
    $ nohup sh bin/mqnamesrv &
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
  $ nohup sh bin/mqbroker -c conf/broker.conf -n localhost:9876 &
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

+ `producerGroup`
+ `consumerGroup`
+ `topic`
+ `tag`
+ `key`

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
                     "*"); // 子表达式
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

