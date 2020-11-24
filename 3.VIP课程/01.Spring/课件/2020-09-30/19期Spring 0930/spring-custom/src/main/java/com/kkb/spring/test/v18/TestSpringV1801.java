package com.kkb.spring.test.v18;


import com.kkb.spring.dao.UserDaoImpl;
import com.kkb.spring.po.User;
import com.kkb.spring.service.UserServiceImpl;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 写测试代码的是A同学
 * 写业务功能的是B同学(写的代码是以jar包的方式依赖过去的)
 */
public class TestSpringV1801 {


    /**
     * A同学根本不想关心UserServiceImpl对象是怎么new的，他只是想测试查询功能。
     * IoC要做的事情，就是让使用对象的同学只需要找工厂去要对应的对象即可，不需要自己创建。
     * IoC是将创建对象的权利，由程序员这边，反转给spring容器去创建
     */
    @Test
    public void test(){
        UserServiceImpl userService = new UserServiceImpl();
        UserDaoImpl userDao = new UserDaoImpl();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://47.113.81.149:3306/kkb?characterEncoding=utf8");
        dataSource.setUsername("root");
        dataSource.setPassword("kkb0826");

        userDao.setDataSource(dataSource);
        userService.setUserDao(userDao);

        Map<String,Object> map = new HashMap<>();
        map.put("username","千年老亚瑟");
        List<User> users = userService.queryUsers(map);
        System.out.println(users);
    }
}
