package com.techapi.bus.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.techapi.bus.entity.User;

/**
 * Created by XF on 4/16/14.
 */
public interface UserDao extends PagingAndSortingRepository<User, String> {
	
	@Query("select u from User u "
			+ "where u.userName = :userName and u.password = :password")
	User login (
			@Param("userName") String userName,
			@Param("password") String password);



}
