package prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 原型模式 理解克隆的作用：在原对象的基础上，完全复制一个新的对象（属性都是新的）
 * 浅复制：新对象的简单类型和String类型可以复制为新的，但是引用对象还是喝原对象的一样。 深复制：完全复制一个新的对象（属性都是新的）
 * 
 * @author think
 *
 */
public class Prototype implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;

	// 简单类型或者String类型
	private String name;

	// 引用类型
	private SerializableObject object;

	
	/* 浅复制 */
	public Object shallowClone() throws CloneNotSupportedException {
		// super.clone()其实就是调用了Object对象的clone方法
		// Object对象的clone方法是调用了native方法去在JVM中实现对象复制。
		// 此时不会调用构造方法去创建对象
		Prototype proto = (Prototype) super.clone();
		return proto;
	}

	/*
	 * 深复制
	 * 
	 * 要实现深复制，需要采用流的形式读入当前对象的二进制输入，再写出二进制数据对应的对象。
	 */
	public Object deepClone() throws IOException, ClassNotFoundException {

		/* 将对象序列化到二进制流 */
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(this);

		/* 从二进制流中读出产生的新对象 */
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);
		return ois.readObject();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SerializableObject getObject() {
		return object;
	}

	public void setObject(SerializableObject object) {
		this.object = object;
	}
	
	
}

//定义一个类，为了演示深浅复制
class SerializableObject implements Serializable {
	private static final long serialVersionUID = 1L;
}
