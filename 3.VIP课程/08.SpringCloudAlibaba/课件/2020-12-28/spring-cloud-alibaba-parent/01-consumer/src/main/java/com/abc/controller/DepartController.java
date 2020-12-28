package com.abc.controller;

import com.abc.bean.Depart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 消费者Controller
 */
@RestController
@RequestMapping("/consumer/depart")
public class DepartController {
    //注入RestTemplate模板对象
    @Autowired
    private RestTemplate restTemplate;

    //发送http请求的，http://localhost:8081地址写死，去访问提供者行不行呢？
    //行、不行【】
    //原因1：如果我服务提供者集群部署：无法访问集群
    //原因2：如果服务发布上线，或者后期服务迁移，需要经常性修改配置文件【1万个服务呢】
    //原因：....负载均衡、熔断

    //配置请求地址
    private static final String SERVICE_PROVIDER = "http://localhost:8081";

    //跨服务新增
    @PostMapping("/save")
    public boolean saveHandle(@RequestBody Depart depart) {
        String url = SERVICE_PROVIDER + "/provider/depart/save";
        //TODO 问题：消费者直连提供者行不行？
        Boolean result = restTemplate.postForObject(url, depart, Boolean.class);
        return result;
    }
    //跨服务根据id删除
    @DeleteMapping("/del/{id}")
    public void deleteHandle(@PathVariable("id") int id) {
        String url = SERVICE_PROVIDER + "/provider/depart/del/" + id;
        restTemplate.delete(url);
    }
    //跨服务修改
    @PutMapping("/update")
    public void updateHandle(@RequestBody Depart depart) {
        String url = SERVICE_PROVIDER + "/provider/depart/update";
        restTemplate.put(url, depart, Boolean.class);
    }
    //跨服务根据id查询
    @GetMapping("/get/{id}")
    public Depart getHandle(@PathVariable("id") int id) {
        String url = SERVICE_PROVIDER + "/provider/depart/get/" + id;
        Depart depart = restTemplate.getForObject(url, Depart.class);
        return depart;
    }
    //跨服务根据列表查询
    @GetMapping("/list")
    public List<Depart> listHandle() {
        String url = SERVICE_PROVIDER + "/provider/depart/list/";
        List list = restTemplate.getForObject(url, List.class);
        return list;
    }
}
