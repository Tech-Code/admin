package com.techapi.bus.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techapi.bus.dao.CityStationDao;
@Component("cityTransstationDataService")
public class CityTransstationDataService {

	@Autowired
	private CityStationDao dao;
	/***
	 * 站点、城际换乘 用于初始化数据
	 * 
	 * @return
	 */
	public List<CityTransstationRelation> getAllCityTransstation() {
		
		
		List<CityTransstationRelation> ctrList = new ArrayList<CityTransstationRelation>();
		
		Map<String, Map<String,List<TransstationInformation>>> slMap = new ConcurrentHashMap<String, Map<String,List<TransstationInformation>>>();
		List<Object[]> cityTransstationList = this.dao
				.findCityAndTransstation();
		for (Object[] o : cityTransstationList) {
			String transtype = (String) o[0];
			String trips = (String) o[1];
			String transdetail = (String) o[2];
			String citycode = (String) o[3];
			String station = (String) o[4];
			String stationorder = (String) o[5];
			String coordinate = (String) o[6];
			String arrivetime = (String) o[7];
			String departtime = (String) o[8];
			Double miles = Double.valueOf(o[9].toString());
			Double price = Double.valueOf(o[10].toString());
			String cityname = (String) o[11];
			TransstationInformation tif = new TransstationInformation(
					transtype, trips, transdetail, citycode, station,
					stationorder, coordinate, arrivetime, departtime, miles,
					price, cityname);
			if (slMap.containsKey(transtype)) {
				Map<String,List<TransstationInformation>> slt = slMap.get(transtype);
				if(slt.containsKey(trips)){
					slt.get(trips).add(tif);
				}else{
					List<TransstationInformation> tifList = new ArrayList<TransstationInformation>();
					tifList.add(tif);
					slt.put(trips, tifList);
				}
			} else {
				List<TransstationInformation> tifList = new ArrayList<TransstationInformation>();
				tifList.add(tif);
				Map<String,List<TransstationInformation>> slt = new HashMap<String,List<TransstationInformation>>();
				slt.put(trips, tifList);
				slMap.put(transtype, slt);
			}
		}
		
		// 遍历Map对象
		for (Entry<String, Map<String,List<TransstationInformation>>> entry : slMap.entrySet()) {
			CityTransstationRelation ctr = new CityTransstationRelation();
			ctr.setTransstationInformation(entry.getValue());
			ctr.setTranstype(entry.getKey());
			ctrList.add(ctr);
		}
		return ctrList;
	}
}
