package com.techapi.bus.data;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.techapi.bus.BusConstants;
import com.techapi.bus.dao.PoiDao;
import com.techapi.bus.dao.SpeedDao;
import com.techapi.bus.dao.TaxiDao;
import com.techapi.bus.entity.Poi;
import com.techapi.bus.entity.Speed;
import com.techapi.bus.entity.Taxi;
@Component("busDataAPIService")
public class BusDataAPIService {

	@Autowired
	public CityTransstationDataService cityTransstationDataService;
	@Autowired
    private PoiDao poiDao;
	@Autowired
    private SpeedDao speedDao;
	@Autowired
    private TaxiDao taxiDao;
	/***
	 * 城际站点表数据
	 * @return
	 */
	public List<CityTransstationRelation> getAllCityTransstation() {
		List<CityTransstationRelation> city = cityTransstationDataService.getAllCityTransstation();
		return city;
	}
	/**
	 * 根据地标点获得一个POI（实时接口）
	 * @param stationid 站点ID
	 * @return
	 */
	public Poi findOnePoiBystationID(String stationid){
		List<Poi> poiList =poiDao.findBystationID(stationid);
		if(poiList!=null&&poiList.size()>0){
			return poiList.get(0);
		}
		return null;
	}
	/**
	 * 根据地标点获得POI（实时接口）
	 * @param stationid 站点ID
	 * @return
	 */
	public List<Poi> findPoiBystationID(String stationid){
		List<Poi> poiList =poiDao.findBystationID(stationid);
		return poiList;
	}
	/***
	 * 根据城市名获得一个出租车费用
	 * @param cityname 城市名称
	 * @return
	 */
	public Taxi findOneTaxiByCityName(String cityname){
		List<Taxi> poiList =taxiDao.findBycityName(cityname);
		if(poiList!=null&&poiList.size()>0){
			return poiList.get(0);
		}
		return null;
	}
	/***
	 * 根据城市名获得出租车费用
	 * @param cityname 城市名称
	 * @return
	 */
	public List<Taxi> findTaxiByCityName(String cityname){
		List<Taxi> poiList =taxiDao.findBycityName(cityname);
		return poiList;
	}
	/***
	 * 根据交通工具和城市获得时速列表
	 * @param transportation 交通工具
	 * @param cityname  城市名称
	 * @return
	 */
	public List<Speed> findSpeedByCityTransportation(String transportation,String cityname){
		List<Speed> speedList = speedDao.findByTransportationAndCityName(transportation, cityname);
		if(speedList==null||speedList.size()==0){
			speedList=speedDao.findByTransportationAndCityName(transportation, BusConstants.DEFAULT_CITYNAME);
		}
		return speedList;
	}
	/***
	 * 根据交通工具和交通工具明细和城市名称获得时速列表
	 * @param transportation 交通工具
	 * @param cityname  城市名称
	 * @param transportdes 交通工具明细
	 * @return
	 */
	public Speed findOneSpeedByCityTransportation(String transportation,String cityname,String transportdes){
		List<Speed> speedList = speedDao.findByTransportationAndDetailAndCityName(transportation, cityname,transportdes);
		if(speedList==null||speedList.size()==0){
			speedList=speedDao.findByTransportationAndDetailAndCityName(transportation, BusConstants.DEFAULT_CITYNAME,transportdes);
		}
		if(speedList!=null&&speedList.size()>0){
			return speedList.get(0);
		}
		return null;
	}
	
	
	
	
	
	
	
	
}
