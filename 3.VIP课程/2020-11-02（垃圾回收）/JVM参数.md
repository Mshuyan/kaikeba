> 官方文档：https://docs.oracle.com/javase/8/docs/technotes/tools/unix/java.html 

# 分类

+ 标准参数
  + 通过`java`命令即可查看
+ 非标准参数
  + 通过`java -X`查看
+ 非stable参数
  + 通过`java -XX:+PrintFlagsFinal`查看

# 标准参数

+ `-d32`

  以32位环境运行

+ `-d64`

  以64位环境运行

+ `-client`

  客户端模式启动

+ `-server`

  服务端模式启动

  64位机器默认该模式

+ `-cp`

  





-XX:CMSInitiatingOccupancyFraction

+CMSClassUnloadingEnabled



-server -Xms1280m -Xmx1280m **-Xmn128m** -Xss256k -XX:PermSize=96m -XX:MaxPermSize=96m -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:CMSFullGCsBeforeCompaction=1 -XX:+UseCMSCompactAtFullCollection -XX:+CMSParallelRemarkEnabled -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -XX:+CMSClassUnloadingEnabled -XX:+DisableExplicitGC -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCTimeStamps