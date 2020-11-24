package com.kkb.spring.dao;

import java.util.List;
import java.util.Map;

import com.kkb.spring.po.User;

public interface UserDao {

	List<User> queryUserList(Map<String, Object> param);
}
