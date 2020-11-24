package com.kkb.spring.factory;

import java.util.List;

/**
 * 可列表化操作spring容器中的bean
 */
public interface ListableBeanFactory extends BeanFactory{

    /**
     * 可以针对指定类型，来获取它或者它类型的bean实例
     * @param clazz
     * @param <T>
     * @return
     */
    <T> List<T> getBeansByType(Class clazz);

    /**
     * 可以针对指定类型，来获取它或者它类型的bean的名称
     * 注意事项：在spring容器中，有了bean的名称，那就可以获取任意的bean实例或者BeanDefinition
     * @param clazz
     * @return
     */
    List<String> getNamesByType(Class clazz);
}
