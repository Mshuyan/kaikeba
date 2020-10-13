package reflect.case3;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 *	通过反射获取成员变量并使用
 *
 *	如何获取成员变量呢？
 *
 *	Class类的成员方法
 *		getFields()：获取该类所有公共变量
 *		getDeclaredFields：获取该类所有变量
 *
 *		public Field getField(String name)：获取该类指定名称的公共变量
 *		public Field getDeclaredField(String name)：获取该类指定名称的变量
 *
 *	Field类的成员方法
 *		public void set(Object obj,Object value)：给指定对象的该属性修改值
 *
 */
public class ReflectDemo {

	public static void main(String[] args) throws Exception {
		//获取Class对象
		Class c = Class.forName("reflect.Student");
		
		//获取该类的所有公共变量
		/*Field[] fields = c.getFields();
		for (Field field : fields) {
			System.out.println(field.toString());
		}*/
		
		//获取该类所有变量
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields) {
			System.out.println(field.toString());
		}
		
		System.out.println("=======================");
		//获取单个成员变量
		Field field = c.getField("address");
		System.out.println(field.toString());
		
		//java.lang.NoSuchFieldException: age
		//Field field2 = c.getField("age");
		//System.out.println(field2.toString());
		
		Field field2 = c.getDeclaredField("name");
		System.out.println(field2.toString());
		
		System.out.println("=============================");
		Constructor constructor = c.getConstructor();
		Object object = constructor.newInstance();
		System.out.println(object);
		field.set(object, "北京");
		System.out.println(object);
		
		//私有变量
		//暴力访问
		field2.setAccessible(true);
		//修改私有成员变量
		field2.set(object, "张三");
		System.out.println(object);
		
		
	}

}

