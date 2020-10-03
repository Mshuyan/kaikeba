package com.kkb.spring.factory;

import com.kkb.spring.ioc.BeanDefinition;

/**
 * 可装配的bean工厂
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    Object createBean(BeanDefinition bd);
}
