package com.abc.service;

import com.abc.bean.Depart;
import com.abc.fallback.DepartServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Feign客户端
 * value属性：设置访问服务名称
 * fallback属性：设置服务降级处理类
 */
@FeignClient(value = "msc-provider-depart", fallback = DepartServiceFallback.class)
@RequestMapping("/provider/depart")
public interface DepartService {

    @PostMapping("/save")
    boolean saveDepart(@RequestBody Depart depart);

    @DeleteMapping("/del/{id}")
    boolean delDepartById(@PathVariable("id") int id);

    @PutMapping("/update")
    boolean modifyDepart(@RequestBody Depart depart);

    @GetMapping("/get/{id}")
    Depart getDepartById(@PathVariable("id") int id);

    @GetMapping("/list")
    List<Depart> listAllDeparts();
}
