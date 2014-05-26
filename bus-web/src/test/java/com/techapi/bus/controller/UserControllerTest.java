package com.techapi.bus.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.techapi.bus.dao.BusLinesDao;
import com.techapi.bus.dao.UserDao;
import com.techapi.bus.model.User;
import com.techapi.bus.model.protocol.BusStation;
import com.techapi.bus.model.protocol.LineEntity;
import com.techapi.bus.service.UserService;
import com.techapi.bus.support.TestSupport;
import com.techapi.bus.util.ChineseUtils;

/**
 * @Description:
 * @author hongjia.hu
 * @date 2014-2-16
 */
public class UserControllerTest extends TestSupport {

	private UserDao userDao = (UserDao) context.getBean(UserDao.class);

	private UserService userService = (UserService) context
			.getBean(UserService.class);

	@Resource
	private BusLinesDao busLineDao = (BusLinesDao) context
			.getBean(BusLinesDao.class);

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public void testSaveUser() {

		User user = new User();
		user.setLoginname("huoln");
		// user.setUsername("霍立娜");
		// user.setPassword("123456");
		user.setEmail("huolina@163.com");
		user.setTime(sdf.format(new Date()));

		userDao.insert(user);
		System.out.println("ok");
	}

	@Test
	public void testFindBuslines() {
		List<LineEntity> list = busLineDao.findAll();
		for (LineEntity entity : list) {
			List<BusStation> stations = entity.getStations();
		}
	}

	public void testRemoveUser() {
		List<String> list = new ArrayList<String>();
		list.add("53006f35e3716ee8db4a75dd");
		userService.deleteUsers(list);
	}

}
