package reflect.case2;

import java.lang.reflect.Constructor;

/**
 * 通过反射获取构造方法，并且创建对象
 * 
 * 如何获取一个类的构造方法呢？
 * Class的成员方法
 * 		public Constructor<?>[] getConstructors() :返回该类的所有公共构造方法
 * 		public Constructor<?>[] getDeclaredConstructors() :返回该类的所有构造方法
 * 
 * 		public Constructor<T> getConstructor(Class<?>... parameterTypes) :返回该类的一个公共构造方法
 * 
 * Constructor的成员方法
 * 		public Object newInstance(Object... initargs)
 * @author 怡吾宇
 *
 */
public class ReflectDemo {

	public static void main(String[] args) throws Exception {
		//获取Class对象
		Class c = Class.forName("reflect.Student");
		
		//获取Student类的构造方法
		//Constructor[] constructors = c.getConstructors();
		Constructor[] constructors = c.getDeclaredConstructors();
		// Constructor类中的方法
		// 		String getName()  
		for (Constructor constructor : constructors) {
			System.out.println(constructor.toString());
		}
		System.out.println("=================================");
		//获取一个构造方法
		//Constructor constructor = c.getConstructor();
		
		//java.lang.NoSuchMethodException
		//Constructor constructor = c.getConstructor(String.class,int.class,String.class);
		
		//Constructor constructor = c.getDeclaredConstructor(String.class,int.class,String.class);
		Constructor constructor = c.getDeclaredConstructor(String.class);
		System.out.println(constructor.toString());
		
		System.out.println("=======================");
		//java.lang.IllegalAccessException
		//不能直接将私有构造方法new对象
		
		//暴力访问
		//Object object = constructor.newInstance("张三",40,"北京");
		
		constructor.setAccessible(true);
		Object object = constructor.newInstance("张三");
		System.out.println(object);
		
	}

}

