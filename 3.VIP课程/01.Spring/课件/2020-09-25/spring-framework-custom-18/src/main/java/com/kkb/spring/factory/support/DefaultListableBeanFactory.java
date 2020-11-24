package com.kkb.spring.factory.support;

import com.kkb.spring.ioc.BeanDefinition;
import com.kkb.spring.registry.BeanDefinitionRegistry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通过BeanDefinitionRegistry接口去暴露DefaultListableBeanFactory，其实是符合最少认知原则
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {
    private Map<String,BeanDefinition> beanDefinitions = new HashMap<>();

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return this.beanDefinitions.get(beanName);
    }

    @Override
    public List<BeanDefinition> getBeanDefinitions() {
        // TODO
        return null;
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition bd) {
        this.beanDefinitions.put(beanName,bd);
    }
}
