package proxy.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 主要作用就是生成代理类 使用JDK的动态代理实现 它是基于接口实现的
 * 
 * @author think
 *
 */
public class AopProxy implements InvocationHandler {

	private Object target;

	public AopProxy(Object target) {
		this.target = target;
	}

	public Object getProxy() {

		Object proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
				this);

		return proxy;
	}

	/**
	 * 产生代理对象的过程中和InvocationHandler接口的invoke方法是否有关系？？？？
	 * 
	 * 什么时候产生代理对象？---- ioc容器创建bean的时候，会触发aop，产生代理对象，放入ioc容器
	 * 什么时候调用代理对象？---- 当第一次调用目标类接口的实例的时候，才是第一次去调用代理对象。
	 * 代理对象的处理逻辑是在哪呢？也就是在哪个类组织增强代码和目标代码的结合的呢？----InvocationHandler的invoke方法
	 * 有没有可能产生了代理对象，却从没有调用过？---可能
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		System.out.println("before");
		method.invoke(target, args);
		return null;
	}

}
