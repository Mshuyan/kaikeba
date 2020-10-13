package simple_factory;

import simple_factory.factory.AnimalFactory;
import simple_factory.factory.AnimalFactory2;
import simple_factory.product.Cat;
import simple_factory.product.Dog;

public class SimpleFactoryDemo {

	public static void main(String[] args) {
		//我想买只猫
		Cat cat = new Cat();
		cat.eat();
		//我想要只狗
		Dog dog = new Dog();
		dog.eat();
		
		System.out.println("=========");
		
		Cat cat2 = (Cat) AnimalFactory.createAnimal("cat");
		Cat cat3 = AnimalFactory2.createCat();
		cat2.eat();
		cat3.eat();
		
		Dog dog2 = (Dog) AnimalFactory.createAnimal("dog");
		Dog dog3 = AnimalFactory2.createDog();
		dog2.eat();
		dog3.eat();
	}

}
