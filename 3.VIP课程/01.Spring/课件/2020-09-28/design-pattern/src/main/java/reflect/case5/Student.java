package reflect.case5;

public class Student {

	private String name;
	private int age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public Student() {
		super();
 	}
	public Student(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	public String sayHello(String name){
		return this.name + "说，sorry , "+name+",我们不合适，我已经"+ age +"岁了";
	}
}
