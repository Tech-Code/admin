package com.techapi.bus.service;

import com.techapi.bus.BusConstants;
import com.techapi.bus.annotation.CacheProxy;
import com.techapi.bus.annotation.ServiceCache;
import com.techapi.bus.dao.AreaDao;
import com.techapi.bus.dao.PoiDao;
import com.techapi.bus.data.MapService;
import com.techapi.bus.entity.Area;
import com.techapi.bus.entity.Grid;
import com.techapi.bus.entity.LatLng;
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
    private AreaDao areaDao;
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
        String poiName = poi.getPoiName();
        Poi existedPoi;

        String cityCode = poi.getCityCode();
        String cityName = poi.getCityName();
        if(cityCode == null || cityCode.isEmpty()) {
            if(cityName != null && !cityName.isEmpty()) {
                Area area = areaDao.findByAreaName(cityName);
                if(area != null) {
                    poi.setCityCode(area.getAdCode().toString());
                } else {
                    resultMap.put("result", BusConstants.RESULT_NO_EXIST_CITYNAME);
                    resultMap.put("alertInfo", BusConstants.RESULT_NO_EXIST_CITYNAME_STR);
                    return resultMap;
                }
            }
        }

        String gridId = MapUtil.getGridId(poi.getPoiCoordinate());

        existedPoi = poiDao.findByPoiNameAndGridId(poiName, gridId);

        if (poiId == null || poiId.isEmpty()) { //新增
            poiId = UUID.randomUUID().toString();
            poi.setPoiId(poiId);
            if (existedPoi != null) {
                resultMap.put("result", BusConstants.RESULT_REPEAT_POI);
                resultMap.put("alertInfo", BusConstants.RESULT_REPEAT_POI_STR);
                return resultMap;
            }
        } else {
            if (existedPoi != null) {
                if (!existedPoi.getPoiId().trim().equals(poi.getPoiId().trim())) {
                    resultMap.put("result", BusConstants.RESULT_REPEAT_POI);
                    resultMap.put("alertInfo", BusConstants.RESULT_REPEAT_POI_STR);
                    return resultMap;
                }

            }
        }

        // 更新gridId
        poi.setGridId(gridId);
        //poiDao.save(poi);

        resultMap.put("poiId", poi.getPoiId());
        resultMap.put("result", BusConstants.RESULT_SUCCESS);
        resultMap.put("alertInfo", BusConstants.RESULT_SUCCESS_STR);

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
        List<Object[]> objects = poiDao.findOneById(id);
        List<Poi> poiList = convertObjectListToPoiList(objects);
        return poiList.get(0);
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

    public Map<String, Object> findBySearchBySection(int page, int rows, String cityCode, String cityName, String poiName, String centerLonLat, String range) {
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
        // 中心点经纬度
        String[] gridIdArray = null;
        if((centerLonLat != null && !centerLonLat.isEmpty()) && range != null && !range.isEmpty()) {
            String[] lonLat = centerLonLat.split(",");
            String lon = lonLat[0];
            String lat = lonLat[1];

            float d_lon = Float.parseFloat(lon);
            float d_lat = Float.parseFloat(lat);

            int iRange = Integer.parseInt(range);


            List<Grid> gridsOfBoundingBox = MapService.getGridsOfBoundingBox(new LatLng(d_lat, d_lon), iRange, false);

            List<String> gridIds = new ArrayList<>();
            for (Grid grid : gridsOfBoundingBox) {
                gridIds.add(grid.getId());
            }

            gridIdArray = gridIds.toArray(new String[0]);
            //strGridIds = "'" + StringUtils.join(gridIdArray,"','") + "'";

        }
        List<Object[]> poiObjectList;
        int totalCount;
        if(gridIdArray != null && gridIdArray.length > 0) {
            //if(cityName.equals())
            poiObjectList = poiDao.findBySearchAndGridIds((page - 1) * rows, page * rows, "%" + cityCode + "%", "%" + cityName + "%", "%" + poiName + "%", gridIdArray);
            totalCount = poiDao.findAllCountByGridIds("%" + cityCode + "%", "%" + cityName + "%", "%" + poiName + "%", gridIdArray);
        } else {
            poiObjectList = poiDao.findBySearch((page - 1) * rows, page * rows, "%" + cityCode + "%", "%" + cityName + "%", "%" + poiName + "%");
            totalCount = poiDao.findAllCount("%" + cityCode + "%", "%" + cityName + "%", "%" + poiName + "%");
        }


        List<Poi> poiList = convertObjectListToPoiList(poiObjectList);
        return PageUtils.getPageMap(totalCount, poiList);


    }



    private List<Poi> convertObjectListToPoiList(List<Object[]> objects) {
        List<Poi> poiList = new ArrayList<>();
        for (Object[] o : objects) {
            String cityCode = (o[0] == null ? "" : o[0].toString());
            String poiId = (o[1] == null ? "" : o[1].toString());
            String poiName = (o[2] == null ? "" : o[2].toString());
            String poiType1 = (o[3] == null ? "" : o[3].toString());
            String poiType2 = (o[4] == null ? "" : o[4].toString());
            String poiType3 = (o[5] == null ? "" : o[5].toString());
            String poiCoordinate = (o[6] == null ? "" : o[6].toString());
            String address = (o[7] == null ? "" : o[7].toString());
            String tel = (o[8] == null ? "" : o[8].toString());
            String gridId = (o[9] == null ? "" : o[9].toString());
            String cityName = (o[10] == null ? "" : o[10].toString());


            Poi poi = new Poi(poiId, cityCode, poiName, poiType1, poiType2, poiType3, poiCoordinate, address, tel, gridId, cityName);

            poiList.add(poi);
        }
        return poiList;
    }

    private Poi convertObjectToPoi(Object[] o) {
        Poi poi = new Poi();
        if(o != null) {
            String cityCode = (o[0] == null ? "" : o[0].toString());
            String poiId = (o[1] == null ? "" : o[1].toString());
            String poiName = (o[2] == null ? "" : o[2].toString());
            String poiType1 = (o[3] == null ? "" : o[3].toString());
            String poiType2 = (o[4] == null ? "" : o[4].toString());
            String poiType3 = (o[5] == null ? "" : o[5].toString());
            String poiCoordinate = (o[6] == null ? "" : o[6].toString());
            String address = (o[7] == null ? "" : o[7].toString());
            String tel = (o[8] == null ? "" : o[8].toString());
            String gridId = (o[9] == null ? "" : o[9].toString());
            String cityName = (o[10] == null ? "" : o[10].toString());

            poi = new Poi(poiId, cityCode, poiName, poiType1, poiType2, poiType3, poiCoordinate, address, tel, gridId, cityName);
        }
        return poi;
    }
}