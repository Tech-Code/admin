package com.techapi.bus.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techapi.bus.entity.User;
import com.techapi.bus.service.UserService;
import com.techapi.bus.util.Constants;

@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;
	
	@RequestMapping("/login")
	public String login(User u, Model model) throws Exception {
		User user = userService.login(u.getUserName(), u.getPassword());
		if (user != null) {
			model.addAttribute(Constants.USER_INFO_SESSION, user);
			return "main";
		} else {
			model.addAttribute("message", "用户名或者密码错误");
			return "login";
		}
	}
}