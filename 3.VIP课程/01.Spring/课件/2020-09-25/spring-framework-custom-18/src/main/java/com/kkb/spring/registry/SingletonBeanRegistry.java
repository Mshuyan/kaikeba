package com.kkb.spring.registry;

/**
 * 提供对实现类中管理的单例Bean集合进行操作功能
 */
public interface SingletonBeanRegistry {
    Object getSingleton(String beanName);

    void addSingleton(String beanName,Object bean);
}
