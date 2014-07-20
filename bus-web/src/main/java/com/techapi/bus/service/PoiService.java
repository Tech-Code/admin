package com.techapi.bus.service;

import com.techapi.bus.BusConstants;
import com.techapi.bus.annotation.CacheProxy;
import com.techapi.bus.annotation.ServiceCache;
import com.techapi.bus.dao.PoiDao;
import com.techapi.bus.dao.StationDao;
import com.techapi.bus.entity.Poi;
import com.techapi.bus.entity.PoiPK;
import com.techapi.bus.entity.Station;
import com.techapi.bus.util.ExcelUtils;
import com.techapi.bus.util.MapUtil;
import com.techapi.bus.util.PageUtils;
import com.techapi.bus.util.TTL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
        if(station == null) {
            resultMap.put("result", BusConstants.RESULT_NO_EXIST_STATION);
            resultMap.put("alertInfo", BusConstants.RESULT_NO_EXIST_STATION_STR);
            return resultMap;
        }
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
        List<Object[]> poiObjectList = poiDao.findBySearch((page - 1) * rows, page * rows, "%%", "%%", "%%", "%%");

        List<Poi> poiList = convertObjectListToPoiList(poiObjectList);

        int totalCount = poiDao.findAllCount("%%", "%%", "%%", "%%");

        return PageUtils.getPageMap(totalCount,poiList);
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

    public Map<String, Object> findBySearchBySection(int page, int rows, String cityCode, String cityName, String poiName, String stationId) {
        //Page<Poi> page1 = poiDao.findAll(new Specification<Poi>() {
        //    public Predicate toPredicate(Root<Poi> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        //        List<Predicate> list = new ArrayList<>();
        //        if (cityCode != null && cityCode.trim().length() > 0) {
        //            list.add(cb.like(root.get("cityCode").as(String.class), "%" + cityCode + "%"));
        //        }
        //        if (poiName != null && poiName.trim().length() > 0) {
        //            list.add(cb.like(root.get("poiName").as(String.class), "%" + poiName + "%"));
        //        }
        //        if (stationId != null && stationId.trim().length() > 0) {
        //            list.add(cb.like(root.get("poiPK").get("stationId").as(String.class), "%" + stationId + "%"));
        //        }
        //        Predicate[] p = new Predicate[list.size()];
        //        return cb.and(list.toArray(p));
        //    }
        //}, new PageRequest(page-1, rows));
        //return PageUtils.getPageMap(page1);
        List<Object[]> poiObjectList = poiDao.findBySearch((page - 1) * rows, (page + 1) * rows, "%" + cityCode + "%", "%" + cityName + "%", "%" + poiName + "%", "%" + stationId + "%");

        List<Poi> poiList = convertObjectListToPoiList(poiObjectList);

        int totalCount = poiDao.findAllCount("%" + cityCode + "%", "%" + cityName + "%", "%" + poiName + "%", "%" + stationId + "%");

        return PageUtils.getPageMap(totalCount, poiList);


    }

    private List<Poi> convertObjectListToPoiList(List<Object[]> objects) {
        List<Poi> poiList = new ArrayList<>();
        for (Object[] o : objects) {
            String id = (o[0] == null ? "" : o[0].toString());
            String cityCode = (o[1] == null ? "" : o[1].toString());
            String stationId = (o[2] == null ? "" : o[2].toString());
            String poiId = (o[3] == null ? "" : o[3].toString());
            String poiName = (o[4] == null ? "" : o[4].toString());
            String poiType1 = (o[5] == null ? "" : o[5].toString());
            String poiType2 = (o[6] == null ? "" : o[6].toString());
            String poiType3 = (o[7] == null ? "" : o[7].toString());
            String poiCoordinate = (o[8] == null ? "" : o[8].toString());
            Double walkdistance = Double.valueOf(o[9].toString());
            String orientation = (o[10] == null ? "" : o[10].toString());
            String address = (o[11] == null ? "" : o[11].toString());
            String tel = (o[12] == null ? "" : o[12].toString());
            String cityName = (o[13] == null ? "" : o[13].toString());
            PoiPK poiPK = new PoiPK(stationId, poiId);

            Poi poi = new Poi(poiPK, id, cityCode, poiName, poiType1, poiType2, poiType3, poiCoordinate, walkdistance, orientation, address, tel, cityName);

            poiList.add(poi);
        }
        return poiList;
    }
}