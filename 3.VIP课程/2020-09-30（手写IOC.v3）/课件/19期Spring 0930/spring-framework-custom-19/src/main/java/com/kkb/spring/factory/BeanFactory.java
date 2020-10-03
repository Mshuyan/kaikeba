package com.kkb.spring.factory;

public interface BeanFactory {

    /**
     * 根据bean名称获取单个bean实例
     * @param beanName
     * @return
     */
    Object getBean(String beanName) ;
}
