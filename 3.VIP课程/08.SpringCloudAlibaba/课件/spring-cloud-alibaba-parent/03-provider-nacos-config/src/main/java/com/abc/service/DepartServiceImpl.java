package com.abc.service;

import com.abc.bean.Depart;
import com.abc.repository.DepartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RefreshScope//刷新作用域： 在代码运行期，将对象的属性值更新
public class DepartServiceImpl implements DepartService {
    @Autowired
    private DepartRepository repository;

    //使用@Value注解注入
    @Value("${depart.name}")
    private String departName;

    @Override
    public boolean saveDepart(Depart depart) {
        Depart obj = repository.save(depart);
        if(obj != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean removeDepartById(int id) {
        if(repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean modifyDepart(Depart depart) {
        Depart obj = repository.save(depart);
        if(obj != null) {
            return true;
        }
        return false;
    }

    @Override
    public Depart getDepartById(int id) {
        if(repository.existsById(id)) {
            Depart depart = repository.getOne(id);
            depart.setName(departName);
            return depart;
        }
        Depart depart = new Depart();
        depart.setName("no this depart:" + departName);
        return depart;
    }

    @Override
    public List<Depart> listAllDeparts() {
        return repository.findAll();
    }
}
