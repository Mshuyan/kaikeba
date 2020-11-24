package com.kkb.spring.registry;

import java.util.HashMap;
import java.util.Map;

/**
 * 真正的spring的该类，需要三个缓存，因为要处理循环依赖问题
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry{
    // 存储单例Bean的集合（多例或者叫原型Bean不存储到集合）
    private Map<String, Object> singletonObjects = new HashMap<>();


    @Override
    public Object getSingleton(String beanName) {
        return this.singletonObjects.get(beanName);
    }

    @Override
    public void addSingleton(String beanName, Object bean) {
        // TODO 使用双重检查锁机制去优化
        this.singletonObjects.put(beanName,bean);
    }
}
