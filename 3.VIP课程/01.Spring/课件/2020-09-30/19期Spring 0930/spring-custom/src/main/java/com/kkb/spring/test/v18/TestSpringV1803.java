package com.kkb.spring.test.v18;

import com.kkb.spring.factory.support.DefaultListableBeanFactory;
import com.kkb.spring.po.User;
import com.kkb.spring.reader.XmlBeanDefinitionReader;
import com.kkb.spring.resource.ClasspathResource;
import com.kkb.spring.resource.Resource;
import com.kkb.spring.service.UserService;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用面向对象思维和配置文件的方式去实现容器化管理Bean
 */
public class TestSpringV1803 {
    private DefaultListableBeanFactory beanFactory;

    @Before
    public void before(){

        String location = "beans.xml";
        // 获取流对象
        // 策略模式
        Resource resource = new ClasspathResource(location);
        //InputStream inputStream = resource.getResource();

        // 按照spring定义的标签语义去解析Document文档
        beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

        beanDefinitionReader.loadBeanDefinitions(resource);
//        beanDefinitionReader.loadBeanDefinitions(location);
    }

    @Test
    public void test() throws Exception{
        UserService userService = (UserService) beanFactory.getBean("userService");

        Map<String,Object> map = new HashMap<>();
        map.put("username","千年老亚瑟");
        List<User> users = userService.queryUsers(map);
        System.out.println(users);
    }

}
