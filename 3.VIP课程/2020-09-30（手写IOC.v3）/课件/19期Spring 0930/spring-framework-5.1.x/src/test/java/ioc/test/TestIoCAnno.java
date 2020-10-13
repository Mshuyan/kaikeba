package ioc.test;

import ioc.annotation.po.Student;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestIoCAnno {

	@Test
	public void test() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("ioc.annotation.po");
		Student student = context.getBean(Student.class);
		System.out.println(student);
		context.close();
	}

}
