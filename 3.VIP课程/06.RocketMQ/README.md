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

+ 从[github](https://github.com/apache/rocketmq/releases)获取源码

+ `maven`编译

  ```mvn
  mvn -Prelease-all -Dcheckstyle.skip=true -DskipTests clean install -U
  ```

+ 在`distribution`模块的`target/rocketmq-4.7.1`目录下获取编译结果

+ 启动

  + 启动`name server`

    + 注册中心

    + 启动命令

      ```sh
      $ nohup sh bin/mqnamesrv &
      ```

    + 查看日志

      ```sh
      $ tail -f logs/rocketmqlogs/namesrv.log
      ```

  + 启动`broker`

    + 启动命令

      ```sh
      # -n 指定注册中心地址
      $ nohup sh bin/mqbroker -n localhost:9876 &
      ```

    + 查看日志

      ```sh
      
      ```

      

