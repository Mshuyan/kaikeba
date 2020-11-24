package factory_method.factory;

import factory_method.product.Animal;
import factory_method.product.Dog;

//具体的工厂实现类
public class DogFactory extends AnimalFactory {

	@Override
	public Animal createAnimal() {
		return new Dog();
	}
}
