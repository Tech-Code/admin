package com.techapi.bus.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.techapi.bus.dao.AnalysisCityDao;
import com.techapi.bus.dao.AnalysisGroupDao;
import com.techapi.bus.dao.AnalysisManageDao;
import com.techapi.bus.dao.AnalysisTypeDao;
import com.techapi.bus.dao.AreaDao;
import com.techapi.bus.dao.ServiceDictDao;
import com.techapi.bus.dao.UserKeyDao;
import com.techapi.bus.entity.AnalysisCity;
import com.techapi.bus.entity.AnalysisManage;
import com.techapi.bus.entity.AnalysisType;
import com.techapi.bus.entity.Area;
import com.techapi.bus.entity.ServiceDict;
import com.techapi.bus.entity.UserKey;
import com.techapi.bus.util.DataUtils;
import com.techapi.bus.util.ExportExcel;
import com.techapi.bus.util.PageUtils;
import com.techapi.bus.vo.AnalysisExcelVO;
import com.techapi.bus.vo.CityListVO;
import com.techapi.bus.vo.GroupListVO;
import com.techapi.bus.vo.SpringMap;

/***
 * 日志分析的服务
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
	@Resource
	public UserKeyDao userKeyDao;
	@Resource
	public ServiceDictDao serviceDictDao;
	@Resource
	public AreaDao areaDao;
	@Resource
	public AnalysisManageDao analysisManageDao;
	

	public Map<String, Object> findAnalysisTypeByTimeAndName(int page,int rows,String name,String startTime,String endTime) {
		Pageable pager = new PageRequest(page-1, rows);
		List<AnalysisType> analysisType=findAnalysisTypeToList(name,startTime,endTime);
		return PageUtils.getPageMap(analysisType,pager);
	}
	
	private List<AnalysisType> findAnalysisTypeToList(String name,String startTime,String endTime){
		List<AnalysisType> analysisType= new ArrayList<AnalysisType>();
		List<Object[]> ato=null;
	    if("all".equals(name)){
			ato =  analysisTypeDao.findByTimeForName(startTime, endTime);
		}else{
			ato =  analysisTypeDao.findByTimeAndNameForName(name,startTime, endTime);
		}
		/**
		 *替换字典数据 
		 */
		Map<String,UserKey> suMap = getKeyMap();
		for(Object[] o:ato){
			AnalysisType at = new AnalysisType();
			String nameo = o[0].toString();
			if(suMap.get(nameo)!=null){
				at.setName(suMap.get(nameo).getBusinessName());
			}else{
				at.setName("未知业务");
			}
			at.setKeyName(nameo);
			at.setTotal(o[1].toString());
			analysisType.add(at);
		}
		return analysisType;
	}
	/***
	 * 业务统计导出到excel
	 */
	public String findAnalysisTypeByTimeAndNameToExcel(HttpServletResponse response,String name,String startTime,String endTime) {
		List<AnalysisType> analysisType= findAnalysisTypeToList(name, startTime, endTime);
		List<AnalysisExcelVO> aeVO = new ArrayList<AnalysisExcelVO>();
		for(AnalysisType at:analysisType){
			AnalysisExcelVO ae =new AnalysisExcelVO();
			ae.setName(at.getName());
			ae.setKeyName(at.getKeyName());
			ae.setTotal(at.getTotal());
			aeVO.add(ae);
		}
		String[] title={"业务名称","业务标识","业务总量"};
		String result = ExportExcel.exportExcel(response, "analysistype.xls", title, aeVO);
		return result;
	}
	
	/***
	 * 按指定时间段及业务账号统计业务量，即统计每个业务的公交换乘服务、公交查询（站点、线路）服务的调用量
	 * type:服务，name:业务
	 */
	public Map<String, Object> findAnalysisTypeByTimeAndType(int page,int rows,String type,String name,String startTime,String endTime) {
		Pageable pager = new PageRequest(page-1, rows);
		 List<AnalysisType> analysisType = loadByTimeAndTypeToList(type,name,startTime,endTime);
		return PageUtils.getPageMap(analysisType,pager);
	}
	
	private List<AnalysisType> loadByTimeAndTypeToList(String type,String name,String startTime,String endTime){
		List<AnalysisType> analysisType= new ArrayList<AnalysisType>();
		List<Object[]> ato=null;
		if("all".equals(type)&&"all".equals(name)){
			ato =  analysisTypeDao.findByTime(startTime, endTime);
		}else if("all".equals(type)){
			ato = analysisTypeDao.findByTimeAndName(name,startTime, endTime);
		}else if("all".equals(name)){
			ato =  analysisTypeDao.findByTimeAndType(type, startTime, endTime);
		}else{
			ato =  analysisTypeDao.findByTimeAndNameAndType(type, name,startTime, endTime);
		}
		/**
		 *替换字典数据 
		 */
		Map<String,UserKey> suMap = getKeyMap();
		Map<String,ServiceDict> sdMap = getServiceDictMap();
		for(Object[] o:ato){
			AnalysisType at = new AnalysisType();
			String nameo = o[0].toString();
			if(suMap.get(nameo)!=null){
				at.setName(suMap.get(nameo).getBusinessName());
			}else{
				at.setName("未知业务");
			}
			at.setKeyName(nameo);
			String typeo = o[1].toString();
			if(sdMap.get(typeo)!=null){
				at.setType(sdMap.get(typeo).getServiceName());
			}else{
				at.setType("未知服务");
			}
			at.setTotal(o[2].toString());
			analysisType.add(at);
		}
		return analysisType;
	}
	/**
	 * 服务统计导出到excel
	 */
	public String findAnalysisTypeByTimeAndTypeToExcel(HttpServletResponse response,String type,String name,String startTime,String endTime) {
		 List<AnalysisType> analysisType = loadByTimeAndTypeToList(type,name,startTime,endTime);
		 String[] title={"业务名称","业务标识","服务类型","业务总量"};
		 String result = ExportExcel.exportExcel(response, "analysisserver.xls", title, analysisType);
		 return result;
	}
	
	
	/***
	 * 按指定时间段及分城市统计业务量，即统计每个城市公交查询调用量
	 */
	public Map<String, Object> findAnalysisCityByTimeAndName(int page,int rows,String name,String cityName,String startTime,String endTime) {
		System.out.println("name:"+name+"---------:cityName"+cityName+"-----,startTime:"+startTime+"----,endTime:"+endTime);
		Pageable pager = new PageRequest(page-1, rows);
		List<CityListVO> ctVOList=findAnalysisCityByTimeAndNameToList(name, cityName, startTime, endTime);
		return PageUtils.getPageMap(ctVOList,pager);
	}
	
	private List<CityListVO> findAnalysisCityByTimeAndNameToList(String name,String cityName,String startTime,String endTime){
		List<Object[]> oTimeAndType=null;
		if("all".equals(name)){
			oTimeAndType =analysisCityDao.findByTime(startTime, endTime);
		}else{
			oTimeAndType =analysisCityDao.findByTimeAndType(name, startTime, endTime);
		}
		
		/***
		 * 标准城市名称，List<城市实体>
		 */
		Map<String,List<AnalysisCity>> acListMap = new HashMap<String,List<AnalysisCity>>();
		for(Object[] o:oTimeAndType){
			AnalysisCity analysisCity = new AnalysisCity();
			String cityname = o[0]==null?"unknown":o[0].toString();
			String oname = o[1]==null?"unknown":o[1].toString();
			String total = o[2]==null?"0":o[2].toString();
			String servername = o[3]==null?"unknown":o[3].toString();
			analysisCity.setCityName(cityname);
			analysisCity.setType(oname);
			analysisCity.setTotal(total);
			analysisCity.setName(servername);
			if(StringUtils.isNotBlank(cityname)){
				List<Area> areaList = null;
				if(cityname.matches("[0-9]+")){
					int citycode = Integer.parseInt(cityname);
					areaList =areaDao.findByCode(citycode);
				}else{
					areaList = areaDao.findByName(cityname+"%");
				}
				if(areaList!=null&&areaList.size()>0){
					Area area =	areaList.get(0);
				    if(acListMap.containsKey(area.getAreaName())){
				    	acListMap.get(area.getAreaName()).add(analysisCity);
				    }else{
				    	List<AnalysisCity> ac = new ArrayList<AnalysisCity>();
				    	ac.add(analysisCity);
				    	acListMap.put(area.getAreaName(), ac);
				    }
				}else{
					if(acListMap.containsKey("未知城市")){
				    	acListMap.get("未知城市").add(analysisCity);
				    }else{
				    	List<AnalysisCity> ac = new ArrayList<AnalysisCity>();
				    	ac.add(analysisCity);
				    	acListMap.put("未知城市", ac);
				    }
				}
			}
		}
		/***
		 * 根据城市查询
		 */
		Map<String,List<AnalysisCity>> acListMapTemp = new HashMap<String,List<AnalysisCity>>();
		if("all".equals(cityName)){
			acListMapTemp=acListMap;
		}else{
			acListMapTemp.put(cityName, acListMap.get(cityName));
		}
		/***
		 * Map<业务+城市，返回实体>
		 */
		Map<String,CityListVO> scmap = new HashMap<String,CityListVO>();
		Map<String,UserKey> suKeymap = getKeyMap();
		for(Entry<String, List<AnalysisCity>> ml:acListMapTemp.entrySet()){
			List<AnalysisCity> acList = ml.getValue();
			for(AnalysisCity ac:acList){
				String servName=ac.getName();
				String oName=ac.getType();
				int total=0;
				if(StringUtils.isNotBlank(ac.getTotal())){
					total=Integer.parseInt(ac.getTotal());
				}
				if(scmap.containsKey(oName+ml.getKey())){
					CityListVO cv =scmap.get(oName+ml.getKey());
					cv.putTotal(servName, total);
				}else{
					CityListVO cv= new CityListVO();
					cv.setCity(ml.getKey());
					if(suKeymap.containsKey(oName)){
						cv.setTypeName(suKeymap.get(oName).getBusinessName());
					}else{
						cv.setTypeName("未知业务");
					}
					cv.setKeyName(oName);
					cv.putTotal(servName, total);
					scmap.put(oName+ml.getKey(), cv);
				}
			}
		}
		List<CityListVO> ctVOList = new ArrayList<CityListVO>();
		for(Entry<String,CityListVO> cvo:scmap.entrySet()){
			ctVOList.add(cvo.getValue());
		}
		return ctVOList;
	}
	/***
	 * 城市统计导出到excel
	 */
	public String findAnalysisCityByTimeAndNameToExcel(HttpServletResponse response,String name,String cityName,String startTime,String endTime) {
		List<CityListVO> ctVOList=findAnalysisCityByTimeAndNameToList(name, cityName, startTime, endTime);
		String[] title={"业务名称","业务标识","公交换乘","公交查询","步行导航","城市名称","业务总量"};
		 String result = ExportExcel.exportExcel(response, "analysisserver.xls", title, ctVOList);
		 return result;
	}
	
	/**
	 * 按业务账号分时段（每天、每小时、每分钟）统计业务量（GroupBy），即统计业务账号指定时间段内每天（小时或分钟）的调用量。
	*/
	public Map<String, Object> findAnalysisGroupByTimeAndType(int page,int rows,Integer position,String name,String startTime,String endTime) throws ParseException {
		Pageable pager = new PageRequest(page-1, rows);
		List<GroupListVO> glList=findAnalysisGroupByTimeAndType( position, name, startTime, endTime);
		return PageUtils.getPageMap(glList,pager);
	}
	
	private List<GroupListVO> findAnalysisGroupByTimeAndType(Integer position,String name,String startTime,String endTime){
		List<Object[]>	analysisGroupO=null;
		if(StringUtils.isBlank(name)||"all".equals(name)){
			if(position==7){
				analysisGroupO = analysisGroupDao.findByPosition7(startTime, endTime);
			}else if(position==10){
				analysisGroupO = analysisGroupDao.findByPosition10(startTime, endTime);
			}else if(position==13){
				analysisGroupO = analysisGroupDao.findByPosition13(startTime, endTime);
			}else{
				analysisGroupO = analysisGroupDao.findByPosition16(startTime, endTime);
			}
				
		}else{
			if(position==7){
				analysisGroupO = analysisGroupDao.findByPositionAndName7(name, startTime, endTime);
			}else if(position==10){
				analysisGroupO = analysisGroupDao.findByPositionAndName10(name, startTime, endTime);
			}else if(position==13){
				analysisGroupO = analysisGroupDao.findByPositionAndName13(name, startTime, endTime);
			}else{
				analysisGroupO = analysisGroupDao.findByPositionAndName16(name, startTime, endTime);
			}
		}
		List<GroupListVO> glList = new ArrayList<GroupListVO>();
		Map<String,UserKey> suMap =getKeyMap();
		for(Object[] o:analysisGroupO){
			GroupListVO gl = new GroupListVO();
			gl.setId("1");
			if(suMap.get(o[0].toString())!=null){
				gl.setName(suMap.get(o[0].toString()).getBusinessName());
			}else{
				gl.setName("未知业务");
			}
			gl.setKeyName(o[0].toString());
			String minute = o[1].toString();
			if(position==7){
				gl.setStartTime(minute.substring(0, 7));
				gl.setEndTime(DataUtils.parseEndTime(minute,7));
			}else if(position==10){
				gl.setStartTime(minute.substring(0, 10));
				gl.setEndTime(DataUtils.parseEndTime(minute,10));
			}else if(position==13){
				gl.setStartTime(minute.substring(0,13));
				gl.setEndTime(DataUtils.parseEndTime(minute,13));
			}else{
				gl.setStartTime(minute.substring(0, 16));
				gl.setEndTime(DataUtils.parseEndTime(minute,16));
			}
			gl.setTotal(o[2].toString());
			glList.add(gl);
		}
		return glList;
	}
	
	public String findAnalysisGroupByTimeAndTypeToExcel(HttpServletResponse response,Integer position,String name,String startTime,String endTime) throws ParseException {
		List<GroupListVO> glList=findAnalysisGroupByTimeAndType( position, name, startTime, endTime);
		String[] title={"开始时间段","结束时间段","业务名称","业务标识","业务总量"};
		 String result = ExportExcel.exportExcel(response, "analysistime.xls", title, glList);
		 return result;
	}
	
	/***
	 * 查询详细的访问log日志
	 */
	public Map<String, Object> findLogByTimeAndType(int page,int rows,String keyword,String name,String startTime,String endTime) {
		 Pageable pageable = new PageRequest(page-1, rows);
		 Page<AnalysisManage> am = null;
		 
		 if(StringUtils.isBlank(keyword)&&"all".equals(name)){//既无关键字也无类型查询
			 am = analysisManageDao.findByTimePage(startTime, endTime,pageable);
		 }else if(StringUtils.isBlank(keyword)&&!"all".equals(name)){ //类型查询
			 am = analysisManageDao.findByTimeAndName(startTime, endTime, name, pageable); 
		 }else if(StringUtils.isNotBlank(keyword)&&"all".equals(name)){//关键字查询
			 am = analysisManageDao.findByTimeAndSearchKey(startTime, endTime, "%"+keyword+"%", pageable);
		 }else if(StringUtils.isNotBlank(keyword)&&!"all".equals(name)){//关键字类型查询
			 am = analysisManageDao.findByTimeAndNameAndSearchKey(startTime, endTime, name, "%"+keyword+"%", pageable);
		 }
		 if(am!=null){
			long total = am.getTotalElements();
			List<AnalysisManage> AnalysisManageList =am.getContent();
			Map<String,UserKey> suMap =getKeyMap();
			for(AnalysisManage aam :AnalysisManageList){
				aam.setKeyName(aam.getName());
				if(suMap.get(aam.getName())!=null){
					aam.setName(suMap.get(aam.getName()).getBusinessName());
				}else{
					aam.setName("未知业务");
				}
				if(aam.getUrl()!=null){
					aam.setUrl(aam.getUrl().replaceAll("\\-", "&"));
				}else{
					aam.setUrl("无效访问");
				}
			}
			return PageUtils.getPageMap(total,AnalysisManageList);
		 }
		 
		 
		return PageUtils.getPageMap(am);
	}

	
	/***
	 * 查询业务账号
	 */
	public List<SpringMap> findNameAll(){
		Iterable<UserKey> stype = userKeyDao.findAll();
		List<SpringMap> lm = new ArrayList<SpringMap>();
		for(UserKey s:stype){
			SpringMap sm = new SpringMap();
			sm.setId(s.getKey());
			sm.setText(s.getBusinessName()+"-"+s.getKey());
			lm.add(sm);
		}
		SpringMap sm = new SpringMap();
		sm.setId("all");
		sm.setText("全部");
		lm.add(sm);
		return lm;
	}
	/**
	 * 服务类型
	 */
	public List<SpringMap> findTypeAll(){
		List<ServiceDict> stype = (List<ServiceDict>) serviceDictDao.findAll();
		List<SpringMap> lm = new ArrayList<SpringMap>();
		for(ServiceDict s:stype){
			SpringMap sm = new SpringMap();
			sm.setId(s.getServiceId()+"");
			sm.setText(s.getServiceName());
			lm.add(sm);
		}
		SpringMap sm = new SpringMap();
		sm.setId("all");
		sm.setText("全部");
		lm.add(sm);
		return lm;
	}
	
	/***
	  * 城市名称
	  */
	public List<SpringMap> findCityAll(){
		List<Area> stype = (List<Area>) areaDao.findAllFilterType();
		List<SpringMap> lm = new ArrayList<SpringMap>();
		for(Area s:stype){
			SpringMap sm = new SpringMap();
			sm.setId(s.getAreaName()+"");
			sm.setText(s.getAreaName());
			lm.add(sm);
		}
		SpringMap sm = new SpringMap();
		sm.setId("all");
		sm.setText("全部");
		lm.add(sm);
		return lm;
	}
	
	/***
	 * 分段类型
	 */
	public List<SpringMap> findTimeType(){
		List<SpringMap> lm = new ArrayList<SpringMap>();
			SpringMap Msm = new SpringMap();
			Msm.setId("7");
			Msm.setText("月");
			lm.add(Msm);
			SpringMap dsm = new SpringMap();
			dsm.setId("10");
			dsm.setText("日");
			lm.add(dsm);
			SpringMap hsm = new SpringMap();
			hsm.setId("13");
			hsm.setText("时");
			lm.add(hsm);
			SpringMap fsm = new SpringMap();
			fsm.setId("16");
			fsm.setText("分");
			lm.add(fsm);
			return lm;
		}
	
	/***
	 * 通过key获得用于权限信息
	 * @return
	 */
	public Map<String,UserKey> getKeyMap(){
		List<UserKey> ukList =(List<UserKey>) userKeyDao.findAll();
		Map<String,UserKey> map = new HashMap<String,UserKey>();
		for(UserKey uk:ukList){
			map.put(uk.getKey(), uk);
		}
		return map;
	}
	
	/***
	 * 通过key获得用于权限信息
	 * @return
	 */
	public Map<String,ServiceDict> getServiceDictMap(){
		List<ServiceDict> ukList =(List<ServiceDict>) serviceDictDao.findAll();
		Map<String,ServiceDict> map = new HashMap<String,ServiceDict>();
		for(ServiceDict uk:ukList){
			map.put(uk.getServiceId()+"", uk);
		}
		return map;
	}
}
