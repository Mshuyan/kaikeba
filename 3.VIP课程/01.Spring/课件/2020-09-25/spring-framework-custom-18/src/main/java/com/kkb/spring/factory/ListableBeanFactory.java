package com.kkb.spring.factory;

import java.util.List;

/**
 * 可列表化操作的Bean工厂
 */
public interface ListableBeanFactory extends BeanFactory{

    /**
     * 根据给定的类型，获取它以及它子类型的所有实例
     * @param type
     * @param <T>
     * @return
     */
    <T> List<T> getBeansByType(Class type);

    List<String> getBeanNamesByType(Class type);
}
