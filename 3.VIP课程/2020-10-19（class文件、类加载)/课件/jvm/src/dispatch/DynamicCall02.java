package dispatch;

/**
 * 多态--动态分配
 * @author 灭霸詹
 *
 */
public class DynamicCall02 {
	static abstract class Human{
		protected abstract void sayHello();
	}
	
	static class Man extends Human{

		@Override
		protected void sayHello() {
			System.out.println("hello,James！");
			
		}	
	}
	static class Woman extends Human{

		@Override
		protected void sayHello() {
			System.out.println("hello,girl！");
			
		}
	}
	
	public static void main(String[]args){
		Human h1 = new Man();
		Human h2 = new Woman();
		h1.sayHello();
		h2.sayHello();


		
	}
}
