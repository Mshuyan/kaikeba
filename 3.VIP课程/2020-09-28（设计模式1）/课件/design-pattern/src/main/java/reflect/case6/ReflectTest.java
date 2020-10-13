package reflect.case6;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * 需求：
 * 		越过泛型检查
 * 		让ArrayList<Integer>中可以存储String类型数据
 * 
 * 知识储备：
 * 		泛型，主要是作用在编译时
 * 
 * 分析：
 * 		1：通过反射去获取ArrayList<Integer>的Class对象
 * 		2：再去通过反射获取ArrayList<Integer>的add方法的Method对象
 * 		3：调用Method对象的invoke方法
 * 
 * @author 怡吾宇
 *
 */
public class ReflectTest {

	public static void main(String[] args) throws Exception {
		
		method1();
		
	}
	
	public static void method1() throws Exception{
		ArrayList<Integer> list = new ArrayList<>();
		list.add(1100);
		//编译时，会进行泛型检查，不允许添加String类型
		//list.add("");
		
		//利用反射解决这个问题
		//2：再去通过反射获取ArrayList<Integer>的add方法的Method对象
		Class c1 = list.getClass();
		Method method = c1.getMethod("add",Object.class);
		
		//3：调用Method对象的invoke方法
		method.invoke(list, "张三");
		method.invoke(list, 1);
		method.invoke(list, 10f);
		
		System.out.println(list);
	}
	
	public static void method2() throws Exception{
	}

}
