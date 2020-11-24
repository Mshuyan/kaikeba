package reflect.case5;

import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.SocketImpl;
import java.util.Properties;

/**
 * 需求：
 * 		1：通过配置文件运行类中的方法
 * 		2：配置文件中的格式都是K-V的
 * 		3：配置文件中有两个Key，分别是className和methodName
 * 
 * 分析：
 * 		1：创建一个配置文件eflect.txt，编写className和methodName的value值
 * 		2：使用Properties类读取reflect.txt文件
 * 		3：通过Properties方法获取className和methodName的值
 * 		4：利用反射，获取className对应的类的对象
 * 		5：利用反射，获取methodName对应的Method对象
 * 		6：调用Method对象的invoke方法
 * 
 * @author 怡吾宇
 *
 */
public class ReflectTest {

	public static void main(String[] args) throws Exception {
		//2：使用Properties类读取reflect.txt文件
		Properties properties = new Properties();
		properties.load(new FileReader("reflect.txt"));
		
		//3：通过Properties方法获取classNa
		String className = properties.getProperty("className");
		String methodName = properties.getProperty("methodName");
		
		//4：利用反射，获取className对应的类的对象
		Class c = Class.forName(className);
		Constructor constructor = c.getConstructor(String.class,int.class);
		Object object = constructor.newInstance("张三",40);
		
		//5：利用反射，获取methodName对应的Method对象
		Method method = c.getMethod(methodName, String.class);
		
		//6：调用Method对象的invoke方法
		Object result = method.invoke(object, "小红");
		
		System.out.println(result);
		
	}

}
