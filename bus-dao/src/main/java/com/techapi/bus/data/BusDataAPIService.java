package com.techapi.bus.data;

import com.techapi.bus.BusConstants;
import com.techapi.bus.annotation.CacheProxy;
import com.techapi.bus.annotation.HessianSerializer;
import com.techapi.bus.annotation.ISerializer;
import com.techapi.bus.annotation.ServiceCache;
import com.techapi.bus.dao.PoiDao;
import com.techapi.bus.dao.SpeedDao;
import com.techapi.bus.dao.TaxiDao;
import com.techapi.bus.dao.UserKeyDao;
import com.techapi.bus.entity.*;
import com.techapi.bus.util.MapUtil;
import com.techapi.bus.util.TTL;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
	@Resource
	private UserKeyDao keyDao;
	@Autowired
	public CacheProxy cacheProxy;

	private static final ExecutorService exec = Executors
			.newFixedThreadPool(50);

	/***
	 * 城际站点表数据
	 * 
	 * @return
	 */
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
		// TODO 需要修改
		List<Poi> poiList = poiDao.findBystationID();
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
		// TODO 需要修改
		List<Poi> poiList = poiDao.findBystationID();
		if (poiList.size() > num) {
			return poiList.subList(0, num);
		}
		return poiList;
	}

	/***
	 * 批量返回一批地标点获得POI（实时接口）
	 * 
	 * @param stationids
	 *            ... 地标点串
	 * @return
	 */
	public Map<String, List<Poi>> findPoiBystationIDList(String... stationids) {
		Map<String, List<Poi>> map = new HashMap<String, List<Poi>>();
		for (String stationid : stationids) {
			// TODO 修改POI缓存问题
			String stationcache = String.format(BusConstants.BUS_GRID_POI,
					stationid);
			List<Poi> poiList;
			// 查询cache
			Object o=null;
			try {
				o = cacheProxy.get(stationcache);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (o == null) {
				// 查询数据库
				// TODO 需要修改
				poiList = poiDao.findBystationID();
				try {
					if (poiList != null) {
						// 补cache
						cacheProxy.put(stationcache, poiList, TTL._1H.getTime());
					} else {
						// 增加null，防止击穿cache，压力数据库
						cacheProxy.put(stationcache, new ArrayList<Poi>(),
								TTL._10M.getTime());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				poiList = (List<Poi>) o;
			}
			map.put(stationid, poiList);
		}
		return map;
	}

	public List<Poi> findPoiByGrids(String mc, int radius, int num) {
		List<Poi> pois = new ArrayList<Poi>();
		float[] lonlat = getLonlat(mc);
		LatLng center = new LatLng(lonlat[1], lonlat[0]);
		// 优先检索当前网格
		Grid grid = MapService.getGirdByLatLng(center);
		List<Object> list = findGridPoi(grid);

		if (list == null || list.size() < num) {
			if (list == null) {
				list = new ArrayList<Object>();
			}
			list.clear();

			List<Grid> grids = MapService.getGridsOfCircle(center, radius,
					false);
			// 清空集合
			for (Grid g : grids) {
				List<Object> objs = findGridPoi(g);
				if (objs != null && objs.size() > 0) {
					list.addAll(objs);
				}

				if (list.size() >= num) {
					break;
				}
			}
		}

		for (Object obj : list) {
			Poi poi = (Poi) obj;
			float[] _lonlat = getLonlat(poi.getPoiCoordinate());
			String dir = MapUtil.getDirection(lonlat[0], lonlat[1], _lonlat[0],
					_lonlat[1]);
			poi.setOrientation(dir);
			double dis = MapUtil.getDistance(lonlat[0], lonlat[1], _lonlat[0],
					_lonlat[1]);
			poi.setWalkDistance(dis);

			if (dis <= radius) {
				pois.add(poi);
			}
		}

		Collections.sort(pois);

		if (pois.size() > num) {
			List<Poi> subList = pois.subList(0, num);
			List<Poi> result = new ArrayList<Poi>();
			for (Poi p : subList) {
				result.add(p);
			}

			return result;
		}

		return pois;
	}

	/**
	 * @param mc
	 * @return
	 */
	private float[] getLonlat(String mc) {
		// TODO Auto-generated method stub
		float[] result = new float[2];
		if (StringUtils.isEmpty(mc) || !mc.contains(",")) {
			return result;
		}
		String[] lonlat = mc.split(",");
		float lon = Float.valueOf(lonlat[0]);
		float lat = Float.valueOf(lonlat[1]);

		return new float[] { lon, lat };
	}

	/**
	 * @param grid
	 * @return
	 */
	private List<Object> findGridPoi(Grid grid) {
		// TODO Auto-generated method stub
		String gridStr = grid.getId();

		// TODO 修改POI缓存问题
		String keyRegex = String.format(BusConstants.BUS_GRID_KEY, gridStr);
		try {
			Set<String> ids = cacheProxy.keys(keyRegex, 3);
			if (ids != null && ids.size() > 0) {
				List<String> keys = new ArrayList<String>(ids);
				return cacheProxy.mGet(keys, 3);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
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
		List<Speed> speedList = speedDao.findByTransportation(transportation);
		if (speedList == null || speedList.size() == 0) {
			speedList = speedDao.findByTransportation(transportation);
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
		List<Speed> speedList = speedDao.findByTransportation(transportation);
		if (speedList == null || speedList.size() == 0) {
			speedList = speedDao.findByTransportation(transportation);
		}
		if (speedList != null && speedList.size() > 0) {
			return speedList.get(0);
		}
		return null;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public Object findUserKey(String key) {
		int index = 2;
		ISerializer serializer = new HessianSerializer();
		String sKey = "BUS:CTL:" + key;

		Object obj = cacheProxy.get(sKey, index);
		if (obj == null) {
			UserKey user = keyDao.findOne(key);

			if (user != null) {
				// 数据持久化
				JedisPool pool = cacheProxy.getJedisPool();
				Jedis jedis = pool.getResource();
				jedis.select(index);

				byte[] bytesValue = serializer.encode(user);
				byte[] bytesKey = sKey.getBytes();
				jedis.set(bytesKey, bytesValue);
				pool.returnResource(jedis);
			}
			return user;
		}
		return obj;
	}
}
