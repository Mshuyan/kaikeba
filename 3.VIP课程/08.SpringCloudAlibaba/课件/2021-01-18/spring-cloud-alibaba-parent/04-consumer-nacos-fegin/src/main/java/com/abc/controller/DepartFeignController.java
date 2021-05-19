package com.abc.controller;

import com.abc.bean.Depart;
import com.abc.service.DepartService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 消费者Controller 基于Feign实现的接口调用
 */
@RestController
@RequestMapping("/feign/consumer/depart")
public class DepartFeignController {

    //Ambiguous mapping 这个BUG其实是SpringMVC的代码的错误！
    //深入源码告诉你为什么？整合Feign
    @Autowired
    private DepartService departService;

    //跨服务新增
    @PostMapping("/save")
    public boolean saveHandle(@RequestBody Depart depart) {
        return departService.saveDepart(depart);
    }

    //跨服务根据id删除
    @DeleteMapping("/del/{id}")
    public void deleteHandle(@PathVariable("id") int id) {
        departService.removeDepartById(id);
    }
    //跨服务修改
    @PutMapping("/update")
    public void updateHandle(@RequestBody Depart depart) {
        departService.saveDepart(depart);
    }
    //跨服务根据id查询
    @GetMapping("/get/{id}")
    public Depart getHandle(@PathVariable("id") int id) {
        return departService.getDepartById(id);
    }

    //跨服务根据列表查询
    @GetMapping("/list")
    public List<Depart> listHandle() {
        return departService.listAllDeparts();
    }
}
