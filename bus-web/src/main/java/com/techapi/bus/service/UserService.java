package com.techapi.bus.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techapi.bus.dao.UserDao;
import com.techapi.bus.entity.User;

/**
 * @Description:
 * @author hongjia.hu
 * @date 2014-3-8
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
		// TODO Auto-generated method stub
		User user = userDao.login(userName, password);
		return user;
		
	}

}