package reflect.case1;

import reflect.Student;

/**
 * 获取Class类对象的方式
 * 
 * 有三种：
 * 		a) 通过对象获取
 * 			Person obj = new Person();
 * 			Class c =obj.getClass();
 * 		b）通过静态属性获取
 * 			Class c = Person.class;
 * 		c）通过静态方法获取，使用字符串表示的类名
 * 			Class c = Class.forName("类的全路径");
 * 
 * 第三种方式和前两种的区别		
 * 		前两种你必须明确Person类型.
 * 		后面是给我这种类型的字符串表示形式就行.这种扩展更强.我不需要知道你的类.我只提供字符串,按照配置文件加载就可以了
 * 
 * Class类是为了描述java中类的信息的
 * 		成员变量	Field
 * 		构造方法	Constructor
 * 		成员方法	Method
 * 
 * 
 * 
 * @author 怡吾宇
 *
 */
public class ReflectDemo {

	public static void main(String[] args) throws ClassNotFoundException {
		//a) 通过对象获取
		Student s1 = new Student();
		Class c1 = s1.getClass();
		
		Student s2 = new Student();
		Class c2 = s2.getClass();
		
		System.out.println(s1 ==s2);//false
		System.out.println(c1 == c2);//true
		
		//b）通过静态属性获取
		Class c3 = Student.class;
		System.out.println(c1 == c3);
		
		//c）通过静态方法获取，使用字符串表示的类名
		Class c4 = Class.forName("reflect.Student");
		System.out.println(c1 == c4);
	}

}

