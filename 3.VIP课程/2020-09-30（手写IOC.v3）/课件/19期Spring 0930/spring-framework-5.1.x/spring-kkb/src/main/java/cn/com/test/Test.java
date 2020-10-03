package cn.com.test;

import cn.com.app.Appconfig;

import cn.com.service.X;
import cn.com.service.Y;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) {
		//上下文初始化完成
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext("cn.com");
//		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(Appconfig.class);

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		System.out.println(ac.getBean(X.class));
		ac.close();
	}


}
