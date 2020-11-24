package com.kkb.spring.service;

import java.util.List;
import java.util.Map;

import com.kkb.spring.po.User;

public interface UserService {
	List<User> queryUsers(Map<String, Object> param);
}
