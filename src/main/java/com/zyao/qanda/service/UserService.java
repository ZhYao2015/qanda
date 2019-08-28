package com.zyao.qanda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyao.qanda.dao.UserDao;
import com.zyao.qanda.model.User;

@Service
public class UserService {
	
	@Autowired
	UserDao userDao;
	
	public User getUser(int id) {
		return userDao.selectById(id);
	}
}
