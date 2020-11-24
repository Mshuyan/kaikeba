package prototype;

public class PrototypeDemo {

	public static void main(String[] args) throws Exception{
		// 创建原对象
		Prototype prototype = new Prototype();
		prototype.setName("zhangsan");
		prototype.setObject(new SerializableObject());
		
		System.out.println("克隆之前的对象："+prototype.getObject());
		// 浅复制出来的对象
		Prototype clone = (Prototype) prototype.shallowClone();
		System.out.println("浅复制出来的对象："+clone.getObject());
		
		// 深复制出来的对象
		Prototype deepClone = (Prototype) prototype.deepClone();
		System.out.println("深复制出来的对象："+deepClone.getObject());
	}
}
