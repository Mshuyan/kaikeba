package com.kkb.spring.factory.support;

import com.kkb.spring.factory.BeanFactory;
import com.kkb.spring.ioc.BeanDefinition;
import com.kkb.spring.registry.BeanDefinitionRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 最终的spring工厂对象
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {
    // 存储配置文件中的bean的定义信息
    private Map<String, BeanDefinition> beanDefinitions = new HashMap<>();


    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return this.beanDefinitions.get(beanName);
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        this.beanDefinitions.put(beanName,beanDefinition);
    }

    @Override
    public List<BeanDefinition> getBeanDefinitions() {
        List<BeanDefinition> beanDefinitionList = new ArrayList<>();
        for(BeanDefinition bd:beanDefinitions.values()){
            beanDefinitionList.add(bd);
        }
        return beanDefinitionList;
    }
}
