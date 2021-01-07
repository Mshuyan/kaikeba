package com.abc.service;

import com.abc.bean.Depart;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
public class DepartServiceImplFallBack implements DepartService {
    @Override
    public boolean saveDepart(Depart depart) {
        return false;
    }

    @Override
    public boolean removeDepartById(int id) {
        return false;
    }

    @Override
    public boolean modifyDepart(Depart depart) {
        return false;
    }

    @Override//接口降级处理方法：在出现异常或者是找不到对应服务，就走这个服务，而不是给用户输出connection refuse
    public Depart getDepartById(int id) {
        Depart depart = new Depart();
        depart.setName("查无此信息");
        return depart;
    }

    @Override
    public List<Depart> listAllDeparts() {
        return null;
    }
}
