> + 课程使用`mysql5.7.3`
> + `mysql`配置文件中不区分`-`和`_`

# 安装

+ docker

  ```sh
  #!/bin/bash
  
  # /usr/local/docker-srv/mysql5.7/logs 需要修改权限为 777
  
  docker run -d --name mysql5.7 \
   --log-opt max-size=10m \
   -v /etc/localtime:/etc/localtime:ro \
   -v /etc/timezone:/etc/timezone:ro \
   -p 3306:3306 \
   --privileged=true \
   -v /usr/local/docker-srv/mysql5.7/conf:/etc/mysql \
   -v /usr/local/docker-srv/mysql5.7/logs:/logs \
   -v /usr/local/docker-srv/mysql5.7/data:/var/lib/mysql \
   -e MYSQL_ROOT_PASSWORD=943397 \
   mysql:5.7
  ```

  

# 文件结构

> + `mysql`数据通常放在`var/lib/mysql`目录下，里面的文件分为日志文件和数据文件

## 日志文件

> + 日志文件都是顺序IO的，追加的形式按顺序写
> + 日志相关配置可以通过` show variables like 'log_%';`查看

### 错误日志

+ 作用：记录运行过程中遇到的所有`错误、警告、通知`等信息,以及每次`启动和关闭`的详细信息。

+ 配置

  ```sh
  # 作用：错误日志存储位置
  # 默认值：stderr，输出到控制台
  # 常用配置：一般都需要指定1个输出位置
  log_error=/var/log/mysqld.log
  # 作用：将哪些级别的警告输出到错误日志，默认2；0表示不输出警告
  # 默认值：2
  # 常用配置：使用默认值
  log_warnings=2
  # 作用：将哪些信息输出到错误日志：1：错误；2：错误和警告；3：错误、警告和通知
  # 默认值：3
  # 常用配置：使用默认值
  log_error_verbosity=3
  ```

### 二进制日志

+ 作用：记录数据库实例的所有修改语句

+ 默认：关闭

+ 常用配置：需要使用`binlog`文件时再开启，一般需要将数据实时同步给其他服务时需要开启

+ 配置

  ```sh
  # 作用：指定服务id
  # 默认值：0
  # 说明：主从复制功能需要为每个mysql实例指定不同的server-id；为0时无法启用主从复制和binlog日志功能
  server-id=1 
  # 作用：启用并配置binlog文件的basename
  # 默认：没有该配置（关闭binlog）
  log-bin=mysql-bin
  ```

+ 说明：启用`binlog`后，会在`/var/lib/mysql`下生成如下文件：

  + `{basename.index}`：二进制索引文件，用于为所有二进制文件建立索引
  + `{basename}.000001`：二进制文件，真正的内容

### 通用查询日志

+ 作用：记录用户所有操作

+ 默认关闭

+ 常用配置：关闭；开启后产生大量不必要的磁盘IO，影响性能

+ 配置

  ```sh
  
  ```

  

## 数据文件

> 数据文件是随机IO的，需要在文件中需要的位置插入、修改数据

