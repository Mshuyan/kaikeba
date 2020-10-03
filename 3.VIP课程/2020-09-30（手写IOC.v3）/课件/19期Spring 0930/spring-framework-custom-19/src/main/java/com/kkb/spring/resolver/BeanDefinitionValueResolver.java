package com.kkb.spring.resolver;

import com.kkb.spring.factory.BeanFactory;
import com.kkb.spring.factory.support.DefaultListableBeanFactory;
import com.kkb.spring.ioc.RuntimeBeanReference;
import com.kkb.spring.ioc.TypedStringValue;

public class BeanDefinitionValueResolver {

    private BeanFactory beanFactory;

    public BeanDefinitionValueResolver(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object resolveValue(Object value){
        if (value instanceof TypedStringValue){
            TypedStringValue typedStringValue = (TypedStringValue) value;
            String stringValue = typedStringValue.getValue();
            Class<?> targetType = typedStringValue.getTargetType();
            if (targetType != null){
                // 根据类型做类型处理(使用策略模式优化)
                if (targetType == Integer.class){
                    return Integer.parseInt(stringValue);
                }else if (targetType == String.class){
                    return stringValue;
                }
            }
            return stringValue;
        }else  if (value instanceof RuntimeBeanReference){
            RuntimeBeanReference beanReference = (RuntimeBeanReference) value;
            String ref = beanReference.getRef();
            // 此处会发生循环依赖问题（后面会去讲）
            return beanFactory.getBean(ref);
        }// Map类型、Set类型、List类型
        return null;
    }
}
