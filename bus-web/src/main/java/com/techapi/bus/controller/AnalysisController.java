package com.techapi.bus.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.techapi.bus.service.AnalysisService;
import com.techapi.bus.vo.SpringMap;
@Controller
@RequestMapping("/analysis")
public class AnalysisController {
	@Resource
	public AnalysisService analysisService;
	
	@RequestMapping("/typelist")
    @ResponseBody
	public Map<String, Object> typeList(@RequestParam(value="type",required = false) String type,@RequestParam(value="startTime",required = false) String startTime,@RequestParam(value="endTime",required = false) String endTime) throws Exception {
		return analysisService.findAnalysisTypeByTimeAndType(type, startTime, endTime);
	}
	
	@RequestMapping("/type")
    @ResponseBody
	public List<SpringMap> type() throws Exception {
		return analysisService.findTypeAll();
	}
	
	@RequestMapping("/citylist")
    @ResponseBody
	public Map<String, Object> cityList(@RequestParam(value="city",required = false) String city,@RequestParam(value="startTime",required = false) String startTime,@RequestParam(value="endTime",required = false) String endTime) throws Exception {
		return analysisService.findAnalysisCityByTimeAndName(city, startTime, endTime);
	}
	
	@RequestMapping("/city")
    @ResponseBody
	public List<SpringMap> city() throws Exception {
		return analysisService.findCityAll();
	}
	
	@RequestMapping("/namelist")
    @ResponseBody
	public Map<String, Object> nameList(@RequestParam(value="name",required = false) String name,@RequestParam(value="startTime",required = false) String startTime,@RequestParam(value="endTime",required = false) String endTime) throws Exception {
		return analysisService.findAnalysisGroupByTimeAndType(name, startTime, endTime);
	}
	
	@RequestMapping("/name")
    @ResponseBody
	public List<SpringMap> name() throws Exception {
		return analysisService.findNameAll();
	}
}
