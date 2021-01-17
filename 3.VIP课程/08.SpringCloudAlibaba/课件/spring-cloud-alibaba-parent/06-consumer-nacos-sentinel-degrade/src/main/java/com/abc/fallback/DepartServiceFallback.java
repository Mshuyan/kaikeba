package com.abc.fallback;

import com.abc.bean.Depart;
import com.abc.service.DepartService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * feign客户端的服务降级处理类
 * @RequestMapping("/fallback/consumer/depart") 主要是为了解决bug的
 */
@Component//注意：服务降级处理类必须注入Spring的容器中
@RequestMapping("/fallback/consumer/depart")
public class DepartServiceFallback implements DepartService {
    @Override
    public boolean saveDepart(Depart depart) {
        System.out.println("执行saveDepart()的服务降级处理方法");
        return false;
    }

    @Override
    public boolean delDepartById(int id) {
        System.out.println("执行delDepartById()的服务降级处理方法");
        return false;
    }

    @Override
    public boolean modifyDepart(Depart depart) {
        System.out.println("执行modifyDepart()的服务降级处理方法");
        return false;
    }

    @Override
    public Depart getDepartById(int id) {
        System.out.println("执行getDepartById()的服务降级处理方法");
        Depart depart = Depart.builder().name("degrade-feign").id(id).build();
        return depart;
    }

    @Override
    public List<Depart> listAllDeparts() {
        System.out.println("执行listAllDeparts()的服务降级处理方法");
        return null;
    }
}
