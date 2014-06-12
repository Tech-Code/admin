package com.techapi.bus.service;

import com.techapi.bus.dao.CityStationDao;
import com.techapi.bus.entity.CityStation;
import com.techapi.bus.util.PageUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author hongjia.hu
 * @date 2014-3-8
 */
@Service
public class CityStationService {

	@Resource
	private CityStationDao cityStationDao;

    public void addOrUpdate(CityStation cityStation) {
        // search data row by cityCode
//        if (!StringUtils.isEmpty(cityStation.getCityCode())) {
//            List<CityStation> cityStationList = cityStationDao.findByCityCode(cityStation.getCityCode());
//            if (cityStationList.size() > 0) {
//
//            }
//        }
        cityStationDao.save(cityStation);
    }

	/**
	 * @return
	 */
	public Map<String,Object> findAll() {
        //cityStationDao.updateCityStation("010","北京");
		List<CityStation>cityStationList = (List<CityStation>)cityStationDao.findAll();
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