package com.kkb.spring.registry;

import java.util.HashMap;
import java.util.Map;

/**
 * 思考：为什么要单独封装一个类呢？
 * 因为在该类中需要对单例bean集合进行很严格的管理操作（线程安全问题）
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry{
    private Map<String,Object> singletonObjects = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return this.singletonObjects.get(beanName);
    }

    @Override
    public void addSingleton(String beanName, Object bean) {
        //TODO 可以使用双重检查锁的方式进行单例的处理
        this.singletonObjects.put(beanName,bean);
    }
}
