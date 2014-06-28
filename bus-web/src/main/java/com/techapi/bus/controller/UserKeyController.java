package com.techapi.bus.controller;

import com.techapi.bus.entity.UserKey;
import com.techapi.bus.service.UserKeyService;
import com.techapi.bus.util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/userkey")
public class UserKeyController {

    @Resource
    private UserKeyService userKeyService;

    @RequestMapping(value = "/add")
    @ResponseBody
    public Map<String, String> add(UserKey userKey, HttpServletRequest request)
            throws Exception {
        Map<String, String> map = new HashMap<>();

        try {
            userKeyService.addOrUpdate(userKey);
            map.put("mes", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("mes", "操作失败");
            throw e;
        }

        return map;
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> list(int page,int rows) throws Exception {
        return userKeyService.findSection(page,rows);
    }

    @RequestMapping("/update")
    public String update(Model model, String id) throws Exception {
        UserKey userKey = userKeyService.findById(id);
        model.addAttribute(Constants.KEY_INFO_SESSION, userKey);
        return "userkey/add";
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String, String> delete(@RequestParam("id") List<String> ids)
            throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        try {
            List<UserKey> poiList = userKeyService.findByIds(ids);
            userKeyService.deleteMany(poiList);
            map.put("mes", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("mes", "删除失败");
            throw e;
        }
        return map;// 重定向
    }
}