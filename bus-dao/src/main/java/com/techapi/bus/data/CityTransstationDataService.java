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
public class CityTransstationDataService implements java.io.Serializable{

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
		List<Object[]> cityTransstationList = this.dao.findCityAndTransstation();
		for (Object[] o : cityTransstationList) {
			String transtype = (o[0]==null?"":o[0].toString());
			String trips = (o[1]==null?"":o[1].toString());
			String transdetail = (o[2]==null?"":o[2].toString());
			String citycode = (o[3]==null?"":o[3].toString());
			String station = (o[4]==null?"":o[4].toString());
			String stationorder =  (o[5]==null?"":o[5].toString());
			String coordinate = (o[6]==null?"":o[6].toString());
			String arrivetime = (o[7]==null?"":o[7].toString());
			String departtime = (o[8]==null?"":o[8].toString());
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
