package com.techapi.bus.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.techapi.bus.dao.AnalysisCityDao;
import com.techapi.bus.dao.AnalysisGroupDao;
import com.techapi.bus.dao.AnalysisTypeDao;
import com.techapi.bus.entity.AnalysisCity;
import com.techapi.bus.entity.AnalysisGroup;
import com.techapi.bus.entity.AnalysisType;
import com.techapi.bus.util.PageUtils;
import com.techapi.bus.vo.SpringMap;

/***
 * 日志分析的服务
 * 
 * @author jiayusun
 */
@Service
public class AnalysisService {
	@Resource
	private AnalysisCityDao analysisCityDao;
	@Resource
	private AnalysisTypeDao analysisTypeDao;
	@Resource
	private AnalysisGroupDao analysisGroupDao;

	/***
	 * 按指定时间段及业务账号统计业务量，即统计每个业务的公交换乘服务、公交查询（站点、线路）服务的调用量
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Map<String, Object> findAnalysisTypeByTimeAndType(String type,String startTime,String endTime) {
		List<AnalysisType> analysisType=null;
		if(StringUtils.isBlank(type)){
			analysisType = (List<AnalysisType>) analysisTypeDao.findAll();
		}else if("all".equals(type)){
			analysisType = (List<AnalysisType>) analysisTypeDao.findByTime(startTime, endTime);
		}else{
			analysisType = (List<AnalysisType>) analysisTypeDao.findByTimeAndType(type, startTime, endTime);
		}
		
		return PageUtils.getPageMap(analysisType);
	}
	
	/**
	 * 服务类型
	 */
	public List<SpringMap> findTypeAll(){
		List<String> stype = analysisTypeDao.findTypeAll();
		List<SpringMap> lm = new ArrayList<SpringMap>();
		for(String s:stype){
			SpringMap sm = new SpringMap();
			sm.setId(s);
			sm.setText(s);
			lm.add(sm);
		}
		SpringMap sm = new SpringMap();
		sm.setId("all");
		sm.setText("all");
		lm.add(sm);
		return lm;
	}
	
	/***
	 * 按指定时间段及分城市统计业务量，即统计每个城市公交查询调用量
	 * @param name
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Map<String, Object> findAnalysisCityByTimeAndName(String name,String startTime,String endTime) {
		System.out.println("name:"+name+",startTime:"+startTime+",endTime:"+endTime);
		List<AnalysisCity> analysisCity=null;
		if(StringUtils.isBlank(name)){
			analysisCity =  (List<AnalysisCity>) analysisCityDao.findAll();
		}else if("all".equals(name)){
			analysisCity = analysisCityDao.findByTime(startTime, endTime);
		}else{
			analysisCity = analysisCityDao.findByTimeAndType(name, startTime, endTime);
		}
		return PageUtils.getPageMap(analysisCity);
	}
	 /***
	  * 城市名称
	  */
	public List<SpringMap> findCityAll(){
		List<String> stype = analysisCityDao.findTypeAll();
		List<SpringMap> lm = new ArrayList<SpringMap>();
		for(String s:stype){
			SpringMap sm = new SpringMap();
			sm.setId(s);
			sm.setText(s);
			lm.add(sm);
		}
		SpringMap sm = new SpringMap();
		sm.setId("all");
		sm.setText("all");
		lm.add(sm);
		return lm;
	}
	/**
	 * 按业务账号分时段（每天、每小时、每分钟）统计业务量（GroupBy），即统计业务账号指定时间段内每天（小时或分钟）的调用量。
	 * @param name
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Map<String, Object> findAnalysisGroupByTimeAndType(String name,String startTime,String endTime) {
		List<AnalysisGroup> analysisGroup=null;
		if(StringUtils.isBlank(name)){
			analysisGroup =   (List<AnalysisGroup>) analysisGroupDao.findAll();
		}else if("all".equals(name)){
			analysisGroup = analysisGroupDao.findByTime(startTime, endTime);
		}else{
			analysisGroup = analysisGroupDao.findByTimeAndName(name, startTime, endTime);
		}
		return PageUtils.getPageMap(analysisGroup);
	}

	/***
	 * 查询业务账号
	 */
	public List<SpringMap> findNameAll(){
		List<String> stype = analysisGroupDao.findNameAll();
		List<SpringMap> lm = new ArrayList<SpringMap>();
		for(String s:stype){
			SpringMap sm = new SpringMap();
			sm.setId(s);
			sm.setText(s);
			lm.add(sm);
		}
		SpringMap sm = new SpringMap();
		sm.setId("all");
		sm.setText("all");
		lm.add(sm);
		return lm;
	}

}
