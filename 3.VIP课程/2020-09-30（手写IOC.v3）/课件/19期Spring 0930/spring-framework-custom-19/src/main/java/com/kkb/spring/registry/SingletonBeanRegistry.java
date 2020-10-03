package com.kkb.spring.registry;

/**
 * 专门用来存储和管理单例Bean对象的注册器
 */
public interface SingletonBeanRegistry {

    /**
     * 获取指定名称的单例Bean
     * @param beanName
     * @return
     */
    Object getSingleton(String beanName);

    /**
     * 添加新的bean实例到注册器中
     * @param beanName
     * @param bean
     */
    void addSingleton(String beanName,Object bean);
}
