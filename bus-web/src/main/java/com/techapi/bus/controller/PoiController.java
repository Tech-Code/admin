package com.techapi.bus.controller;

import com.techapi.bus.entity.Poi;
import com.techapi.bus.service.PoiService;
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
import java.util.Set;

@Controller
@RequestMapping("/poi")
public class PoiController {

	@Resource
	private PoiService poiService;

    @RequestMapping(value = "/add")
    @ResponseBody
    public Map<String, String> add(Poi poi, HttpServletRequest request)
            throws Exception {
        Map<String, String> map = new HashMap<>();

        try {
            poiService.addOrUpdate(poi);
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
    public Map<String, Object> list(int page, int rows) throws Exception {
        return poiService.findSection(page, rows);
    }

    @RequestMapping("/update")
    public String update(Model model, String id) throws Exception {
        Poi poi = poiService.findById(id);
        model.addAttribute(Constants.POI_INFO_SESSION, poi);
        return "poi/add";
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String, String> delete(@RequestParam("id") List<String> ids)
            throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        try {
            List<Poi> poiList = poiService.findByIds(ids);
            poiService.deleteMany(poiList);
            map.put("mes", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("mes", "删除失败");
            throw e;
        }
        return map;// 重定向
    }

    @RequestMapping(value = "/getPoiType1")
    @ResponseBody
    public Map<String, Set> getPoiType1()
            throws Exception {
        Map<String, Set> map = new HashMap<>();
        Map<String, Map<String, List<String>>> poiTypeMap = poiService.getPoiTypeData();

        map.put("poiType1List", poiTypeMap.keySet());
        return map;
    }

    @RequestMapping(value = "/getPoiType2")
    @ResponseBody
    public Map<String, Set> getPoiType2(String poiType1)
            throws Exception {
        Map<String, Set> map = new HashMap<>();
        Map<String, Map<String, List<String>>> poiTypeMap = poiService.getPoiTypeData();

        Map<String, List<String>> poiType2Map = poiTypeMap.get(poiType1);
        map.put("poiType2List", poiType2Map.keySet());
        return map;
    }

    @RequestMapping(value = "/getPoiType3")
    @ResponseBody
    public Map<String, List<String>> getPoiType3(String poiType1,String poiType2)
            throws Exception {
        Map<String, List<String>> map = new HashMap<>();
        Map<String, Map<String, List<String>>> poiTypeMap = poiService.getPoiTypeData();

        Map<String, List<String>> poiType2Map = poiTypeMap.get(poiType1);
        List<String> poiType3List = poiType2Map.get(poiType2);
        map.put("poiType3List", poiType3List);
        return map;
    }

}