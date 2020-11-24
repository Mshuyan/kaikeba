package proxy.target;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

//手写代理类的源代码
//public class $Proxy11 implements 目标类的接口集合{
//
//    // 遍历目标类的接口方法
//    private static Method m1;
//    private static Method m2;
//
//    private InvocationHandler h;
//    public $Proxy11(InvocationHandler h) {
//        this.h = h;
//    }
//
//    // 遍历目标类的接口方法
//    public void save() {
//        try {
//            this.h.invoke(this, m1, null);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//    }
//    public void update() {
//        try {
//            this.h.invoke(this, m2, null);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//    }
//
//    static {
//        try {
//            // 遍历目标类的接口方法
//            m1 = Class.forName("目标类的全路径").getDeclaredMethod("目标类中的方法名称");
//            m2 = Class.forName("目标类的全路径").getDeclaredMethod("目标类中的方法名称");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//}
//
//
//
//
//












// 定义类名的命名规则$ + Proxy + 4
// 代理类为什么知道要实现UserService-接口呢？
// Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz },
//				new MyInvocationHandler(target));

// implements实现的接口，就是newProxyInstance方法的第二个参数，遍历即可
/*
public class $Proxy11 implements UserService {

	// newProxyInstance方法的第三个参数
	private InvocationHandler h;

	public $Proxy11(InvocationHandler h) {
		this.h = h;
	}

	// saveUser对应的方法对象
	private static Method m1;

	// 可以通过反射获取每个接口的方法声明
	@Override
	public void saveUser() {
		try {
			this.h.invoke(this,m1,null);
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}

	static {
		try {
			m1 = Class.forName("com.kkb.UserService").getDeclaredMethod("saveUser");
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
*/
