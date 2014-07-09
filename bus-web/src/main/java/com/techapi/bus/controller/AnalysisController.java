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
	
	
	
	@RequestMapping("/namelist")
    @ResponseBody
	public Map<String, Object> namelist(int page, int rows,@RequestParam(value="name",required = false) String name,@RequestParam(value="startTime",required = false) String startTime,@RequestParam(value="endTime",required = false) String endTime) throws Exception {
		System.out.println("page:"+page+"rows:"+rows+"------name:"+name+"--------startTime:"+startTime+"-------endTime:"+endTime);
		return analysisService.findAnalysisTypeByTimeAndName(page,rows,name, startTime, endTime);
	}
	
	@RequestMapping("/typelist")
    @ResponseBody
	public Map<String, Object> typeList(int page, int rows,@RequestParam(value="type",required = false) String type,@RequestParam(value="name",required = false) String name,@RequestParam(value="startTime",required = false) String startTime,@RequestParam(value="endTime",required = false) String endTime) throws Exception {
		System.out.println("page:"+page+"rows:"+rows+"type:"+type+"------name:"+name+"--------startTime:"+startTime+"-------endTime:"+endTime);
		return analysisService.findAnalysisTypeByTimeAndType(page,rows,type,name, startTime, endTime);
	}
	
	@RequestMapping("/citylist")
    @ResponseBody
	public Map<String, Object> cityList(int page, int rows,@RequestParam(value="name",required = false) String name,@RequestParam(value="city",required = false) String city,@RequestParam(value="startTime",required = false) String startTime,@RequestParam(value="endTime",required = false) String endTime) throws Exception {
		System.out.println("page:"+page+"rows:"+rows+"city:"+city+"------name:"+name+"--------startTime:"+startTime+"-------endTime:"+endTime);
		return analysisService.findAnalysisCityByTimeAndName(page,rows,name,city, startTime, endTime);
	}
	
	@RequestMapping("/grouplist")
    @ResponseBody
	public Map<String, Object> grouplist(int page, int rows,@RequestParam(value="position",required = false) String position,@RequestParam(value="name",required = false) String name,@RequestParam(value="startTime",required = false) String startTime,@RequestParam(value="endTime",required = false) String endTime) throws Exception {
		System.out.println("page:"+page+"rows:"+rows+"positin:"+position+"------name:"+name+"--------startTime:"+startTime+"-------endTime:"+endTime);
		return analysisService.findAnalysisGroupByTimeAndType(page,rows,position==null?17:Integer.parseInt(position),name, startTime, endTime);
	}
	
	
	@RequestMapping("/loglist")
    @ResponseBody
	public Map<String, Object> loglist(int page, int rows,@RequestParam(value="keyword",required = false) String keyword,@RequestParam(value="name",required = false) String name,@RequestParam(value="startTime",required = false) String startTime,@RequestParam(value="endTime",required = false) String endTime) throws Exception {
		System.out.println("page:"+page+"rows:"+rows+"keyword:"+keyword+"------name:"+name+"--------startTime:"+startTime+"-------endTime:"+endTime);
		return analysisService.findLogByTimeAndType(page,rows,keyword,name, startTime, endTime);
	}
	
	
	/***
	 * 服务类型
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/servicename")
    @ResponseBody
	public List<SpringMap> servicename() throws Exception {
		return analysisService.findTypeAll();
	}
	/***
	 * 业务名称
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/typename")
    @ResponseBody
	public List<SpringMap> typename() throws Exception {
		return analysisService.findNameAll();
	}
	
	/***
	 * 时间分段类型
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/timetype")
    @ResponseBody
	public List<SpringMap> timetype() throws Exception {
		return analysisService.findTimeType();
	}
	/***
	 * 城市名称
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cityname")
    @ResponseBody
	public List<SpringMap> cityname() throws Exception {
		return analysisService.findCityAll();
	}
	
}
