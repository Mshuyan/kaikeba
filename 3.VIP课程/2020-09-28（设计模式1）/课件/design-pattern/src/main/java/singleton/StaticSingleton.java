package singleton;

import java.io.Serializable;

/**
 * 
 * @author 怡吾宇
 *
 */
public class StaticSingleton implements Serializable {

	private static final long serialVersionUID = 1L;

	private StaticSingleton() {
	}

	/*
	 * 此处使用一个内部类来维护单例 JVM在类加载的时候，是互斥的，所以可以由此保证线程安全问题
	 */
	private static class SingletonFactory {
		private static StaticSingleton singleton = new StaticSingleton();
	}

	/* 获取实例 */
	public static StaticSingleton getSingletonInstance() {
		return SingletonFactory.singleton;
	}

}

class Test {

	public static void main(String[] args) {
		StaticSingleton singleton = StaticSingleton.getSingletonInstance();
		System.out.println(singleton);
	}
}