package singleton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;

/**
 * 单例破坏案例演示
 * 
 * @author 灭霸詹
 *
 */
public class SingletonAttack {
	public static void main(String[] args) throws Exception {
		// serializationAttack1();
		// reflectionAttack1();
		// serializationAttack2();
		reflectionAttack2();
		// serializationAttack3();
		// reflectionAttack3();
	}

	/**
	 * 破坏单例之双重检查锁反射攻击
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void reflectionAttack1() throws Exception {
		// 通过反射，获取单例类的私有构造器
		Constructor constructor = DoubleCheckLockSingleton.class.getDeclaredConstructor();
		// 设置私有成员的暴力破解
		constructor.setAccessible(true);
		// 通过反射去创建单例类的多个不同的实例
		DoubleCheckLockSingleton s1 = (DoubleCheckLockSingleton) constructor.newInstance();
		// 通过反射去创建单例类的多个不同的实例
		DoubleCheckLockSingleton s2 = (DoubleCheckLockSingleton) constructor.newInstance();
		s1.tellEveryone();
		s2.tellEveryone();
		System.out.println(s1 == s2);
	}

	/**
	 * 破坏单例之双重检查锁序列化攻击
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static void serializationAttack1() throws Exception {
		// 对象序列化流去对对象进行操作
		ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("serFile"));
		// 通过单例代码获取一个对象
		DoubleCheckLockSingleton s1 = DoubleCheckLockSingleton.getSingletonInstance();
		// 将单例对象，通过序列化流，序列化到文件中
		outputStream.writeObject(s1);

		// 通过序列化流，将文件中序列化的对象信息读取到内存中
		ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File("serFile")));
		// 通过序列化流，去创建对象
		DoubleCheckLockSingleton s2 = (DoubleCheckLockSingleton) inputStream.readObject();
		s1.tellEveryone();
		s2.tellEveryone();

		System.out.println(s1 == s2);
	}

	/**
	 * 破坏单例之静态内部类反射攻击
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void reflectionAttack2() throws Exception {
		// 通过反射，获取单例类的私有构造器
		Constructor constructor = StaticSingleton.class.getDeclaredConstructor();
		// 设置私有成员的暴力破解
		constructor.setAccessible(true);
		// 通过反射去创建单例类的多个不同的实例
		StaticSingleton s1 = (StaticSingleton) constructor.newInstance();
		// 通过反射去创建单例类的多个不同的实例
		StaticSingleton s2 = (StaticSingleton) constructor.newInstance();
		System.out.println(s1 == s2);
	}

	/**
	 * 破坏单例之静态内部类序列化攻击
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static void serializationAttack2() throws Exception {
		// 对象序列化流去对对象进行操作
		ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("serFile"));
		// 通过单例代码获取一个对象
		StaticSingleton s1 = StaticSingleton.getSingletonInstance();
		// 将单例对象，通过序列化流，序列化到文件中
		outputStream.writeObject(s1);

		// 通过序列化流，将文件中序列化的对象信息读取到内存中
		ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File("serFile")));
		// 通过序列化流，去创建对象
		StaticSingleton s2 = (StaticSingleton) inputStream.readObject();

		System.out.println(s1 == s2);
	}

	/**
	 * 破坏单例之枚举反射攻击
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void reflectionAttack3() throws Exception {
		// 通过反射，获取单例类的私有构造器
		Constructor constructor = EnumSingleton.class.getDeclaredConstructor();
		// 设置私有成员的暴力破解
		constructor.setAccessible(true);
		// 通过反射去创建单例类的多个不同的实例
		EnumSingleton s1 = (EnumSingleton) constructor.newInstance();
		// 通过反射去创建单例类的多个不同的实例
		EnumSingleton s2 = (EnumSingleton) constructor.newInstance();
		System.out.println(s1 == s2);
	}

	/**
	 * 破坏单例之枚举序列化攻击
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static void serializationAttack3() throws Exception {
		// 对象序列化流去对对象进行操作
		ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("serFile"));
		// 通过单例代码获取一个对象
		EnumSingleton s1 = EnumSingleton.INSTANCE;
		// 将单例对象，通过序列化流，序列化到文件中
		outputStream.writeObject(s1);

		// 通过序列化流，将文件中序列化的对象信息读取到内存中
		ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File("serFile")));
		// 通过序列化流，去创建对象
		EnumSingleton s2 = (EnumSingleton) inputStream.readObject();

		System.out.println(s1 == s2);
	}
}
