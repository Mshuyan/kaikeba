package proxy.factory;

import java.lang.reflect.Proxy;

import proxy.advice.MyInvocationHandler;
import proxy.target.UserService;

/**
 * 主要作用就是生成代理类 使用JDK的动态代理实现 它是基于接口实现的
 * 
 * @author think
 *
 */
public class JDKProxyFactory {

	/**
	 * @return
	 */
	public Object getProxy(Object target) {

		Class<?> clazz = UserService.class;
		Class<?> clazz2 = target.getClass();
		System.out.println(clazz);
		System.out.println(clazz2);
		System.out.println(clazz2.getInterfaces());
		System.out.println(target.getClass().getInterfaces());
		// 如何生成一个代理类呢？
		// 1、编写源文件(java文件)----目录类接口interface实现类（调用了目标对象的方法）
		// class Proxy4{

		// InvocationHandler
		// 目标对象
		// 目标对象的方法
		// void saveUer(){
		// 动态生成的
		// 需要自定义编写
		// InvocationHandler.invoke(){
		// 编写其他逻辑
		// 调用目标对象的方法
		// }

		// }
		// }
		// 2、编译源文件为class文件
		// 3、将class文件加载到JVM中(ClassLoader)
		// 4、将class文件对应的对象进行实例化（反射）

		// Proxy是JDK中的API类
		// 第一个参数：目标对象的类加载器
		// 第二个参数：目标对象的接口
		// 第二个参数：代理对象的执行处理器
//		 Object proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(), new Class[] { clazz2 },new MyInvocationHandler(target));
		Object proxy = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz },
				new MyInvocationHandler(target));
		
		// Proxy.newProxyInstance(loader, interfaces, h);
		return proxy;
	}

}
