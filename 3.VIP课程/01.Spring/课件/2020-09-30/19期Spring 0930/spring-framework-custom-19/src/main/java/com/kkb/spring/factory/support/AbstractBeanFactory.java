package com.kkb.spring.factory.support;

import com.kkb.spring.factory.BeanFactory;
import com.kkb.spring.ioc.BeanDefinition;
import com.kkb.spring.registry.DefaultSingletonBeanRegistry;

/**
 * 定义getBean的整体流程
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String beanName) {
        // 1、从缓存中获取对应的bean实例（map结构）---beanname为key
        Object bean = getSingleton(beanName);
        if (bean != null) {
            return bean;
        }
        // 2、如果没有对应的bean实例，此时需要xml解析出来的对应的信息（map结构中的BeanDefinition）--beanname为key
        BeanDefinition bd = getBeanDefinition(beanName);
        if (bd == null) {
            return null;
        }
        // 3、根据BeanDefinition的信息去创建Bean实例
        // 判断要创建的是单例的bean还是多例的bean
        if (bd.isSingleton()) {
            bean = createBean(bd);
            // 细化创建Bean的流程
            // 4、将创建出来的bean实例放入缓存
            addSingleton(beanName, bean);
        } else if (bd.isPrototype()) {
            bean = createBean(bd);
        }
        return bean;
    }

    /**
     * 抽象模板方法，将某个具体功能交给子类（AbstractAutowireCapableBeanFactory）去实现
     * @param bd
     * @return
     */
    protected abstract Object createBean(BeanDefinition bd);

    /**
     * 抽象模板方法，将某个具体功能交给子类(DefaultListableBeanFactory)去实现
     * @param beanName
     * @return
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName);
}
