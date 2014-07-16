package com.techapi.bus.service;

import com.techapi.bus.BusConstants;
import com.techapi.bus.dao.CityStationDao;
import com.techapi.bus.entity.CityStation;
import com.techapi.bus.solr.BusUpdate;
import com.techapi.bus.solr.basic.BaseOperate;
import com.techapi.bus.util.PageUtils;
import com.techapi.bus.util.PropertyMapUtils;
import com.techapi.bus.vo.SpringMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description:
 * @author hongjia.hu
 * @date 2014-3-8
 */
@Service
public class CityStationService extends BaseOperate {

	@Resource
	private CityStationDao cityStationDao;

    public Map<String, String> addOrUpdate(CityStation cityStation) {
        Map<String, String> resultMap = new HashMap<>();
        String id = cityStation.getId();
        List<CityStation> cityStationList = cityStationDao.findByStationName(cityStation.getCityStationName());
        if (id == null || id.isEmpty()) {
            if (cityStationList.size() > 0) {
                resultMap.put("result", BusConstants.RESULT_REPEAT_STATION);
                resultMap.put("alertInfo", BusConstants.RESULT_REPEAT_STATION_STR);
                return resultMap;
            }
        } else {
            if (cityStationList.size() > 0) {
                CityStation editCityStation = cityStationDao.findOne(id);
                if(!editCityStation.getCityStationName().trim().equals(cityStation.getCityStationName().trim())) {
                    resultMap.put("result", BusConstants.RESULT_REPEAT_STATION);
                    resultMap.put("alertInfo", BusConstants.RESULT_REPEAT_STATION_STR);
                    return resultMap;
                }

            }
        }
        String transType = PropertyMapUtils.getPoiTypeName(cityStation.getTransType());
        cityStation.setTransType(transType);

        cityStation = cityStationDao.save(cityStation);

        resultMap.put("id", cityStation.getId());
        resultMap.put("result", BusConstants.RESULT_SUCCESS);
        resultMap.put("alertInfo", BusConstants.RESULT_SUCCESS_STR);
        new BusUpdate().updateCityStation(cityStation);
        return resultMap;
    }

	/**
	 * @return
	 */
	public Map<String,Object> findAll() {
        //cityStationDao.updateCityStation("010","北京");
		List<CityStation>cityStationList = (List<CityStation>)cityStationDao.findAll();
        return PageUtils.getPageMap(cityStationList);
	}

    public Map<String, Object> findSection(int page, int rows) {
        Pageable pager = new PageRequest(page - 1, rows);
        Page<CityStation> cityStationList = cityStationDao.findAll(pager);
        return PageUtils.getPageMap(cityStationList);
    }

    /**
     * @return
     */
    public CityStation findById(String id) {
        //cityStationDao.updateCityStation("010","北京");
        CityStation cityStation = cityStationDao.findOne(id);
        return cityStation;
    }

    public List<CityStation> findByIds(List<String> ids) {
        return (List<CityStation>)cityStationDao.findAll(ids);
    }

    public void deleteOne(String id) {
        cityStationDao.delete(id);
    }

    public void deleteMany(List<CityStation> cityStationList) {
        List<String> ids = new ArrayList<>();
        for(CityStation cityStation : cityStationList) {
            ids.add(cityStation.getId());
        }
        deleteBeanByIds(ids);
        cityStationDao.delete(cityStationList);
    }


    public List<CityStation> suggetList(String q) {
        List<CityStation> cityStationNameList = (ArrayList) queryBeans(q, 0, 10, CityStation.class);
        return cityStationNameList;
    }

    public List<SpringMap> findAllTransTypes() {
        Map<String, String> poiTypeNameMap = PropertyMapUtils.getPoiTypeNameMap();
        Iterator<String> iterator = poiTypeNameMap.keySet().iterator();
        List<SpringMap> springMapList = new ArrayList<>();
        while(iterator.hasNext()) {
            String poiTypeName = iterator.next();
            SpringMap springMap = new SpringMap();
            springMap.setId(poiTypeNameMap.get(poiTypeName));
            springMap.setText(poiTypeName);
            springMapList.add(springMap);
        }
        SpringMap totalSpringMap = new SpringMap();
        totalSpringMap.setId("all");
        totalSpringMap.setText("全部");
        springMapList.add(totalSpringMap);

        return springMapList;
    }


    public Map<String, Object> findBySearchBySection(int page, int rows,String cityCode, String cityName, String selectTransType, String stationName) {
        Pageable pager = new PageRequest(page - 1, rows);

        List<CityStation> searchResult = cityStationDao.findBySearch("%" + cityCode + "%", "%" + cityName + "%", "%" + selectTransType + "%", "%" + stationName + "%");

        return PageUtils.getPageMap(searchResult, pager);
    }
}

