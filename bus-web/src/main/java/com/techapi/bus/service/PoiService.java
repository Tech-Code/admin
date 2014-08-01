package com.techapi.bus.service;

import com.techapi.bus.BusConstants;
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
                    poi.setCityName(area.getAreaName());
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
        poiDao.save(poi);

        resultMap.put("poiId", poi.getPoiId());
        resultMap.put("result", BusConstants.RESULT_SUCCESS);
        resultMap.put("alertInfo", BusConstants.RESULT_SUCCESS_STR);

        return resultMap;
    }

    public Map<String, Object> findSection(int page, int rows,String cityName) {
        List<Poi> poiList = poiDao.findBySearch((page - 1) * rows, page * rows, "%%", "%"+ cityName+"%", "%%");


        int totalCount = poiDao.findAllCount("%%", "%" + cityName + "%", "%%");

        return PageUtils.getPageMap(totalCount,poiList);
    }

    /**
     * @return
     */
    public Poi findById(String id) {
        Poi poi = poiDao.findByPoiId(id);
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
        float d_lon = 0.0f;
        float d_lat = 0.0f;
        int iRange = 0;
        if((centerLonLat != null && !centerLonLat.isEmpty()) && range != null && !range.isEmpty()) {
            String[] lonLat = centerLonLat.split(",");
            String lon = lonLat[0];
            String lat = lonLat[1];

            d_lon = Float.parseFloat(lon);
            d_lat = Float.parseFloat(lat);

            iRange = Integer.parseInt(range);


            List<Grid> gridsOfBoundingBox = MapService.getGridsOfBoundingBox(new LatLng(d_lat, d_lon), iRange, false);

            List<String> gridIds = new ArrayList<>();
            for (Grid grid : gridsOfBoundingBox) {
                gridIds.add(grid.getId());
            }

            gridIdArray = gridIds.toArray(new String[0]);
            //strGridIds = "'" + StringUtils.join(gridIdArray,"','") + "'";

        }
        List<Poi> poiList;
        int totalCount;
        if(gridIdArray != null && gridIdArray.length > 0) {
            //poiList = poiDao.findBySearchAndGridIds((page - 1) * rows, page * rows, "%" + cityCode + "%", "%" + cityName + "%", "%" + poiName + "%", gridIdArray);
            poiList = poiDao.findBySearchAndGridIdsAndRange((page - 1) * rows, page * rows, "%" + cityCode + "%", "%" + cityName + "%", "%" + poiName + "%", gridIdArray, iRange, d_lon, d_lat);
            //totalCount = poiDao.findAllCountByGridIds("%" + cityCode + "%", "%" + cityName + "%", "%" + poiName + "%", gridIdArray);
            totalCount = poiDao.findBySearchAndGridIdsAndRangeCount("%" + cityCode + "%", "%" + cityName + "%", "%" + poiName + "%", gridIdArray, iRange, d_lon, d_lat);
        } else {
            poiList = poiDao.findBySearch((page - 1) * rows, page * rows, "%" + cityCode + "%", "%" + cityName + "%", "%" + poiName + "%");
            totalCount = poiDao.findAllCount("%" + cityCode + "%", "%" + cityName + "%", "%" + poiName + "%");
        }

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