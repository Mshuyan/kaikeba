package build.builder;

import build.product.Father;
import build.product.Student;

// 构建器
public class StudentBuilder {

	// 需要构建的对象
	private Student student = new Student();

	public StudentBuilder id(int id) {
		student.setId(id);
		return this;
	}

	public StudentBuilder name(String name) {
		student.setName(name);
		return this;
	}

	public StudentBuilder age(int age) {
		student.setAge(age);
		return this;
	}

	public StudentBuilder father(String fatherName) {
		Father father = new Father();
		father.setName(fatherName);
		student.setFather(father);
		return this;
	}

	// 构建对象
	public Student build() {
		return student;
	}
}
