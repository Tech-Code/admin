package com.techapi.bus.controller;

import com.techapi.bus.entity.Transstation;
import com.techapi.bus.service.TransStationService;
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
@RequestMapping("/transstation")
public class TransStationController {

	@Resource
	private TransStationService transStationService;

    @RequestMapping(value = "/add")
    @ResponseBody
    public Map<String, String> add(Transstation transStation, HttpServletRequest request)
            throws Exception {
        Map<String, String> map = new HashMap<>();

        try {
            transStationService.addOrUpdate(transStation);
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
        return transStationService.findAll();
	}

    @RequestMapping("/update")
    public String update(Model model, String id) throws Exception {
        Transstation transStation = transStationService.findById(id);
        model.addAttribute(Constants.TS_INFO_SESSION, transStation);
        return "transstation/add";
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String, String> delete(@RequestParam("id") List<String> ids)
            throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        try {
            List<Transstation> transstationList = transStationService.findByIds(ids);
            transStationService.deleteMany(transstationList);
            map.put("mes", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("mes", "删除失败");
            throw e;
        }
        return map;// 重定向
    }

}