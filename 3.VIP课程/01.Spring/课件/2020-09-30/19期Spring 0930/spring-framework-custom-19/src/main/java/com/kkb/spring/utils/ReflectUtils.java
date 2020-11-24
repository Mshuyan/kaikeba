package com.kkb.spring.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtils {
    public static void invokeMethod(Class clazz,String initMethod,Object bean){
        try {
            Method method = clazz.getDeclaredMethod(initMethod);
            method.invoke(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setProperty(Object bean, String name, Object valueToUse) {
        try {
            Class<?> aClass = bean.getClass();
            Field field = aClass.getDeclaredField(name);
            field.setAccessible(true);
            field.set(bean,valueToUse);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Object createInstance(Class clazz){
        try {
            // 通过构造器去new一个Bean(无参构造，思考：如何实现有参数构造)
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            return constructor.newInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
