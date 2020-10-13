package singleton;
/**
 * 单例实现之懒汉式实现
 * 
 * 
 * 思想：需要对象的时候，再去创建对象
 * 
 * 懒汉式设计模式，有一种叫法：延迟加载
 * 
 * 
 * 懒汉式单例模式步骤：
 * 		1：构造私有
 * 		2：定义私有静态成员变量，先不初始化
 * 		3：定义公开静态方法，获取本身对象
 * 			有对象就返回已有对象
 * 			没有对象，再去创建
 * 
 * 线程安全问题，判断依据：
 * 		1：是否存在多线程	是
 * 		2：是否有共享数据	是
 * 		3：是否存在非原子性操作	
 * 
 * 
 * 
 * 这个类可以满足基本要求，但是，懒汉式单例实现，存在线程安全隐患，如何解决？
 * 我们首先会想到对getSingletonInstance方法加synchronized关键字，SingletonFactory3
 * 
 * @author 怡吾宇
 *
 */
public class Student2 {

	//1：构造私有
	private Student2(){}
	
	//2：定义私有静态成员变量，先不初始化
	private static Student2 student = null;
	
	//3：定义公开静态方法，获取本身对象
	public static Student2 getSingletonInstance(){
		//没有对象，再去创建
		if (student == null) {
			student = new Student2();
		}
		//有对象就返回已有对象
		return student;
	}
	
}
