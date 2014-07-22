package com.techapi.bus.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techapi.bus.dao.UserDao;
import com.techapi.bus.entity.User;

/**
 * @Description:
 * @author Andrew.Xue
 * @date 2014-5-31
 */
@Service
public class UserService {

	@Resource
	private UserDao userDao;

	/**
	 * @param query
	 * @return
	 */
	public User login(String userName,String password) {
		User user = userDao.login(userName, password);
		return user;
		
	}

}