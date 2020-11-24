package com.kkb.spring.service;

import java.util.List;
import java.util.Map;

import com.kkb.spring.dao.UserDao;
import com.kkb.spring.po.User;

public class UserServiceImpl implements UserService {

	// 依赖注入UserDao
	private UserDao userDao;

	// setter方法注入UserDao
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public List<User> queryUsers(Map<String, Object> param) {
		return userDao.queryUserList(param);
	}

}
