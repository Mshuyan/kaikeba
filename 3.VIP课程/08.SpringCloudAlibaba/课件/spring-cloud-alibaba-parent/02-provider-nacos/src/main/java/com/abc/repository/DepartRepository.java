package com.abc.repository;

import com.abc.bean.Depart;
import org.springframework.data.jpa.repository.JpaRepository;
//持久层：在中国Mybatis非常流行灵活，但是Hibernate纯ORM
public interface DepartRepository extends JpaRepository<Depart, Integer> {
}
