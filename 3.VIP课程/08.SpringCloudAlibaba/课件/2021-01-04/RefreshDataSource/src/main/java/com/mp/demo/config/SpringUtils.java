package com.mp.demo.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SpringUtils implements ApplicationContextAware
{

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        SpringUtils.applicationContext = applicationContext;
    }

    public static <T> T getBean(String beanName)
    {
        if (applicationContext.containsBean(beanName))
        {
            return (T) applicationContext.getBean(beanName);
        }
        else
        {
            return null;
        }
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> baseType)
    {
        return applicationContext.getBeansOfType(baseType);
    }

    public static ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }
}