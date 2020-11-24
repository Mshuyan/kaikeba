package dispatch;

//调用静态方法 import com.kkb.test.Target;
public class StaticCall01 {
	public static void main() {
		Target.f1(); // 调用静态方法
	}
}

class Target {
	public static void f1() {
		System.out.println("Target — f1()");
	}
}