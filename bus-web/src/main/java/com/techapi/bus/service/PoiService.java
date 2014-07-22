package com.techapi.bus.service;

import com.techapi.bus.BusConstants;
import com.techapi.bus.annotation.CacheProxy;
import com.techapi.bus.annotation.ServiceCache;
import com.techapi.bus.dao.PoiDao;
import com.techapi.bus.dao.StationDao;
import com.techapi.bus.entity.Poi;
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
 * @author fei.xue
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
        String poiId = poi.getPoiId();
        Poi existedPoi;

        if (poiId == null || poiId.isEmpty()) {
            // TODO 修改查询问题
            existedPoi = poiDao.findByCityCodeAndPoiNameAndCoorinate(poiId);
            if (existedPoi != null) {
                resultMap.put("result", BusConstants.RESULT_REPEAT_POI);
                resultMap.put("alertInfo", BusConstants.RESULT_REPEAT_POI_STR);
                return resultMap;
            }
        } else {
            existedPoi = poiDao.findByPoiId(poiId);
            if (existedPoi != null) {
                if (!existedPoi.getPoiId().trim().equals(poi.getPoiId().trim())) {
                    resultMap.put("result", BusConstants.RESULT_REPEAT_POI);
                    resultMap.put("alertInfo", BusConstants.RESULT_REPEAT_POI_STR);
                    return resultMap;
                }

            }
        }

        poiDao.save(poi);

        resultMap.put("poiId", poi.getPoiId());
        resultMap.put("result", BusConstants.RESULT_SUCCESS);
        resultMap.put("alertInfo", BusConstants.RESULT_SUCCESS_STR);

        // 写redis
        String poicache = String.format(BusConstants.BUS_GRID_POI, poi.getGridId(),poi.getPoiId());
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
        List<Object[]> poiObjectList = poiDao.findBySearch((page - 1) * rows, page * rows, "%%", "%%", "%%");

        List<Poi> poiList = convertObjectListToPoiList(poiObjectList);

        int totalCount = poiDao.findAllCount("%%", "%%", "%%");

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
                String poicache = String.format(BusConstants.BUS_GRID_POI, MapUtil.getGridId(poi.getPoiCoordinate()),poi.getPoiId());
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
        List<Object[]> poiObjectList = poiDao.findBySearch((page - 1) * rows, (page + 1) * rows, "%" + cityCode + "%", "%" + cityName + "%", "%" + poiName + "%");

        List<Poi> poiList = convertObjectListToPoiList(poiObjectList);

        int totalCount = poiDao.findAllCount("%" + cityCode + "%", "%" + cityName + "%", "%" + poiName + "%");

        return PageUtils.getPageMap(totalCount, poiList);


    }

    private List<Poi> convertObjectListToPoiList(List<Object[]> objects) {
        List<Poi> poiList = new ArrayList<>();
        for (Object[] o : objects) {
            String cityCode = (o[1] == null ? "" : o[1].toString());
            String poiId = (o[3] == null ? "" : o[3].toString());
            String poiName = (o[4] == null ? "" : o[4].toString());
            String poiType1 = (o[5] == null ? "" : o[5].toString());
            String poiType2 = (o[6] == null ? "" : o[6].toString());
            String poiType3 = (o[7] == null ? "" : o[7].toString());
            String poiCoordinate = (o[8] == null ? "" : o[8].toString());
            String address = (o[11] == null ? "" : o[11].toString());
            String tel = (o[12] == null ? "" : o[12].toString());
            String cityName = (o[13] == null ? "" : o[13].toString());


            Poi poi = new Poi(poiId, cityCode, poiName, poiType1, poiType2, poiType3, poiCoordinate, address, tel, cityName);

            poiList.add(poi);
        }
        return poiList;
    }
}