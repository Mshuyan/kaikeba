package com.mp.demo.controller;

import com.alibaba.druid.pool.DruidDataSource;
import com.mp.demo.JsonResult;
import com.mp.demo.config.DruidConfiguration;
import com.mp.demo.config.SpringUtils;
import com.mp.demo.entity.UserInfoEntity;
import com.mp.demo.serivce.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.UUID;

@RestController
@RefreshScope
@RequestMapping("/user")
public class UserInfoController
{

    @Autowired
    private UserInfoService userInfoService;

    @Value("${user.name:}")
    private String userName;

    @Value("${spring.datasource.url:}")
    private String jdbcUrl;

    @Autowired
    private DruidConfiguration druidConfiguration;

    @RequestMapping("save")
    @ResponseBody
    public JsonResult save()
    {
        UserInfoEntity settingEntity = new UserInfoEntity();
        settingEntity.setName("test_" + UUID.randomUUID());
        userInfoService.save(settingEntity);
        return JsonResult.newInstanceSuccess();
    }

    @RequestMapping("read")
    @ResponseBody
    public JsonResult loadSetting()
    {
        UserInfoEntity bizUserSettingEntity = userInfoService.getById(1);
        return JsonResult.newInstanceSuccess(bizUserSettingEntity);
    }

    @GetMapping("/refresh")
    public String refresh() throws SQLException
    {
        DruidDataSource master = SpringUtils.getBean("dataSource");
        master.setUrl(druidConfiguration.getDbUrl());
        master.setUsername(druidConfiguration.getUsername());
        master.setPassword(druidConfiguration.getPassword());
        master.setDriverClassName(druidConfiguration.getDriverClassName());
        master.restart();
        return userName + "<>" + jdbcUrl + "----------" + druidConfiguration.getDbUrl();
    }
}
