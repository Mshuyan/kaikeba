# lua整合

## 概述

+ `lua`是1个脚本语言
+ `redis`中使用`lua`脚本，则脚本中执行的命令都是一起执行的，可以认为是`原子操作`

## 使用

### lua脚本中调用Redis命令

+ 调用

  + `redis.call()`：出错时返回错误信息，停止执行
  + `redis.pcall()`：出错时记录错误信息，继续执行
  + 返回值为`redis`命令的返回值

+ 返回值

  `lua`脚本使用`return`返回脚本的返回值给客户端，不`return`则返回`nil`

+ 例

  ```sh
  127.0.0.1:6379> eval "return redis.call('set',KEYS[1],ARGV[1])" 1 n1 zha
  OK
  ```

### redis命令行

#### EVAL命令

```sh
# 语法
127.0.0.1:6379> eval SCRIPT KEY_NUMS KEY ... VALUE ...
# SCRIPT：脚本内容
# KEY_NUMS：key个数
# KEY：多个key，可变参数
# VALUE：多个value，可变参数
# 例
127.0.0.1:6379> eval "return {KEYS[1],KEYS[2],ARGV[1],ARGV[2]}" 2 key1 key2 first second
1) "key1"
2) "key2"
3) "first"
4) "second"
```

#### SCRIPT命令

+ `script load SCRIPT`：将脚本缓存，并返回`SHA1`摘要（使用校验和作为唯一标识）
+ `script exists SHA1`：根据`SHA1`摘要判断脚本是否存在
+ `script kill`：杀死所有正在运行的脚本

+ `script flush`：清除所有脚本缓存

+ 例

  ```sh
  127.0.0.1:6379> script load "return redis.call('set',KEYS[1],ARGV[1])"
  "c686f316aaf1eb01d5a4de1b0b63cd233010e63d"
  127.0.0.1:6379> script exists c686f316aaf1eb01d5a4de1b0b63cd233010e63d
  1) (integer) 1
  127.0.0.1:6379> script kill
  (error) NOTBUSY No scripts in execution right now.
  127.0.0.1:6379> script flush
  OK
  ```

#### EVALSHA

```sh
# 语法: 就是将 eval 的脚本换成 SHA1
127.0.0.1:6379> evalsha SHA1 KEY_NUMS KEY ... VALUE ...
# 例
127.0.0.1:6379> script load "return {KEYS[1],KEYS[2],ARGV[1],ARGV[2]}"
"a42059b356c875f0717db19a51f6aaca9ae659ea"
127.0.0.1:6379> evalsha a42059b356c875f0717db19a51f6aaca9ae659ea 2 1 2 3 4
1) "1"
2) "2"
3) "3"
4) "4"
```

### linux命令行

+ `redis-cli --eval`

  语法：

  ```sh
  $ redis-cli -eval SCRIPT KEY ... , VALUE ...
  ```

  + `SCRIPT`：脚本文件
  + `KEY`：脚本中使用的`key`，可变参数，逗号分隔
  + `VALUE`：脚本中使用的`value`，可变参数，逗号分隔
  + `KEY`和`VALUE`之间使用` , `分割，`,`前后都有空格

+ 例

  + 编写脚本文件`test.lua`

    ```lua
    return {KEYS[1],KEYS[2],ARGV[1],ARGV[2]}
    ```

  + 命令行执行

    ```sh
    root at shuyan in ~
    $ cat test.lua
    return {KEYS[1],KEYS[2],ARGV[1],ARGV[2]}
    
    root at shuyan in ~
    $ redis-cli --eval test.lua 1 2 , 3 4
    1) "1"
    2) "2"
    3) "3"
    4) "4"
    ```

    

# 消息模式（了解）

+ 1V1

  + `lpush`发布消息
  + `brpop`订阅消息

+ 1Vn

  + `publish`发布消息
  + `subscribe`订阅消息

  ![image-20201111013824097](assets/image-20201111013824097.png) 

# Redis Stream（了解）

+ 5.0新增的数据结构
+ 可以看成消息队列
+ 相对于redis之前的发布订阅功能，增加了持久化的特性

# 分布式锁

## 分布式锁特性

分布式锁必须具备如下特性

+ 互斥性：只能有1个人拿到锁
+ 同一性：谁加的锁只能谁解锁

## Redis实现

### 加锁

#### setnx（不推荐）

```java
public boolean tryLock(String key,String requset,int timeout) {
    Long result = jedis.setnx(key, requset);
    // result = 1时，设置成功，否则设置失败
    if (result == 1L) {
        return jedis.expire(key, timeout) == 1L;
    } else {
        return false;
    }
}
```

+ `setnx`与`expire`命令不是原子操作，遇上第一条执行异常或执行完重启了，锁无法过期

#### lua脚本（一般）

```java
public boolean tryLock_with_lua(String key, String UniqueId, int seconds) {
    String lua_scripts = "if redis.call('setnx',KEYS[1],ARGV[1]) == 1 then" +
            "redis.call('expire',KEYS[1],ARGV[2]) return 1 else return 0 end";
    List<String> keys = new ArrayList<>();
    List<String> values = new ArrayList<>();
    keys.add(key);
    values.add(UniqueId);
    values.add(String.valueOf(seconds));
    Object result = jedis.eval(lua_scripts, keys, values);
    //判断是否成功
    return result.equals(1L);
}
```

+ 实现效果上没有问题，但是还是用`setnx`实现，代码比较多

#### set（推荐）

```java
public boolean tryLock_with_set(String key, String UniqueId, int seconds) {
    return "OK".equals(jedis.set(key, UniqueId, "NX", "EX", seconds));
}
```

### 解锁

+ 不可以直接删除`key`，如果真的中途被其他人拿到了锁，这里之间删除`key`将解了别人的锁

+ 使用`lua`脚本先把`value`拿出来看看是不是自己的再解锁

  ```java
  public boolean releaseLock_with_lua(String key,String value) {
      String luaScript = "if redis.call('get',KEYS[1]) == ARGV[1] then " +
              "return redis.call('del',KEYS[1]) else return 0 end";
      return jedis.eval(luaScript, Collections.singletonList(key), Collections.singletonList(value)).equals(1L);
  }
  ```

### 为什么需要设置value值

为了让保证同一性，删除锁的时候先看看是不是自己加的锁，避免删了别人的锁

### Redisson

+ `redisson`是`redis`客户端，比`jsdis`和`lettuce`好，强烈推荐
+ `redisson`支持可重入锁、公平锁、读写锁、联锁、红锁

# 常见问题

## 缓存穿透

+ 问题

  大量请求一直请求数据库不存在的key，redis没有缓存`null`值，这些请求会直接打到数据库

+ 解决方案

  + 缓存`null`值，过期时间很短，数据变更时修改
  + 布隆过滤器

## 缓存雪崩

+ 问题

  大量缓存集中失效，请求直接打到数据库

+ 解决方案

  + 过期时间分散开
  + 使用二级缓存
  + 保证redis服务高可用

## 缓存击穿

+ 问题

  单个缓存过期，正好大量请求过来了，直接打到数据库

+ 解决方案

  + 分布式锁保证不会同时访问数据库

## 双写一致性

+ 问题

  当数据库更新数据，redis需要同步更新时，如何保证最终数据一致性

+ 解决方案

  ```
  update_db
  sleep xxx ms
  rm_redis
  ```

# 调优

## lazy free

+ 4.0新加特性

+ 删除操作不在主进程进行，使用子进程后台删除

+ 参数

  默认都是关闭的

  ```sh
  lazyfree-lazy-eviction no	# 内存淘汰时的删除是否后台删除
  lazyfree-lazy-expire no		# 过期key删除是否后台删除
  lazyfree-lazy-server-del no # 隐式删除是否后台删除（如rename命令）
  slave-lazy-flush no			# 执行flush命令时是否后台删除
  ```

## 内存淘汰策略

+ 默认关闭，建议开启
+ `maxmoemory`：最好设置为物理内存`75%`
+ 配置合适的缓存淘汰策略

## 禁用耗时命令

+ 禁用`keys`，使用`scan`扫描
+ 删除大数据时使用`unlink`代替`del`，删除改为异步

## pipeline批量操作

+ 一组指令一起发送至服务端执行
+ 减少网络开销

