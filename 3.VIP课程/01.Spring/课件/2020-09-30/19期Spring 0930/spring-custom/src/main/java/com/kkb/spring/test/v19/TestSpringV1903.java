package com.kkb.spring.test.v19;

import com.kkb.spring.factory.support.DefaultListableBeanFactory;
import com.kkb.spring.po.User;
import com.kkb.spring.reader.XmlBeanDefinitionReader;
import com.kkb.spring.resource.ClasspathResource;
import com.kkb.spring.resource.Resource;
import com.kkb.spring.service.UserService;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSpringV1903 {

    private DefaultListableBeanFactory beanFactory;

    @Before
    public void before(){
        // TODO 注册BeanDefinition
        beanFactory = new DefaultListableBeanFactory();

        Resource resource = new ClasspathResource("beans.xml");

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        beanDefinitionReader.loadBeanDefinitions(resource);
    }

    @Test
    public void test(){
        // getBean
        UserService userService = (UserService) beanFactory.getBean("userService");
        Map<String, Object> map = new HashMap<>();
        map.put("username", "千年老亚瑟");
        List<User> users = userService.queryUsers(map);
        System.out.println(users);
    }
}