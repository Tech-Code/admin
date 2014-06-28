package com.techapi.bus.controller;

import com.alibaba.fastjson.JSONObject;
import com.techapi.bus.entity.CityStation;
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
import java.util.ArrayList;
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
        Map<String, String> map;

        try {
            map = transStationService.addOrUpdate(transStation);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

	@RequestMapping("/list")
    @ResponseBody
	public Map<String, Object> list(int page,int rows) throws Exception {
        return transStationService.findSection(page, rows);
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

    @RequestMapping(value = "/suggestlist")
    public
    @ResponseBody
    Object suggest(@RequestParam("stationName") String stationName)
            throws Exception {
        JSONObject json = new JSONObject();
        List<CityStation> cityStationNameList;

        //String q = "cityStationName:"+ "北京";
        String q = "cityStationName:" + stationName;

        try {
            if(stationName.isEmpty()) {
                cityStationNameList = new ArrayList<>();
            } else {
                cityStationNameList = transStationService.suggetList(q);
            }

            json.put("result", cityStationNameList);

        } catch (Exception e) {
            e.printStackTrace();

            throw e;
        }
        return json;// 重定向
    }

}