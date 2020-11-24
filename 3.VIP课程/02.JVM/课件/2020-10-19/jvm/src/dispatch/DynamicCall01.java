package dispatch;

//调用方法
public class DynamicCall01 {
	public static void main(String[] args) {
		Father father = new Son();
		// 多态
		father.f1(); //发生了方法的重载，同时也发生了方法的重写 //invokevirtual
		// 打印结果： Son-f1()
	}
}

// 被调用的父类
class Father {
	public void f1() {
		System.out.println("father-f1()");
	}

	public void f1(int i) {
		System.out.println("father-f1() para-int " + i);
	}
}

// 被调用的子类
class Son extends Father {
	public void f1() {
		// 覆盖父类的方法
		System.out.println("Son-f1()");
	}

	public void f1(char c) {
		System.out.println("Son-s1() para-char " + c);
	}
}
