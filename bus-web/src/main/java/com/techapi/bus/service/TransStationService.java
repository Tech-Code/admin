package com.techapi.bus.service;

import com.techapi.bus.BusConstants;
import com.techapi.bus.dao.CityStationDao;
import com.techapi.bus.dao.TransstationDao;
import com.techapi.bus.entity.CityStation;
import com.techapi.bus.entity.Transstation;
import com.techapi.bus.solr.BaseQuery;
import com.techapi.bus.util.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author Andrew.xue
 * @date 2014-3-8
 */
@Service
public class TransStationService extends BaseQuery {

	@Resource
	private TransstationDao transStationDao;

    @Resource
    private CityStationDao cityStationDao;

    public Map<String, String> addOrUpdate(Transstation transstation) {
        Map<String, String> resultMap = new HashMap<>();
        // 判断站点是否存在
        // 直接手动填写了不存在的站点
        CityStation filledCityStation = transstation.getCityStation();
        if (filledCityStation == null ||
                filledCityStation.getId() == null ||
                filledCityStation.getId().isEmpty()) {
            resultMap.put("result", BusConstants.RESULT_NOTEXIST);
            resultMap.put("alertInfo", BusConstants.RESULT_NOTEXIST_STR);
            return resultMap;
        }

        // 站点选择，后期修改为不存在站点
        CityStation selectedCityStation = cityStationDao.findOne(filledCityStation.getId());
        if(!selectedCityStation.getStationName().equals(filledCityStation.getStationName())) {
            resultMap.put("result", BusConstants.RESULT_NOTEXIST);
            resultMap.put("alertInfo", BusConstants.RESULT_NOTEXIST_STR);
            return resultMap;
        }

        String id = transstation.getId();

        CityStation cityStation = cityStationDao.findOne(transstation.getCityStation().getId());

        List<Transstation> transStationList = transStationDao.findByTripsAndStationName(transstation.getTrips(), cityStation.getStationName());

        // 新增
        if (id == null || id.isEmpty()) {
           if (transStationList.size() > 0) {
                resultMap.put("result", BusConstants.RESULT_REPEAT);
                resultMap.put("alertInfo", BusConstants.RESULT_REPEAT_STR);
                return resultMap;
            }
        } else {
            if (transStationList.size() > 0) {
                Transstation editTransstation = transStationDao.findOne(id);
                if (!editTransstation.getTrips().trim().equals(transStationList.get(0).getTrips().trim()) ||
                        !editTransstation.getCityStation().getStationName().equals(transStationList.get(0).getCityStation().getStationName())) {
                    resultMap.put("result", BusConstants.RESULT_REPEAT);
                    resultMap.put("alertInfo", BusConstants.RESULT_REPEAT_STR);
                    return resultMap;
                }
            }
        }
        transstation = transStationDao.save(transstation);

        resultMap.put("id", transstation.getId());
        resultMap.put("result", BusConstants.RESULT_SUCCESS);
        resultMap.put("alertInfo", BusConstants.RESULT_SUCCESS_STR);
        return resultMap;
    }

	/**
	 * @return
	 */
	public Map<String,Object> findAll() {
        //transStationDao.updateCityStation("010","北京");
		List<Transstation>transStationList = (List<Transstation>) transStationDao.findAll();
        return PageUtils.getPageMap(transStationList);
	}

    public Map<String, Object> findSection(int page, int rows) {
        Pageable pager = new PageRequest(page - 1, rows);
        Page<Transstation> transStationList = transStationDao.findAll(pager);

        return PageUtils.getPageMap(transStationList);
    }

    /**
     * @return
     */
    public Transstation findById(String id) {
        //cityStationDao.updateCityStation("010","北京");
        Transstation transStation = transStationDao.findOne(id);
        return transStation;
    }

    public List<Transstation> findByIds(List<String> ids) {
        return (List<Transstation>)transStationDao.findAll(ids);
    }

    public void update(String id) {
    }

    public void deleteOne(String id) {
        transStationDao.delete(id);
    }

    public void deleteMany(List<Transstation> transStationList) {
        transStationDao.delete(transStationList);
    }

    public List<CityStation> suggetList(String q) {
        List<CityStation> cityStationNameList = (ArrayList) queryBeans(q, 0, 10, CityStation.class);
        return cityStationNameList;
    }

    public List<Transstation> findTransstationByCityStationIds(List<String> cityStationIds) {
        String[] idArray = cityStationIds.toArray(new String[0]);
        if(idArray != null || idArray.length > 0) {
            return transStationDao.findTransstationByCityStationId(idArray);
        }
        return null;
    }
}