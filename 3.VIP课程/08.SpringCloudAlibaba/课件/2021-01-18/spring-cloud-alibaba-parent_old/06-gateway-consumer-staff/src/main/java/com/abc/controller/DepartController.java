package com.abc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepartController {

    //跨服务根据列表查询
    @GetMapping("/consumer/staff/get/{id}")
    public String getHandle(@PathVariable("id") int id) {
        return "获取到的staff的id为：" + id;
    }
}
