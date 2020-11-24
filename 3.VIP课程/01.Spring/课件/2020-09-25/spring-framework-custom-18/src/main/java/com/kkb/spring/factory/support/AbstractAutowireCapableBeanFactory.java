package com.kkb.spring.factory.support;

import com.kkb.spring.ioc.BeanDefinition;
import com.kkb.spring.ioc.PropertyValue;
import com.kkb.spring.resolver.BeanDefinitionValueResolver;
import com.kkb.spring.utils.ReflectUtils;

import java.util.List;

/**
 * 真正实现Bean的创建的类
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{
    @Override
    protected Object createBean(BeanDefinition bd) {
        Object bean = null;
        try {
            // 5.1 Bean的实例化（new）
            bean = createBeanInstance(bd);
            // 5.2 Bean的属性填充（依赖注入）
            populateBean(bean,bd);
            // 5.3 Bean的初始化（调用初始化方法，完成初始化操作）
            initializeBean(bean,bd);
        }catch (Exception e){
            e.printStackTrace();
        }

        return bean;
    }
    private void initializeBean(Object bean, BeanDefinition bd) throws Exception{

        // TODO 处理Aware接口
        // TODO 处理InitializingBean接口的初始化操作

        // 处理init-method标签属性对应的初始化方法
        invokeInitMethod(bean,bd);

    }

    private void invokeInitMethod(Object bean, BeanDefinition bd) throws Exception{
        String initMethod = bd.getInitMethod();
        if (initMethod == null){
            return;
        }

        ReflectUtils.invokeMethod(bean,initMethod);

    }

    private void populateBean(Object bean, BeanDefinition bd) throws Exception{
        List<PropertyValue> propertyValues = bd.getPropertyValues();
        for (PropertyValue pv : propertyValues) {
            String name = pv.getName();
            Object value = pv.getValue();

            // 处理之后的value值
            BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);
            Object valueToUse = valueResolver.resolveValue(value);

            ReflectUtils.setProperty(bean,name,valueToUse);
        }
    }

    private Object createBeanInstance(BeanDefinition bd) throws Exception{
        // TODO 1、通过静态工厂方法去创建Bean的实例
        // TODO 2、通过对象工厂去创建Bean的实例
        Object bean = ReflectUtils.createBean(bd.getClazzType());

        return bean;
    }

}
