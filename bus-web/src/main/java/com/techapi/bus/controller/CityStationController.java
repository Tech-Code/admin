package com.techapi.bus.controller;

import com.alibaba.fastjson.JSONObject;
import com.techapi.bus.entity.CityStation;
import com.techapi.bus.entity.PoiStation;
import com.techapi.bus.entity.Transstation;
import com.techapi.bus.service.CityStationService;
import com.techapi.bus.service.TransStationService;
import com.techapi.bus.solr.BusSearch;
import com.techapi.bus.solr.query.QueryForBus;
import com.techapi.bus.solr.result.PoiStationResult;
import com.techapi.bus.util.Constants;
import com.techapi.bus.vo.SpringMap;
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
@RequestMapping("/citystation")
public class CityStationController{

	@Resource
	private CityStationService cityStationService;

    @Resource
    private TransStationService transStationService;

    @RequestMapping(value = "/add")
    @ResponseBody
    public Map<String, String> add(CityStation cityStation, HttpServletRequest request)
            throws Exception {
        Map<String, String> map;

        try {
            map = cityStationService.addOrUpdate(cityStation);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

	@RequestMapping("/list")
    @ResponseBody
	public Map<String, Object> list(int page,int rows) throws Exception {
        return cityStationService.findSection(page, rows);
	}

    @RequestMapping("/update")
    public String update(Model model, String id) throws Exception {
        CityStation cityStation = cityStationService.findById(id);
        model.addAttribute(Constants.CS_INFO_SESSION, cityStation);
        return "citystation/add";
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String, String> delete(@RequestParam("id") List<String> ids)
            throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        try {
            List<CityStation> cityStationList = cityStationService.findByIds(ids);

            List<Transstation> transstationList =transStationService.findTransstationByCityStationIds(ids);

            if(transstationList.size() > 0) {
                map.put("mes", "包含城际站点依赖ID，不能删除!");
            } else {
                cityStationService.deleteMany(cityStationList);
                map.put("mes", "删除成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("mes", "删除失败");
            throw e;
        }
        return map;// 重定向
    }

    @RequestMapping(value = "/suggestlist")
    @ResponseBody
    public Object suggest(@RequestParam(value = "cityStationName", required = false) String stationName,
                          @RequestParam(value = "poiType") String poiType,
                          @RequestParam(value = "adName", required = false) String adName)
            throws Exception {
        JSONObject json = new JSONObject();

        QueryForBus queryForBus = new QueryForBus();
        queryForBus.setPoiType(poiType);
        queryForBus.setAdName(adName);
        queryForBus.setPoiStationName(stationName);
        queryForBus.setRows(Constants.SUGGEST_LIST_ROWS);

        BusSearch busSearch = new BusSearch(queryForBus);
        PoiStationResult poiStationResult = busSearch.searchPoiStationList();


        try {
            List<PoiStation> poiStationList = poiStationResult.getList();
            json.put("result", poiStationList);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return json;// 重定向
    }

    @RequestMapping(value = "/transtypes")
    @ResponseBody
    public List<SpringMap> getTransTypes()
            throws Exception {
        return cityStationService.findAllTransTypes();
    }

    @RequestMapping(value = "/searchlist")
    @ResponseBody
    public Map<String, Object> searchList(int page, int rows,
                                          @RequestParam(value = "cityCode", required = false) String cityCode,
                                          @RequestParam(value = "cityName", required = false) String cityName,
                                          @RequestParam(value = "selectTransType", required = false) String selectTransType,
                                          @RequestParam(value = "stationName", required = false) String stationName) throws Exception {
        System.out.println("page: " + page + "rows: " + rows + "cityCode: " + cityCode + "------cityName: " + cityName + "--------selectTransType:" + selectTransType + "-------stationName:" + stationName);
        return cityStationService.findBySearchBySection(page, rows, cityCode.trim(), cityName.trim(), selectTransType.trim(), stationName.trim());
    }
}