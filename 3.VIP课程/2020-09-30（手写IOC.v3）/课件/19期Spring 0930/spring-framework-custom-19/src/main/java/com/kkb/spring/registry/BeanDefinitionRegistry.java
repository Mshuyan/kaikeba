package com.kkb.spring.registry;

import com.kkb.spring.ioc.BeanDefinition;

import java.util.List;

/**
 * 专门用来存储和管理BeanDefinition信息的
 */
public interface BeanDefinitionRegistry {

    BeanDefinition getBeanDefinition(String beanName);

    void registerBeanDefinition(String beanName,BeanDefinition beanDefinition) ;

    List<BeanDefinition> getBeanDefinitions();
}
