package proxy.factory;

import com.sun.istack.internal.NotNull;
import proxy.advice.MyInvocationHandler;
import proxy.target.UserService;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;


/**
 * 主要作用就是生成代理类 使用JDK的动态代理实现 它是基于接口实现的
 * 
 * @author think
 *
 */
public class JDKProxyFactory implements InvocationHandler {

	private static final String ln = "\r\n";

	private Object target;

	public JDKProxyFactory(Object target) {
		this.target = target;
	}

	/**
	 * @return
	 */
	public Object getProxy() {

		Class<?> clazz = UserService.class;
		Class<?> clazz2 = target.getClass();
		System.out.println(clazz);
		System.out.println(clazz2);
		System.out.println(clazz2.getInterfaces());
		System.out.println(target.getClass().getInterfaces());
		// 如何生成一个代理类呢？
		// 1、编写源文件(java文件)----目录类接口interface实现类（调用了目标对象的方法）
		// class Proxy4{

		// InvocationHandler
		// 目标对象
		// 目标对象的方法
		// void saveUer(){
		// 动态生成的
		// 需要自定义编写
		// InvocationHandler.invoke(){
		// 编写其他逻辑
		// 调用目标对象的方法
		// }

		// }
		// }
		// 2、编译源文件为class文件
		// 3、将class文件加载到JVM中(ClassLoader)
		// 4、将class文件对应的对象进行实例化（反射）

		// Proxy是JDK中的API类
		// 第一个参数：目标对象的类加载器
		// 第二个参数：目标对象的接口
		// 第二个参数：代理对象的执行处理器
		// Object proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(), new
		// Class[] { clazz2 },new MyInvocationHandler(target));
		Object proxy = Proxy.newProxyInstance(clazz.getClassLoader(),
				new Class[] { clazz },
				new MyInvocationHandler(target));

		// Proxy.newProxyInstance(loader, interfaces, h);
		return proxy;
	}

	public Object getProxy2() throws Exception {
		/**
		 * loader：目标类对应的类加载器 interfaces：目标类对应的接口数组
		 */

//		return newProxyInstance(target.getClass().getClassLoader(),
//				target.getClass().getInterfaces(),
//				this);

		return Proxy.newProxyInstance(
				target.getClass().getClassLoader(),
				target.getClass().getInterfaces(),
				this);
	}

	/**
	 * 演示JDK动态代理产生代理对象的底层原理
	 * @return
	 */
	public Object newProxyInstance(ClassLoader classLoader,
								   @NotNull Class<?>[] interfaces,
								   @NotNull InvocationHandler h) {
		try {
			// 1.根据目标类的接口信息，去动态编写代理对象的源代码（java代码---字符串）
			String sourceCode = generateSourceCode(interfaces);

			String path = JDKProxyFactory.class.getResource("").getPath();
			File f = new File(path + "$Proxy0.java");
			FileWriter fw = new FileWriter(f);
			fw.write(sourceCode);
			fw.close();
			
			// 2.使用JDK自带的API完成javac编译功能，将java源代码编译成class文件
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
			Iterable iterable = manager.getJavaFileObjects(f);

			CompilationTask task = compiler.getTask(null, manager, null, null, null, iterable);
			task.call();
			manager.close();

			// 3.使用目标类对应的类加载器完成代理类的class文件的类加载
			//ClassLoader classLoader = target.getClass().getClassLoader();
			Class<?> clazz = classLoader.loadClass("$Proxy0");
			// 4.而JVM就会根据代理类的class信息创建代理对象。
			Constructor<?> constructor = clazz.getDeclaredConstructor(InvocationHandler.class);
			// newInstance第三个参数也有用了
			Object proxy = constructor.newInstance(h);

			return proxy;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String generateSourceCode(Class<?>[] interfaces) {
		StringBuffer sb = new StringBuffer();
		sb.append("package proxy.target;" + ln);
		sb.append("import java.lang.reflect.InvocationHandler;" + ln);
		sb.append("import java.lang.reflect.Method;" + ln);

		sb.append("public class $Proxy"+18+" implements " );

		for(int i = 0;i<interfaces.length ;i++){
			sb.append(interfaces[0].getName() );
			sb.append(",");
		}
		sb.substring(0,sb.length()-1);
		// 去掉最后的,
		sb.append(" { "+ ln);

		sb.append("private InvocationHandler h;" + ln);

		//遍历接口中的方法
		sb.append("private static Method m1;" + ln);

		sb.append("public $Proxy0(InvocationHandler h) {" + ln);
		sb.append("this.h = h;" + ln);
		sb.append("}" + ln);

		//遍历接口中的方法
		sb.append("public void saveUser() {" + ln);
		sb.append("try {" + ln);
		sb.append("h.invoke(this, m1, null);" + ln);
		sb.append("} catch (Throwable e) {" + ln);
		sb.append("e.printStackTrace();" + ln);
		sb.append("}" + ln);
		sb.append("}" + ln);
				sb.append("static {" + ln);
		sb.append("try {" + ln);

		//遍历接口中的方法
		sb.append("m1 = Class.forName(\"com.kkb.UserServiceImpl\").getDeclaredMethod(\"saveUser\");" + ln);


		sb.append("} catch (Exception e) {" + ln);
		sb.append("e.printStackTrace();" + ln);
		sb.append("}" + ln);
		sb.append("}" + ln);
		sb.append("}" + ln);

		return sb.toString();
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// 代理对象方法执行流程
		// 调用增强功能（调用自定义的类+方法）
		// 需要调用目标对象的目标方法
		Object returnValue = method.invoke(target, args);
		// 调用增强功能（调用自定义的类+方法）


		// 为了解决目标对象被多个增强功能进行增强的时候，如何不重复修改InvocationHandler里面的代码？
		// 将增强功能封装到一个个Advice(aop联盟提供的接口MethodInterceptor)--MethodInterceptor集合（MethodInterceptor可配置）
		// MethodInvocation.proceed();--以此为入口，完成MethodInterceptor的链式调用或者说是递归调用
		return returnValue;
	}
}
