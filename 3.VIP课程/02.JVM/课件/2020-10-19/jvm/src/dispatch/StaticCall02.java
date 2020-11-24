package dispatch;

/**
 * 重载方法-静态分配
 * @author 灭霸詹
 *
 */
public class StaticCall02{
	
	static abstract class Human{}
	static class Man extends Human{	}
	static class Woman extends Human{}
	
	public void sayHello(Human guy){
		System.out.println("hello,人类！");//1
	}
	public void sayHello(Man guy){
		System.out.println("hello,老铁！");//2
	}
	public void sayHello(Woman guy){
		System.out.println("hello,老妹！");//3
	}
	
	public static void main(String[]args){
		
		Human h1 = new Man();
		Human h2 = new Woman();
		
		StaticCall02 sd = new StaticCall02();
		sd.sayHello(h1);
		sd.sayHello(h2);

		
	}
}
