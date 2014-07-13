package com.techapi.bus.controller;

import com.techapi.bus.entity.Taxi;
import com.techapi.bus.service.TaxiService;
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
@RequestMapping("/taxi")
public class TaxiController {

	@Resource
	private TaxiService taxiService;

    @RequestMapping(value = "/add")
    @ResponseBody
    public Map<String, String> add(Taxi taxi, HttpServletRequest request)
            throws Exception {
        Map<String, String> map;

        try {
            map = taxiService.addOrUpdate(taxi);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return map;
    }

	@RequestMapping("/list")
    @ResponseBody
	public Map<String, Object> list(int page, int rows) throws Exception {
        return taxiService.findSection(page, rows);
	}

    @RequestMapping("/update")
    public String update(Model model, String id) throws Exception {
        Taxi taxi = taxiService.findById(id);
        model.addAttribute(Constants.TAXI_INFO_SESSION, taxi);
        return "taxi/add";
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String, String> delete(@RequestParam("id") List<String> ids)
            throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        try {
            List<Taxi> taxiList = taxiService.findByIds(ids);
            taxiService.deleteMany(taxiList);
            map.put("mes", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("mes", "删除失败");
            throw e;
        }
        return map;// 重定向
    }

    @RequestMapping(value = "/searchlist")
    @ResponseBody
    public Map<String, Object> searchList(int page, int rows,
                                          @RequestParam(value = "cityCode", required = false) String cityCode,
                                          @RequestParam(value = "cityName", required = false) String cityName) throws Exception {
        System.out.println("page: " + page + "rows: " + rows + "cityCode: " + cityCode + "------cityName: " + cityName);
        return taxiService.findBySearchBySection(page, rows, cityCode, cityName);
    }

}