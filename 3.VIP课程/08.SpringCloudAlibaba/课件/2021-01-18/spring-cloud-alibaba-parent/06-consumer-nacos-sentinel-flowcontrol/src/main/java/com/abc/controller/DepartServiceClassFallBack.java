package com.abc.controller;

import com.abc.bean.Depart;

import java.util.ArrayList;
import java.util.List;

//服务降级处理类
public class DepartServiceClassFallBack {

    public static Depart getFallback(int id, Throwable e){
        System.out.println("getHandle()执行异常 " + id);
//        Depart depart = new Depart();
//        depart.setId(id);
//        depart.setName("degrade-class-" + id + "-" + e.getMessage());
        //-----
        Depart depart2 = Depart.builder()
                .id(id)
                .name("degrade-class-" + id + "-" + e.getMessage())
                .build();
        return depart2;
    }

    public static List<Depart> listFallback(){
        System.out.println("listHandle()执行异常 ");
        List<Depart> list = new ArrayList<>();
        list.add(Depart.builder()
                .name("no any depart")
                .build());
        return list;
    }
}
