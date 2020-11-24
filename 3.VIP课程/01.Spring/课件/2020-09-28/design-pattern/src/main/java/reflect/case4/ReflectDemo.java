package reflect.case4;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 *	通过反射获取成员方法并使用
 *
 *	如何获取成员方法呢？
 *
 *	Class类的成员方法
 *		getMethods()：获取所有公共方法
 *		getDeclaredMethods()：获取所有方法
 *
 *		public Method getMethod(String name,Class<?>... parameterTypes)：获取该类一个公共方法
 *		public Method getDeclaredMethod(String name,Class<?>... parameterTypes)：获取该类一个任意方法
 *
 *	
 *
 */
public class ReflectDemo {

	public static void main(String[] args) throws Exception {
		//获取Class对象
		Class c = Class.forName("reflect.Student");
		
		//getDeclaredMethods()：获取所有公共方法
		//会将父类的公共方法都获取到
		Method[] methods1 = c.getMethods();
		for (Method method : methods1) {
			System.out.println(method);
		}
		System.out.println("========================");
		//getDeclaredMethods()：获取所有方法
		//只会获取本类内的方法
		Method[] methods = c.getDeclaredMethods();
		for (Method method : methods) {
			System.out.println(method);
		}
		System.out.println("==========================");
		
		//getDeclaredMethod(String name,Class<?>... parameterTypes)：获取任意方法
		Method method = c.getDeclaredMethod("show");
		System.out.println(method);
		
		Method method2 = c.getDeclaredMethod("method", String.class);
		System.out.println(method2);
		
		Method method3 = c.getDeclaredMethod("method3", String.class);
		System.out.println(method3);
		
		//使用Method方法
		Constructor constructor = c.getConstructor();
		Object object = constructor.newInstance();
		System.out.println(object);
		
		//调用无参无返回值的
		method.invoke(object);
		
		//调用有参无返回值的
		method2.invoke(object, "张三");
		
		//调用有参有返回值的
		//暴力访问
		method3.setAccessible(true);
		Object result = method3.invoke(object, "张三");
		System.out.println(result);
		
	}

}

