package com.abc.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity//声明当前类为实体类
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "fieldHandler"})//作用是json序列化时将java bean中的一些属性忽略掉，序列化和反序列化都受影响。
public class Depart {
    @Id//声明当前属性为主键id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//配置主键生成策略：使用数据库主键自增策略
    private Integer id;
    private String name;
}


