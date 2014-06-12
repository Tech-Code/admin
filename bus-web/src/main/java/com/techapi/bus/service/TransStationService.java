package com.techapi.bus.service;

import com.techapi.bus.dao.TransstationDao;
import com.techapi.bus.entity.Transstation;
import com.techapi.bus.util.PageUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author Andrew.xue
 * @date 2014-3-8
 */
@Service
public class TransStationService {

	@Resource
	private TransstationDao transStationDao;

    public void addOrUpdate(Transstation transstation) {
        // search data row by cityCode
        transStationDao.save(transstation);
    }

	/**
	 * @return
	 */
	public Map<String,Object> findAll() {
        //cityStationDao.updateCityStation("010","北京");
		List<Transstation>transStationList = (List<Transstation>) transStationDao.findAll();
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
        //transStationDao.updateCityStation("010","北京");
        //transStationDao.save();
    }

    public void deleteOne(String id) {
        transStationDao.delete(id);
    }

    public void deleteMany(List<Transstation> transStationList) {
        transStationDao.delete(transStationList);
    }

}