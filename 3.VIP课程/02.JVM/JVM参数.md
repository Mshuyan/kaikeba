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

  `-classpath`的缩写
  
+ `-classpath`

  + 指定`classpath`执行程序

  + 默认`classpath`是在环境变量中配置的

    ```sh
    export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
    ```

    `jvm`加载`class`文件时都是从这些路径加载

+ `-D<name>=<value>`

  传递系统参数，程序中可以获取

  ```java
  System.getProperty(“propertyName”)
  ```

+ `-verbose`

  + 在输出设备上显示虚拟机运行情况

  + 可选参数

    + `class`

      + 语法

        ```sh
        $ java -verbose a.jar
        # 或
        $ java -verbose:class a.jar
        ```

      + 打印类加载信息

    + `gc`

      + 语法

        ```sh
        $ java -verbose:gc a.jar
        ```

      + `gc`时打印`gc`信息

    + `jni`

      + 语法

        ```sh
        $ java -verbose:jni a.jar
        ```

      + 打印本地（`native`）方法调用情况

+ `-version`

  打印`jdk`版本

+ `-showversion`

  程序启动前先打印`jdk`版本信息

+ `-? -help`

  帮助文档

+ `-X`

  打印非标准参数

+ `-ea[:<packagename>...|:<classname>]`

  `-enableassertions`的缩写

+ `-enableassertions[:<packagename>...|:<classname>]`

  + 启用用户类断言功能（默认关闭）

  + 语法

    ```sh
    # 打开所有用户类断言功能
    $ java -jar -ea a.jar
    # 打开MyClass类断言功能
    $ java -jar -ea:MyClass a.jar
    # 打开 com.learn 这个包下面的类的断言功能
    $ java -jar -ea:com.learn a.jar
    # 打开缺省包下面的类（无包名的类）的断言功能
    $ java -jar -ea:... a.jar
    # 打开 com.learn 及其子包下面的类的断言功能
    $ java -jar -ea:com.learn... a.jar
    ```

+ `-da[:<packagename>...|:<classname>]`

  `-disableassertions`的缩写

+ `-disableassertions[:<packagename>...|:<classname>]`

  + 关闭用户类的断言功能（默认关闭）
  + 语法：同 `-ea`

+ `-esa | -enablesystemassertions`

  + 打开系统类（jdk自带的类）的断言功能（默认关闭）

+ `-dsa | -disablesystemassertions`

  + 关闭系统类（jdk自带的类）的断言功能（默认关闭）

+ `-agentlib:<libname>[=<options>]`

  + 按库名装载本地库

  + 搜索路径为环境变量中的`PATH`中的路径

    + windows搜索`*.dll`文件，linux搜索`*.so`文件

  + 参数

    + `libname`：库名
    + `options`：传递给库的启动参数

  + 语法

    ```sh
    $ java -jar -agentlib:hrof a.jar
    ```

+ `-agentpath:<pathname>[=<options>]`

  + 根据路径转载本地库，不再搜索`PATH`中的路径

+ `-javaagent:<jarpath>[=<options>]`

  + 指定`jvm`启动时装入`java`语言基础设施代理
  + `jarpath`文件中的`mainfest`文件必须有`Premain-Class`（启动前捆绑时需要）、`Agent-Class`（运行时捆绑时需要）属性。代理类也必须实现公共的静态`public static void premain(String agentArgs, Instrumentation inst)`方法（和main方法类似）。当`jvm`初始化时，将按代理类的说明顺序调用`premain`方法

+ `-splash:<imagepath>`

  运行程序前先把指定图片打印出来

  没测试成功

+ `-jar`

  执行指定`jar`包

# 非标准参数

> + https://www.cnblogs.com/holos/p/6616028.html

+ `-Xmixed`
  + 动态编译 + 解释 执行
  + 默认就是
+ `-Xint`
  + 解释方式执行程序
+ `-Xcomp`
  + 编译方式执行程序
+ `-Xbootclasspath<option>:<directories and zip/jar files separated by :>`
  + 指定`BootstrapCloassloader`的加载路径
  + 参数
    + `option`
      + 无：完全替代（不要使用）
      + `/a`：原有类之后加载（可以使用）
      + `/p`：原有类之前加载（不要使用）
    + `file`
      + 要加载的类的路径或文件，`:`分割
+ `-Xdiag`
  + 显示附加诊断信息
+ `-Xnoclassgc`
  + 禁用类垃圾收集
+ `-Xincgc`
  + 启用增量垃圾收集
+ `-Xloggc:<file>`
+ `-Xbatch`
+ `-Xms<size>`
+ `-Xmx<size>`
+ `-Xmn<size>`
+ `-Xss<size>`
+ `-Xprof`
+ `-Xfuture`
+ `-Xrs`
+ `-Xcheck:jni`
+ `-Xshare:<option>`
+ `-XshowSettings<:option>`



-server -Xms1280m -Xmx1280m **-Xmn128m** -Xss256k -XX:PermSize=96m -XX:MaxPermSize=96m -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:CMSFullGCsBeforeCompaction=1 -XX:+UseCMSCompactAtFullCollection -XX:+CMSParallelRemarkEnabled -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -XX:+CMSClassUnloadingEnabled -XX:+DisableExplicitGC -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCTimeStamps