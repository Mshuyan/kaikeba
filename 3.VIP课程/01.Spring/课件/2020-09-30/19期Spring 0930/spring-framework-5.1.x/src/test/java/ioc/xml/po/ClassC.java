package ioc.xml.po;

public class ClassC {

	private ClassB classB;
	
	public ClassC(ClassB classB) {
		this.classB = classB;
	}

	public ClassB getClassB() {
		return classB;
	}

	public void setClassB(ClassB classB) {
		this.classB = classB;
	}

}
