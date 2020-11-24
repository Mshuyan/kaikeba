package simple_factory.factory;

import java.util.HashMap;
import java.util.Map;

import simple_factory.product.Animal;
import simple_factory.product.Cat;
import simple_factory.product.Dog;

//简单工厂，也称为上帝工厂
public class AnimalFactory {

	// 简单工厂设计模式（负担太重、不符合开闭原则）
	public static Animal createAnimal(String name) {

		if ("cat".equals(name)) {
			return new Cat();
		} else if ("dog".equals(name)) {
			return new Dog();
		} else if ("cow".equals(name)) {
			return new Dog();
		} else {
			return null;
		}

	}

	public static Object createObject(String name) {

		if ("cat".equals(name)) {
			return new Cat();
		} else if ("dog".equals(name)) {
			return new Dog();
		} else if ("cow".equals(name)) {
			return new Dog();
		} else {
			return null;
		}
	}

	public static Object getBean(String name) {

		// 优化方案
		// 给对象起个名，在xml配置文件中，建立名称和对象的映射关系

		Map<String, Object> map = new HashMap<>();// Map中的数据怎么来？
		Object object = map.get(name);
		return object;
	}
}
