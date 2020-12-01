# Lambda

> + 参考资料：https://juejin.cn/post/6896112296049934343

## 特点

+ `Lambda`表达式，强调的是，剔除掉繁琐的格式，关注代码实现过程，将实现过程（方法）作为参数传递

## 格式

```java
// 格式：(参数列表) -> {代码块}
// 例：
(a,b) -> return a>b;
```

## 前提条件

+ 满足`函数式接口`要求的接口，才可以使用`Lambda`表达式
+ 通过`lambda`使用该类型接口的地方，必须将该接口类型作为参数或局部变量使用

## 函数式接口

### 要求

+ 有且仅有1个抽象方法的接口

+ demo

  ```java
  @FunctionalInterface
  public interface MyFunctionalInterface {
  	void myMethod();
  }
  ```

### @FunctionalInterface

+ 仅用于检查该接口是不是只有1个抽象方法，不加该注解也能正常使用

### 常用函数式接口

+ 函数式接口基本都在`java.util.function`包下

#### Supplier

+ 包路径：`java.util.function.Supplier<T>`

+ 功能：供给，提供

+ 抽象方法：`T get()`

+ 源码：

  ```java
  @FunctionalInterface
  public interface Supplier<T> {
  
      /**
       * Gets a result.
       *
       * @return a result
       */
      T get();
  }
  ```

#### Consumer

+ 包路径：`java.util.function.Consumer<T>`

+ 功能：消费数据

+ 抽象方法：`void accept(T t)`

+ 源码：

  ```java
  @FunctionalInterface
  public interface Consumer<T> {
  
      /**
       * Performs this operation on the given argument.
       *
       * @param t the input argument
       */
      void accept(T t);
  
      /**
       * Returns a composed {@code Consumer} that performs, in sequence, this
       * operation followed by the {@code after} operation. If performing either
       * operation throws an exception, it is relayed to the caller of the
       * composed operation.  If performing this operation throws an exception,
       * the {@code after} operation will not be performed.
       *
       * @param after the operation to perform after this operation
       * @return a composed {@code Consumer} that performs in sequence this
       * operation followed by the {@code after} operation
       * @throws NullPointerException if {@code after} is null
       */
      default Consumer<T> andThen(Consumer<? super T> after) {
          Objects.requireNonNull(after);
          return (T t) -> { accept(t); after.accept(t); };
      }
  }
  ```

#### Function

+ 包路径：`java.util.function.Function<T,R>`

+ 功能：通过`T`类型数据得到`R`类型数据

+ 抽象方法：`R apply(T t)`

+ 源码：

  ```java
  @FunctionalInterface
  public interface Function<T, R> {
  
      /**
       * Applies this function to the given argument.
       *
       * @param t the function argument
       * @return the function result
       */
      R apply(T t);
  
      /**
       * Returns a composed function that first applies the {@code before}
       * function to its input, and then applies this function to the result.
       * If evaluation of either function throws an exception, it is relayed to
       * the caller of the composed function.
       *
       * @param <V> the type of input to the {@code before} function, and to the
       *           composed function
       * @param before the function to apply before this function is applied
       * @return a composed function that first applies the {@code before}
       * function and then applies this function
       * @throws NullPointerException if before is null
       *
       * @see #andThen(Function)
       */
      default <V> Function<V, R> compose(Function<? super V, ? extends T> before) {
          Objects.requireNonNull(before);
          return (V v) -> apply(before.apply(v));
      }
  
      /**
       * Returns a composed function that first applies this function to
       * its input, and then applies the {@code after} function to the result.
       * If evaluation of either function throws an exception, it is relayed to
       * the caller of the composed function.
       *
       * @param <V> the type of output of the {@code after} function, and of the
       *           composed function
       * @param after the function to apply after this function is applied
       * @return a composed function that first applies this function and then
       * applies the {@code after} function
       * @throws NullPointerException if after is null
       *
       * @see #compose(Function)
       */
      default <V> Function<T, V> andThen(Function<? super R, ? extends V> after) {
          Objects.requireNonNull(after);
          return (T t) -> after.apply(apply(t));
      }
  
      /**
       * Returns a function that always returns its input argument.
       *
       * @param <T> the type of the input and output objects to the function
       * @return a function that always returns its input argument
       */
      static <T> Function<T, T> identity() {
          return t -> t;
      }
  }
  ```

#### Predicate

+ 包路径：`java.util.function.Predicate<T>`

+ 功能：通过`T`类型数据得到判断结果

+ 抽象方法：`boolean test(T t)`

+ 源码：

  ```java
  @FunctionalInterface
  public interface Predicate<T> {
  
      /**
       * Evaluates this predicate on the given argument.
       *
       * @param t the input argument
       * @return {@code true} if the input argument matches the predicate,
       * otherwise {@code false}
       */
      boolean test(T t);
  
      /**
       * Returns a composed predicate that represents a short-circuiting logical
       * AND of this predicate and another.  When evaluating the composed
       * predicate, if this predicate is {@code false}, then the {@code other}
       * predicate is not evaluated.
       *
       * <p>Any exceptions thrown during evaluation of either predicate are relayed
       * to the caller; if evaluation of this predicate throws an exception, the
       * {@code other} predicate will not be evaluated.
       *
       * @param other a predicate that will be logically-ANDed with this
       *              predicate
       * @return a composed predicate that represents the short-circuiting logical
       * AND of this predicate and the {@code other} predicate
       * @throws NullPointerException if other is null
       */
      default Predicate<T> and(Predicate<? super T> other) {
          Objects.requireNonNull(other);
          return (t) -> test(t) && other.test(t);
      }
  
      /**
       * Returns a predicate that represents the logical negation of this
       * predicate.
       *
       * @return a predicate that represents the logical negation of this
       * predicate
       */
      default Predicate<T> negate() {
          return (t) -> !test(t);
      }
  
      /**
       * Returns a composed predicate that represents a short-circuiting logical
       * OR of this predicate and another.  When evaluating the composed
       * predicate, if this predicate is {@code true}, then the {@code other}
       * predicate is not evaluated.
       *
       * <p>Any exceptions thrown during evaluation of either predicate are relayed
       * to the caller; if evaluation of this predicate throws an exception, the
       * {@code other} predicate will not be evaluated.
       *
       * @param other a predicate that will be logically-ORed with this
       *              predicate
       * @return a composed predicate that represents the short-circuiting logical
       * OR of this predicate and the {@code other} predicate
       * @throws NullPointerException if other is null
       */
      default Predicate<T> or(Predicate<? super T> other) {
          Objects.requireNonNull(other);
          return (t) -> test(t) || other.test(t);
      }
  
      /**
       * Returns a predicate that tests if two arguments are equal according
       * to {@link Objects#equals(Object, Object)}.
       *
       * @param <T> the type of arguments to the predicate
       * @param targetRef the object reference with which to compare for equality,
       *               which may be {@code null}
       * @return a predicate that tests if two arguments are equal according
       * to {@link Objects#equals(Object, Object)}
       */
      static <T> Predicate<T> isEqual(Object targetRef) {
          return (null == targetRef)
                  ? Objects::isNull
                  : object -> targetRef.equals(object);
      }
  }
  ```

# Stream

> + 参考资料：https://juejin.cn/post/6896614244385554446

## 获取流

+ 所有的`Collection`集合都可以通过`stream`默认方法获取流；
+ `Stream`接口的静态方法`of`可以获取数组对应的流。

## 常用方法

+ `forEach`：遍历
+ `filter`：过滤
+ `count`：统计
+ `limit`：取前几个
+ `skip`：跳过前几个
+ `concat`：组合

## 方法引用符

+ 如果Lambda要表达的函数方案 , 已经存在于某个方法的实现中，那么则可以使用方法引用符调用该方法，简化代码。

  自动将`lambda`表达式的参数作为该方法的参数

+ demo

  ```java
  List<MyData> datas = new ArrayList<>();
  Set<MyData> dataSet = new HashSet<>();
  
  datas.stream().forEach(data -> System.out.println(data));
  // 可使用 方法引用符 简化
  datas.stream().forEach(System.out::println);
  ```

+ 使用方式
  + 引用成员方法

    ```java
    datas.stream().forEach(dataSet::add);
    ```

  + 引用静态方法

    ```java
    datas.stream().forEach(MyData::addAtomic);
    ```

  + 引用类构造方法

    ```java
    datas.stream().forEach(MyData::new);
    ```

  + 引用数组构造方法

    ```java
    int[] array = createArray(int[]::new, 3);
    ```

    

