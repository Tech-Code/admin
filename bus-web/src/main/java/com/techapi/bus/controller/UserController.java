package com.techapi.bus.controller;

import com.techapi.bus.entity.User;
import com.techapi.bus.service.UserService;
import com.techapi.bus.util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    public static final String 用户名或者密码错误 = "用户名或者密码错误";
    @Resource
	private UserService userService;

    @RequestMapping(value = "/add")
    @ResponseBody
    public Map<String, String> add(User user) throws Exception {
        Map<String, String> map = new HashMap<>();
        try {
            map = userService.addOrUpdate(user);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("mes", "操作失败");
            throw e;
        }
        return map;
    }
	
	@RequestMapping("/login")
	public String login(User u, Model model) throws Exception {
		User user = userService.login(u.getUserName(), u.getPassword());
		if (user != null) {
			model.addAttribute(Constants.USER_INFO_SESSION, user);
			return "main";
		} else {
			model.addAttribute("message", 用户名或者密码错误);
			return "login";
		}
	}

    @RequestMapping(value = "/update")
    public String update(Model model, String id, String updateType) throws Exception {
        User user = userService.getUserById(id);
        model.addAttribute(Constants.USER_INFO_SESSION, user);
        model.addAttribute(Constants.UPDATE_USER_TYPE_SESSION, updateType);
        return "user/add";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(Model model) throws Exception {
        return userService.getAllUser();
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String, String> delete(@RequestParam("id") List<String> ids)
            throws Exception {
        Map<String, String> map = new HashMap<>();
        try {
            List<String> idsByRole = userService.findAllIdsByRole("0");

            if(ids.containsAll(idsByRole)) {
                map.put("mes", "至少要存在一个管理员用户");
                return map;
            }
            List<User> userList = userService.findByIds(ids);
            userService.deleteMany(userList);
            map.put("mes", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("mes", "删除失败");
            throw e;
        }
        return map;// 重定向
    }

}