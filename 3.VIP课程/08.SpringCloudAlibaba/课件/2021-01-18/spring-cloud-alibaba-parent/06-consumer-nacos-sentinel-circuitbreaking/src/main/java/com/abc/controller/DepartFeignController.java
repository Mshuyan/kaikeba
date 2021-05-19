package com.abc.controller;

import com.abc.bean.Depart;
import com.abc.service.DepartService;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 消费者Controller
 */
@RestController
@RequestMapping("/feign/consumer/depart")
public class DepartFeignController {

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
        departService.delDepartById(id);
    }
    //跨服务修改
    @PutMapping("/update")
    public void updateHandle(@RequestBody Depart depart) {
        departService.modifyDepart(depart);
    }

    @GetMapping("/get/{id}")
    public Depart getHandle(@PathVariable("id") int id) {
        Depart depart = departService.getDepartById(id);
        return depart;
    }
    //跨服务根据列表查询
    @GetMapping("/list")
    public List<Depart> listHandle() {
        List<Depart> departList = departService.listAllDeparts();
        return departList;
    }
}
