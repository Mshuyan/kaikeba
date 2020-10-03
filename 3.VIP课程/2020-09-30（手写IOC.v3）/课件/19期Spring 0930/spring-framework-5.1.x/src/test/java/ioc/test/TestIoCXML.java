package ioc.test;


import ioc.xml.po.ClassA;
import ioc.xml.po.ClassB;
import ioc.xml.po.Course;
import ioc.xml.po.Student;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TestIoCXML {

	@Test
	public void test() {
		// 指定XML路径
		String path = "spring/beans.xml";
		Resource resource = new ClassPathResource(path);
		XmlBeanFactory beanFactory = new XmlBeanFactory(resource );
		// Bean实例创建流程
		Student student = (Student) beanFactory.getBean("student");
		System.out.println(student);
	}
	// 暗号：天马流星拳
	@Test
	public void test1() {
		// 指定XML路径
		String path = "spring/beans.xml";
		// 创建DefaultListableBeanFactory工厂，这也就是Spring的基本容器
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		// 创建BeanDefinition阅读器
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		// 进行BeanDefinition注册流程
		reader.loadBeanDefinitions(path);
		// Bean实例创建流程
		Student student = (Student) beanFactory.getBean("student");
		System.out.println(student);
	}

	@Test
	public void testInitApplicationContext() throws Exception {
		// 创建IoC容器，并进行初始化
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-ioc.xml");
		System.out.println("===============================");
		// 获取Bean的实例，并验证Bean的实例是否是单例模式的
		Student bean = (Student) context.getBean("student");
		Student bean2 = (Student) context.getBean("classC");
		System.out.println(bean);
		System.out.println(bean2);
	}

	@Test
	public void testInitApplicationContext2() throws Exception {
		// 创建IoC容器，并进行初始化
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-ioc.xml");
		System.out.println("===============================");
		// 获取Bean的实例，并验证Bean的实例是否是单例模式的
		Student bean = (Student) context.getBean(Student.class);
		System.out.println(bean);
	}

	@Test
	public void test2() throws Exception {
		Course course = new Course();
		// 操作单个属性
		PropertyDescriptor pd = new PropertyDescriptor("name", Course.class);
		Class<?> propertyType = pd.getPropertyType();
		System.out.println(propertyType);
		Method w = pd.getWriteMethod();// 获取属性的setter方法
		w.invoke(course, "winclpt");
		System.out.println(course.getName());
		Method r = pd.getReadMethod();// 获取属性的getter方法
		Object object = r.invoke(course, null);
		System.out.println(object.toString());

		// 操作所有属性
		BeanInfo bi = Introspector.getBeanInfo(Course.class);
		PropertyDescriptor[] pds = bi.getPropertyDescriptors();
		for (PropertyDescriptor p : pds) {

		}
	}

	/**
	 * 循环依赖演示
	 * 
	 * @throws Exception
	 */
	@Test
	public void test3() throws Exception {
		// 创建IoC容器，并进行初始化
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-ioc.xml");
		System.out.println("===============================");
		// 获取Bean的实例，并验证Bean的实例是否是单例模式的
		ClassA classA = (ClassA) context.getBean(ClassA.class);
		System.out.println(classA.getClass());
		System.out.println(classA.getClassB().getClass());
		ClassB classB = (ClassB) context.getBean(ClassB.class);
		System.out.println(classB.getClass());
		System.out.println(classB.getClassA().getClass());
	}

}
