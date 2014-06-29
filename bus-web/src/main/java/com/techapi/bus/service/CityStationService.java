package com.techapi.bus.service;

import com.techapi.bus.BusConstants;
import com.techapi.bus.dao.CityStationDao;
import com.techapi.bus.entity.CityStation;
import com.techapi.bus.solr.BaseQuery;
import com.techapi.bus.util.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author hongjia.hu
 * @date 2014-3-8
 */
@Service
public class CityStationService extends BaseQuery{

	@Resource
	private CityStationDao cityStationDao;

    public Map<String, String> addOrUpdate(CityStation cityStation) {
        Map<String, String> resultMap = new HashMap<>();
        String id = cityStation.getId();
        List<CityStation> cityStationList = cityStationDao.findByStationName(cityStation.getStationName());
        if (id == null || id.isEmpty()) {
            if (cityStationList.size() > 0) {
                resultMap.put("id", cityStationList.get(0).getId());
                resultMap.put("result", BusConstants.RESULT_REPEAT);
                resultMap.put("alertInfo", BusConstants.RESULT_REPEAT_STR);
                return resultMap;
            }
        } else {
            if (cityStationList.size() > 0) {
                resultMap.put("result", BusConstants.RESULT_REPEAT_STATION);
                resultMap.put("alertInfo", BusConstants.RESULT_REPEAT_STATION_STR);
                return resultMap;
            }
        }
        cityStation = cityStationDao.save(cityStation);

        resultMap.put("id", cityStation.getId());
        resultMap.put("result", BusConstants.RESULT_SUCCESS);
        resultMap.put("alertInfo", BusConstants.RESULT_SUCCESS_STR);
        updateBean(cityStation);
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

    public void update(String id) {
        //cityStationDao.updateCityStation("010","北京");
        //cityStationDao.save();
    }

    public void deleteOne(String id) {
        cityStationDao.delete(id);
    }

    public void deleteMany(List<CityStation> cityStationList) {
        cityStationDao.delete(cityStationList);
    }

}