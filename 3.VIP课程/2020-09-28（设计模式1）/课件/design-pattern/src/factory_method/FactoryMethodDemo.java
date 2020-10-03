package factory_method;

import factory_method.factory.AnimalFactory;
import factory_method.factory.CatFactory;
import factory_method.factory.DogFactory;
import factory_method.product.Cat;
import factory_method.product.Dog;

public class FactoryMethodDemo {

	public static void main(String[] args) {
		// 我想买只猫
		Cat cat = new Cat();
		cat.eat();
		// 我想要只狗
		Dog dog = new Dog();
		dog.eat();

		System.out.println("=========");

		AnimalFactory catFactory = new CatFactory();
		Cat cat2 = (Cat) catFactory.createAnimal();
		cat2.eat();
		
		AnimalFactory dogFactory = new DogFactory();
		Dog dog2 = (Dog) dogFactory.createAnimal();
		dog2.eat();
	}
}
