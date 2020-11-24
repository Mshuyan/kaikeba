//package cn.com.bean;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.BeanDefinition;
//import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
//import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
//import org.springframework.beans.factory.support.AbstractBeanDefinition;
//
//public class KKBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
//	/*
//	方法调用时机是spring完成扫描之后，放入BeanDefinitionMap之后   封装自己的操作
//	 */
//	@Override
//	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//		AbstractBeanDefinition x = (AbstractBeanDefinition) beanFactory.getBeanDefinition("x");
//		x.setBeanClass(Z.class);
//	}
//}
