package com.kkb.spring.factory.support;

import com.kkb.spring.factory.BeanFactory;
import com.kkb.spring.ioc.BeanDefinition;
import com.kkb.spring.registry.DefaultSingletonBeanRegistry;

/**
 * BeanFactory的抽象实现
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    @Override
    public Object getBean(String beanName) {
        // 1、先从单例Bean的map集合中查找
        Object singletonObject = getSingleton(beanName);

        // 2、如果存在，则直接返回该bean
        if (singletonObject != null){
            return singletonObject;
        }

        // 3、不存在，则从Bean的定义信息集合中查找对应的BeanDefinition
        BeanDefinition bd = getBeanDefinition(beanName);
        if (bd == null){
            return null;
        }

        // 4、判断该Bean是单例还是多例
//        String scope = bd.getScope();
//        if ("singleton".equals(scope)){
//
//        }else if("prototype".equals(scope)){
//
//        }

        // 5、创建Bean
        if (bd.isSingleton()){
            singletonObject = createBean(bd);
            // 6、创建完Bean，加入单例Bean的集合
            addSingleton(beanName,singletonObject);
        }else if (bd.isPrototype()){
            singletonObject = createBean(bd);
        }

        return singletonObject;
    }

    /**
     * 使用抽象模板方法，将真正的实现逻辑延迟到子类（DefaultListableBeanFactory中实现）
     * @param beanName
     * @return
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName);

    /**
     * 使用抽象模板方法，将真正的实现逻辑延迟到子类（AbstractAutowireCapableBeanFactory中实现）
     * @param bd
     * @return
     */
    protected abstract Object createBean(BeanDefinition bd) ;

    // 暗号：助教找媳妇
}
