package reflect;

//定义一个测试反射的类
public class Student{
	private String name;
	int age;
	public String address;
	
	public Student(){}
	
	Student(String name,int age,String address){
		this.name = name;
		this.age = age;
		this.address = address;
	}
	
	@SuppressWarnings("unused")
	private Student(String name){
		this.name = name;
	}
	
	public void show(){
		
	}
	
	public void method(String name){
		System.out.println(name);
	}
	
	public void method2(String name,int age){
		System.out.println(name + "--"+age);
	}
	
	public String method3(String name){
		return "hello,"+name;
	}
	
	@SuppressWarnings("unused")
	private void hello(String name){
		System.out.println("hello,"+name);
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", age=" + age + ", address=" + address + "]";
	}
	
	
}
