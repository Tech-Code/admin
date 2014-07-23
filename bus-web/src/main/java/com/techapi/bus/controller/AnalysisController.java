package com.techapi.bus.controller;

import com.techapi.bus.service.AnalysisService;
import com.techapi.bus.vo.SpringMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/analysis")
public class AnalysisController {
	@Resource
	public AnalysisService analysisService;
	
	
	
	@RequestMapping("/namelist")
    @ResponseBody
	public Map<String, Object> namelist(HttpServletRequest request, HttpServletResponse response,int page, int rows,@RequestParam(value="name",required = false) String name,@RequestParam(value="startTime",required = false) String startTime,@RequestParam(value="endTime",required = false) String endTime) throws Exception {
		System.out.println("page:"+page+"rows:"+rows+"------name:"+name+"--------startTime:"+startTime+"-------endTime:"+endTime);
		return analysisService.findAnalysisTypeByTimeAndName(page,rows,name.trim(), startTime.trim(), endTime.trim());
	}
	
	@RequestMapping("/downloaddl")
	public void  downloadnamelist(HttpServletRequest request, HttpServletResponse response,@RequestParam(value="name",required = false) String name,@RequestParam(value="startTime",required = false) String startTime,@RequestParam(value="endTime",required = false) String endTime) throws Exception {
		analysisService.findAnalysisTypeByTimeAndNameToExcel(response,name.trim(), startTime.trim(), endTime.trim());
	}
	
	@RequestMapping("/typelist")
    @ResponseBody
	public Map<String, Object> typeList(int page, int rows,@RequestParam(value="type",required = false) String type,@RequestParam(value="name",required = false) String name,@RequestParam(value="startTime",required = false) String startTime,@RequestParam(value="endTime",required = false) String endTime) throws Exception {
		System.out.println("page:"+page+"rows:"+rows+"type:"+name+"------name:"+type+"--------startTime:"+startTime+"-------endTime:"+endTime);
		return analysisService.findAnalysisTypeByTimeAndType(page,rows,name.trim(),type.trim(), startTime.trim(), endTime.trim());
	}
	
	@RequestMapping("/downloadtl")
	public void downloadtypeList(HttpServletRequest request, HttpServletResponse response,@RequestParam(value="type",required = false) String type,@RequestParam(value="name",required = false) String name,@RequestParam(value="startTime",required = false) String startTime,@RequestParam(value="endTime",required = false) String endTime) throws Exception {
		 analysisService.findAnalysisTypeByTimeAndTypeToExcel(response,name.trim(),type.trim(), startTime.trim(), endTime.trim());
	}
	
	@RequestMapping("/citylist")
    @ResponseBody
	public Map<String, Object> cityList(int page, int rows,@RequestParam(value="name",required = false) String name,@RequestParam(value="city",required = false) String city,@RequestParam(value="startTime",required = false) String startTime,@RequestParam(value="endTime",required = false) String endTime) throws Exception {
		System.out.println("page:"+page+"rows:"+rows+"city:"+city+"------name:"+name+"--------startTime:"+startTime+"-------endTime:"+endTime);
		return analysisService.findAnalysisCityByTimeAndName(page,rows,name.trim(),city.trim(), startTime.trim(), endTime.trim());
	}
	
	@RequestMapping("/downloadcl")
	public void downloadcityList(HttpServletRequest request, HttpServletResponse response,@RequestParam(value="name",required = false) String name,@RequestParam(value="city",required = false) String city,@RequestParam(value="startTime",required = false) String startTime,@RequestParam(value="endTime",required = false) String endTime) throws Exception {
		 analysisService.findAnalysisCityByTimeAndNameToExcel(response,name.trim(),city.trim(), startTime.trim(), endTime.trim());
	}
	
	@RequestMapping("/grouplist")
    @ResponseBody
	public Map<String, Object> grouplist(int page, int rows,@RequestParam(value="position",required = false) String position,@RequestParam(value="name",required = false) String name,@RequestParam(value="startTime",required = false) String startTime,@RequestParam(value="endTime",required = false) String endTime) throws Exception {
		System.out.println("page:"+page+"rows:"+rows+"positin:"+position+"------name:"+name+"--------startTime:"+startTime+"-------endTime:"+endTime);
		if(startTime.length()==16){
			startTime+=":00";
		}else if(startTime.length()==13){
			startTime+=":00:00";
		}else if(startTime.length()==10){
			startTime+=" 00:00:00";
		}else if(startTime.length()==7){
			startTime+="-01 00:00:00";
		}
		
		if(endTime.length()==16){
			endTime+=":59";
		}else if(endTime.length()==13){
			endTime+=":59:59";
		}else if(endTime.length()==10){
			endTime+=" 23:59:59";
		}else if(endTime.length()==7){
			endTime+="-31 23:59:59";
		}
		System.out.println("group analysis: startTime"+startTime+" endTime: "+endTime);
		return analysisService.findAnalysisGroupByTimeAndType(page,rows,position==null?17:Integer.parseInt(position),name.trim(), startTime.trim(), endTime.trim());
	}
	
	@RequestMapping("/downloadgl")
	public void downloadgrouplist(HttpServletRequest request, HttpServletResponse response,@RequestParam(value="position",required = false) String position,@RequestParam(value="name",required = false) String name,@RequestParam(value="startTime",required = false) String startTime,@RequestParam(value="endTime",required = false) String endTime) throws Exception {
		if(startTime.length()==16){
			startTime+=":00";
		}else if(startTime.length()==13){
			startTime+=":00:00";
		}else if(startTime.length()==10){
			startTime+=" 00:00:00";
		}else if(startTime.length()==7){
			startTime+="-01 00:00:00";
		}
		
		if(endTime.length()==16){
			endTime+=":59";
		}else if(endTime.length()==13){
			endTime+=":59:59";
		}else if(endTime.length()==10){
			endTime+=" 23:59:59";
		}else if(endTime.length()==7){
			endTime+="-31 23:59:59";
		}
		System.out.println("group time + startTime:"+startTime+" endTime:"+endTime);
	   analysisService.findAnalysisGroupByTimeAndTypeToExcel(response,position==null?17:Integer.parseInt(position),name, startTime, endTime);
	}
	
	
	@RequestMapping("/loglist")
    @ResponseBody
	public Map<String, Object> loglist(int page, int rows,@RequestParam(value="keyword",required = false) String keyword,@RequestParam(value="name",required = false) String name,@RequestParam(value="startTime",required = false) String startTime,@RequestParam(value="endTime",required = false) String endTime) throws Exception {
		System.out.println("page:"+page+"rows:"+rows+"keyword:"+keyword+"------name:"+name+"--------startTime:"+startTime+"-------endTime:"+endTime+" 23:59:59");
		return analysisService.findLogByTimeAndType(page,rows,keyword,name, startTime+" 00:00:00", endTime+" 23:59:59");
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
	public List<SpringMap> cityname(int notAll) throws Exception {
		return analysisService.findCityAll(notAll);
	}
	
}
