package build.director;

import build.builder.StudentBuilder;
import build.product.Student;

// 导演类/测试类
public class BuildDemo {

	public static void main(String[] args) {
		
		StudentBuilder builder = new StudentBuilder();
		// 决定如何创建一个Student
		builder.age(18).name("dili").father("wangjianlin");
		
		//根据不同的表示去创建对象（私人定制）
		Student student = builder.build();
		
		//builder.build(xxxxxxxx).build();
		System.out.println(student);

	}
}
