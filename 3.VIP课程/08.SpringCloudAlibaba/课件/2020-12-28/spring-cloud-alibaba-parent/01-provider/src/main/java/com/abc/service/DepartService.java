package com.abc.service;

import com.abc.bean.Depart;

import java.util.List;
//业务层接口
public interface DepartService {
    boolean saveDepart(Depart depart);
    boolean removeDepartById(int id);
    boolean modifyDepart(Depart depart);
    Depart getDepartById(int id);
    List<Depart> listAllDeparts();
}
