package com.kkb.spring.factory;

/**
 * spring容器的顶级接口
 */
public interface BeanFactory {
    /**
     * 根据bean的名称获取对应的实例
     * @param beanName
     * @return
     */
    Object getBean(String beanName);
}
