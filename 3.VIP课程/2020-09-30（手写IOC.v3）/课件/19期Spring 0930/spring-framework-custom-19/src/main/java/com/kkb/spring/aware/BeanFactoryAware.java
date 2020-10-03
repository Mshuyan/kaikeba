package com.kkb.spring.aware;

import com.kkb.spring.factory.BeanFactory;

/**
 * 当一个被BeanFactory管理的bean想使用BeanFactory的时候，只需要实现的该接口就可以了
 */
public interface BeanFactoryAware extends Aware{

    void setBeanFactory(BeanFactory beanFactory);
}
