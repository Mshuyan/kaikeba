package proxy.advice;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * cglib动态代理使用的动态增强的类
 * 
 * @author think
 *
 */
public class MyMethodInterceptor implements MethodInterceptor {

	/***
	 * Object proxy:这是代理对象，也就是[目标对象]的子类
	 * Method method:[目标对象]的方法 
	 * Object[] arg:参数
	 * MethodProxy methodProxy：代理对象的方法
	 */
	@Override
	public Object intercept(Object proxy, Method method, Object[] arg, MethodProxy methodProxy) throws Throwable {
		// 因为代理对象是目标对象的子类
		// 该行代码，实际调用的是父类目标对象的方法
		System.out.println("这是cglib的代理方法");

		// 通过调用子类[代理类]的invokeSuper方法，去实际调用[目标对象]的方法
		// Object returnValue = method.invoke(target, arg);
		Object returnValue = methodProxy.invokeSuper(proxy, arg);
		
		//	method.invoke(target, arg);
		
		//目标对象的saveUser(){}
		//代理对象saveUser(){
		//super();
		//}
		
		// 代理对象调用代理对象的invokeSuper方法，而invokeSuper方法会去调用目标类的invoke方法完成目标对象的调用

		return returnValue;
	}

}
