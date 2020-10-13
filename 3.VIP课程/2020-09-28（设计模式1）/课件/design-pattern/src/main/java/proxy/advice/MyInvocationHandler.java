package proxy.advice;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * JDK动态代理使用的动态增强的类
 * 
 * @author think
 *
 */
public class MyInvocationHandler implements InvocationHandler {

	// 目标对象的引用
	private Object target;

	// 通过构造方法将目标对象注入到代理对象中
	public MyInvocationHandler(Object target) {
		super();
		this.target = target;
	}

	/**
	 * 代理对象会执行的方法
	 * 第一个参数：代理对象本身
	 * 第二个参数：目标对象的方法对象（Method对象），一个方法针对一个Method对象
	 * 第三个参数：目标对象的方法参数
	 * 
	 * 代理对象执行的逻辑：
	 * 		需要执行目标对象的原方法？
	 * 			如何执行目标对象的原方法呢？
	 * 			该处使用的是反射
	 * 			【要调用方法的Method对象.invoke(要调用方法的对象,要调用方法的参数)】
	 * 		只是在调用目标对象的原方法前边和后边可能要加上一些增强功能的代码
	 * 
	 * 增强代码比如：
	 * 		在原方法调用之前，开启事务，源方法结束之后，提交和回滚事务
	 * 
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		System.out.println("这是jdk的代理方法");
		// 下面的代码，是反射中的API用法
		// 该行代码，实际调用的是[目标对象]的方法
		// 利用反射，调用[目标对象]的方法
		Object returnValue = method.invoke(target, args);
		// AopUtils.invokeJoinpointUsingReflection(this.advised, method, args);
		
		// ReflectiveMethodInvocation.proceed()进行递归增强
		
		// 增强的部分
		return returnValue;
	}
}
