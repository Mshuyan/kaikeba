package singleton;

import java.io.Serializable;

/**
 * 
 * @author 怡吾宇
 *
 *         双重检查加锁
 */
public class DoubleCheckLockSingleton implements Serializable {

	private static final long serialVersionUID = 1L;

	// 存储单例实例
	// volatile：
	// 1、禁止指令重排序
	// 2、保证可见性
	private volatile static DoubleCheckLockSingleton instance;
	private static boolean flag = false;

	private DoubleCheckLockSingleton() {
		System.out.println("====对象构建开始=====");
		synchronized (DoubleCheckLockSingleton.class) {
			if (false == flag) {
				flag = !flag;
			} else {
				throw new RuntimeException("单例模式正在被攻击");
			}

		}
		System.out.println("====对象构建结束=====");
	}

	public static DoubleCheckLockSingleton getSingletonInstance() {
		if (instance == null) {
			// B线程检测到student不为空
			synchronized (DoubleCheckLockSingleton.class) {
				if (instance == null) {
					instance = new DoubleCheckLockSingleton();
					// A线程被指令重排了，刚好先赋值了；但还没执行完构造函数。
				}
			}
		}
		return instance;// 后面B线程执行时将引发：对象尚未初始化错误。
	}

	/**
	 * 防止序列化攻击
	 * 
	 * @return
	 */
	private Object readResolve() {
		return instance;
	}

	public void tellEveryone() {
		System.out.println("This is a DoubleCheckLockSingleton " + this.hashCode());
	}
}
