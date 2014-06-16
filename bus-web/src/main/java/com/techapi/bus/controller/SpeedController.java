package com.techapi.bus.controller;

import com.techapi.bus.entity.Speed;
import com.techapi.bus.service.SpeedService;
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
@RequestMapping("/speed")
public class SpeedController {

	@Resource
	private SpeedService speedService;

    @RequestMapping(value = "/add")
    @ResponseBody
    public Map<String, String> add(Speed speed, HttpServletRequest request)
            throws Exception {
        Map<String, String> map = new HashMap<>();

        try {
            speedService.addOrUpdate(speed);
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
	public Map<String, Object> list() throws Exception {
        return speedService.findAll();
	}

    @RequestMapping("/update")
    public String update(Model model, String id) throws Exception {
        Speed speed = speedService.findById(id);
        model.addAttribute(Constants.SPEED_INFO_SESSION, speed);
        return "speed/add";
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String, String> delete(@RequestParam("id") List<String> ids)
            throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        try {
            List<Speed> speedList = speedService.findByIds(ids);
            speedService.deleteMany(speedList);
            map.put("mes", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("mes", "删除失败");
            throw e;
        }
        return map;// 重定向
    }

}