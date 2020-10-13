package reflect.case7;

/**
 * 需求：
 * 		1：编写一个Student类，里面有两个私有变量：name和age
 * 		2：该类内没有公开set方法，提供赋值操作
 * 		3：编写一个工具类，实现修改任意对象中变量的功能：
 * 			功能定义：
 * 			public void setProperty(Object obj, String propertyName, Object value){}
 * 		
 * 
 * @author 怡吾宇
 *
 */
public class ReflectTest {

	public static void main(String[] args) {
		//想去修改Student类的私有属性值
		Student student = new Student();
		System.out.println(student);
		//调用工具类修改私有变量的值
		ReflectTools.setProperty(student, "name", "张三");
		ReflectTools.setProperty(student, "age", 25);
		
		System.out.println(student);
	}
}
