package com.kkb.spring.factory.support;

import com.kkb.spring.aware.Aware;
import com.kkb.spring.aware.BeanFactoryAware;
import com.kkb.spring.factory.AutowireCapableBeanFactory;
import com.kkb.spring.ioc.BeanDefinition;
import com.kkb.spring.ioc.PropertyValue;
import com.kkb.spring.resolver.BeanDefinitionValueResolver;
import com.kkb.spring.utils.ReflectUtils;

import java.util.List;

/**
 * 可以实现装配功能的抽象类
 */
public abstract class AbstractAutowireCapableBeanFactory  extends AbstractBeanFactory implements AutowireCapableBeanFactory{

    @Override
    public Object createBean(BeanDefinition bd) {
        // 第一步：对象实例化（new）
        Object bean = createInstanceBean(bd);
        // 第二步：依赖注入（属性填充setter）
        populateBean(bean, bd);
        // 第三步：对象初始化（调用初始化方法）
        initializeBean(bean, bd);

        return bean;
    }


    private void initializeBean(Object bean, BeanDefinition bd) {
        //Aware接口（标记接口，BeanFactoryAware-->对带有标记的bean注入一个BeanFactory）
        if (bean instanceof Aware){
            if (bean instanceof BeanFactoryAware){
                ((BeanFactoryAware)bean).setBeanFactory(this);
            }
        }
        // 初始化的方式有两种：init-method标签属性指定的方法、InitializingBean接口
        invokeInitMethod(bean, bd);
        // AOP产生代理对象，就是在初始化方法中产生的
    }

    private void invokeInitMethod(Object bean, BeanDefinition bd) {
        String initMethod = bd.getInitMethod();
        if (initMethod == null || "".equals(initMethod)) {
            return;
        }
        ReflectUtils.invokeMethod(bd.getClazzType(),initMethod,bean);
    }

    private void populateBean(Object bean, BeanDefinition bd) {
        // 思考：spring的依赖注入是通过属性注入的，还是setter方法注入的？
        List<PropertyValue> propertyValues = bd.getPropertyValues();
        for (PropertyValue pv : propertyValues) {
            String name = pv.getName();
            Object value = pv.getValue();// 此时value是TypeStringValue或者RuntimeBeanReference

            // 获取可以完成依赖注入的值
            BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);
            Object valueToUse = valueResolver.resolveValue(value);

            // 完成属性注入
            ReflectUtils.setProperty(bean, name, valueToUse);
        }
    }

    private Object createInstanceBean(BeanDefinition bd) {
        // TODO 可以从实例工厂中获取一个Bean

        // TODO 可以从静态工厂方法中获取一个Bean
        Object bean = ReflectUtils.createInstance(bd.getClazzType());
        return bean;
    }

}
