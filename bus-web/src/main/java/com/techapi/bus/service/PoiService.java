package com.techapi.bus.service;

import com.techapi.bus.BusConstants;
import com.techapi.bus.annotation.CacheProxy;
import com.techapi.bus.annotation.ServiceCache;
import com.techapi.bus.dao.PoiDao;
import com.techapi.bus.dao.StationDao;
import com.techapi.bus.entity.Poi;
import com.techapi.bus.entity.Station;
import com.techapi.bus.util.ExcelUtils;
import com.techapi.bus.util.MapUtil;
import com.techapi.bus.util.PageUtils;
import com.techapi.bus.util.TTL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Description:
 * @author hongjia.hu
 * @date 2014-3-8
 */
@Service
public class PoiService {

	@Resource
	private PoiDao poiDao;
    @Resource
    private StationDao stationDao;
    @Autowired
    public CacheProxy cacheProxy;

    private static Map<String,Map<String,List<String>>> poiTypeMap = new TreeMap<>();

    public Map<String, Map<String, List<String>>> getPoiTypeData() {
        if(poiTypeMap == null || poiTypeMap.size() == 0) {
            poiTypeMap = ExcelUtils.readPoiType();
        }
        return poiTypeMap;
    }

    @ServiceCache(TTL._30M)
    public Map<String, String> addOrUpdate(Poi poi) {

        Map<String, String> resultMap = new HashMap<>();
        String poiId = poi.getPoiPK().getPoiId();
        String stationId = poi.getPoiPK().getStationId();
        String id = poi.getId();
        Poi existedPoi;

        if (id == null || id.isEmpty()) {
            existedPoi = poiDao.findBystationIDAndPoiId(stationId, poiId);
            if (existedPoi != null) {
                resultMap.put("result", BusConstants.RESULT_REPEAT_POI);
                resultMap.put("alertInfo", BusConstants.RESULT_REPEAT_POI_STR);
                return resultMap;
            }
        } else {
            existedPoi = poiDao.findOneById(id);
            if (existedPoi != null) {
                if (!existedPoi.getPoiPK().getPoiId().trim().equals(poi.getPoiPK().getPoiId().trim()) ||
                        !existedPoi.getPoiPK().getStationId().trim().equals(poi.getPoiPK().getStationId().trim())) {
                    resultMap.put("result", BusConstants.RESULT_REPEAT_POI);
                    resultMap.put("alertInfo", BusConstants.RESULT_REPEAT_POI_STR);
                    return resultMap;
                }

            }
        }

        // 添加方位与距离计算
        Station station = stationDao.findOne(stationId);
        String poiCoordinate = poi.getPoiCoordinate();
        String[] poiLonLat = poiCoordinate.split(",");
        String direction = MapUtil.getDirection(Double.parseDouble(station.getStationLon()),
                Double.parseDouble(station.getStationLat()),
                Double.parseDouble(poiLonLat[0]),
                Double.parseDouble(poiLonLat[1]));
        double distance = MapUtil.getDistance(Double.parseDouble(station.getStationLon()),
                Double.parseDouble(station.getStationLat()),
                Double.parseDouble(poiLonLat[0]),
                Double.parseDouble(poiLonLat[1]));
        poi.setWalkDistance(distance);
        poi.setOrientation(direction);

        poiDao.save(poi);

        resultMap.put("id", poi.getId());
        resultMap.put("result", BusConstants.RESULT_SUCCESS);
        resultMap.put("alertInfo", BusConstants.RESULT_SUCCESS_STR);

        // 写redis
        String poicache = String.format(BusConstants.BUS_POI_STATIONID, poi.getPoiPK().getStationId());
        if (poi != null) {
            //补cache
            cacheProxy.put(poicache, poi);
        } else {
            //增加null，防止击穿cache，压力数据库
            cacheProxy.put(poicache, new Poi(), TTL._10M.getTime());
        }

        return resultMap;
    }

    public Map<String, Object> findSection(int page, int rows) {
        Pageable pager = new PageRequest(page-1, rows);
        Page<Poi> poiList = poiDao.findAll(pager);

        return PageUtils.getPageMap(poiList);
    }

    /**
     * @return
     */
    public Poi findById(String id) {
        Poi poi = poiDao.findOneById(id);
        return poi;
    }

    public List<Poi> findByIds(List<String> ids) {
        String[] idArray = ids.toArray(new String[0]);
        List<Poi> deletePoi = poiDao.findByids(idArray);
        return deletePoi;
    }

    public void deleteOne(String id) {
        poiDao.delete(id);
    }

    public void deleteMany(List<Poi> poiList) {
        for (Poi poi : poiList) {
            if (poi != null) {
                String poicache = String.format(BusConstants.BUS_POI_STATIONID, poi.getPoiPK().getStationId());
                cacheProxy.delete(poicache);
            }
        }
        poiDao.delete(poiList);

    }

}