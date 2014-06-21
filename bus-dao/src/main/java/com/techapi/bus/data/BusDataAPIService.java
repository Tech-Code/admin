package com.techapi.bus.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techapi.bus.BusConstants;
import com.techapi.bus.annotation.CacheProxy;
import com.techapi.bus.annotation.ServiceCache;
import com.techapi.bus.dao.PoiDao;
import com.techapi.bus.dao.SpeedDao;
import com.techapi.bus.dao.TaxiDao;
import com.techapi.bus.entity.Poi;
import com.techapi.bus.entity.Speed;
import com.techapi.bus.entity.Taxi;
import com.techapi.bus.util.TTL;

@Service
public class BusDataAPIService {

	@Autowired
	public CityTransstationDataService cityTransstationDataService;
	@Resource
	private PoiDao poiDao;
	@Resource
	private SpeedDao speedDao;
	@Resource
	private TaxiDao taxiDao;
	@Autowired
	public CacheProxy cacheProxy;

	private static final ExecutorService exec = Executors
			.newFixedThreadPool(50);

	/***
	 * 城际站点表数据
	 * 
	 * @return
	 */
	@ServiceCache(TTL._5M)
	public List<CityTransstationRelation> getAllCityTransstation() {
		List<CityTransstationRelation> city = cityTransstationDataService
				.getAllCityTransstation();
		return city;
	}

	/**
	 * 根据地标点获得一个POI（实时接口）
	 * 
	 * @param stationid
	 *            站点ID
	 * @return
	 */
	@ServiceCache(TTL._30M)
	public Poi findOnePoiBystationID(String stationid) {
		List<Poi> poiList = poiDao.findBystationID(stationid);
		if (poiList != null && poiList.size() > 0) {
			return poiList.get(0);
		}
		return null;
	}

	/**
	 * 根据地标点获得POI（实时接口）
	 * 
	 * @param stationid
	 *            站点ID
	 * @param stationid
	 *            返回数据条数
	 * @return
	 */
	@ServiceCache(TTL._30M)
	public List<Poi> findPoiBystationID(String stationid, Integer num) {
		List<Poi> poiList = poiDao.findBystationID(stationid);
		if (poiList.size() > num) {
			return poiList.subList(0, num);
		}
		return poiList;
	}

	/***
	 * 批量返回一批地标点获得POI（实时接口）
	 * 
	 * @param stationid
	 *            ... 地标点串
	 * @return
	 */
	public Map<String, List<Poi>> findPoiBystationIDList(String... stationids) {
		Map<String, List<Poi>> map = new HashMap<String, List<Poi>>();
		for (String stationid : stationids) {
			String stationcache = String.format(BusConstants.BUS_POI_STATIONID,
					stationid);
			List<Poi> poiList;
			// 查询cache
			Object o = cacheProxy.get(stationcache);
			if (o == null) {
				// 查询数据库
				poiList = poiDao.findBystationID(stationid);
				if (poiList != null) {
					// 补cache
					cacheProxy.put(stationcache, poiList, TTL._1H.getTime());
				} else {
					// 增加null，防止击穿cache，压力数据库
					cacheProxy.put(stationcache, new ArrayList<Poi>(),
							TTL._10M.getTime());
				}
			} else {
				poiList = (List<Poi>) o;
			}
			map.put(stationid, poiList);
		}
		return map;
	}

	/***
	 * 根据城市名获得一个出租车费用
	 * 
	 * @param cityname
	 *            城市名称
	 * @return
	 */
	@ServiceCache(TTL._30M)
	public Taxi findOneTaxiByCityName(String cityname) {
		List<Taxi> poiList = taxiDao.findBycityName(cityname);
		if (poiList != null && poiList.size() > 0) {
			return poiList.get(0);
		}
		return null;
	}

	/***
	 * 根据城市名获得出租车费用
	 * 
	 * @param cityname
	 *            城市名称
	 * @return
	 */
	@ServiceCache(TTL._30M)
	public List<Taxi> findTaxiByCityName(String cityname) {
		List<Taxi> poiList = taxiDao.findBycityName(cityname);
		return poiList;
	}

	/***
	 * 根据交通工具和城市获得时速列表
	 * 
	 * @param transportation
	 *            交通工具
	 * @param cityname
	 *            城市名称
	 * @return
	 */
	@ServiceCache(TTL._30M)
	public List<Speed> findSpeedByCityTransportation(String transportation,
			String cityname) {
		List<Speed> speedList = speedDao.findByTransportationAndCityName(
				transportation, cityname);
		if (speedList == null || speedList.size() == 0) {
			speedList = speedDao.findByTransportationAndCityName(
					transportation, BusConstants.DEFAULT_CITYNAME);
		}
		return speedList;
	}

	/***
	 * 根据交通工具和交通工具明细和城市名称获得时速列表
	 * 
	 * @param transportation
	 *            交通工具
	 * @param cityname
	 *            城市名称
	 * @param transportdes
	 *            交通工具明细
	 * @return
	 */
	@ServiceCache(TTL._30M)
	public Speed findOneSpeedByCityTransportation(String transportation,
			String cityname, String transportdes) {
		List<Speed> speedList = speedDao
				.findByTransportationAndDetailAndCityName(transportation,
						cityname, transportdes);
		if (speedList == null || speedList.size() == 0) {
			speedList = speedDao
					.findByTransportationAndDetailAndCityName(transportation,
							BusConstants.DEFAULT_CITYNAME, transportdes);
		}
		if (speedList != null && speedList.size() > 0) {
			return speedList.get(0);
		}
		return null;
	}
}
