package singleton;
/**
 * 对getSingletonInstance方法加synchronized关键字
 * 
 * 
 * synchronized关键字锁住的是这个对象，这样的用法，在性能上会有所下降
 * 因为每次调用getInstance()，都要对对象上锁
 * 事实上，只有在第一次创建对象的时候需要加锁，之后就不需要了
 * 所以，这个地方需要改进
 * 
 * @author 怡吾宇
 *
 */
public class Student3 {

	private Student3(){}
	
	private static Student3 student = null;
	
	// 此处考验对synchronized知识点的掌握情况
	public static synchronized Student3 getSingletonInstance(){
		if (student == null) {
			student = new Student3();
		}
		return student;
	}
	
}
