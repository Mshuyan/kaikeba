package singleton;

public class SingletonDemo {

	public static void main(String[] args) {
		// 饿汉式
		System.out.println(Student1.getSingletonInstance());
		// 懒汉式
		System.out.println(Student2.getSingletonInstance());
		System.out.println(Student3.getSingletonInstance());
		System.out.println(Student4.getSingletonInstance());
		System.out.println(DoubleCheckLockSingleton.getSingletonInstance());
	}

}
