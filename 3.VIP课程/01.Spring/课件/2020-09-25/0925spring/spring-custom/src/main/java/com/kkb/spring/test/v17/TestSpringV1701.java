package com.kkb.spring.test.v17;

import com.kkb.spring.dao.UserDao;
import com.kkb.spring.dao.UserDaoImpl;
import com.kkb.spring.po.User;
import com.kkb.spring.service.UserServiceImpl;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSpringV1701 {
    // 由A程序员编写
    @Test
    public void test(){
        // A 程序员他其实只想使用业务对象去调用对应的服务
        // 但是现在A程序员还需要去进行业务对象的构建
        // A程序员也不了解业务对象的构造细节
        // 理解IoC

        UserServiceImpl userService = new UserServiceImpl();
        UserDaoImpl userDao = new UserDaoImpl();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://39.105.204.66:3306/kkb?characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        userDao.setDataSource(dataSource);
        userService.setUserDao(userDao);

        //实现用户查询功能
        Map<String, Object> map = new HashMap<>();
        map.put("username","千年老亚瑟");


        List<User> users = userService.queryUsers(map);
        System.out.println(users);
    }
}
