package com.techapi.bus.service;

import com.techapi.bus.dao.TaxiDao;
import com.techapi.bus.entity.Taxi;
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
public class TaxiService {

	@Resource
	private TaxiDao taxiDao;

    public void addOrUpdate(Taxi taxi) {
        taxiDao.save(taxi);
    }

	/**
	 * @return
	 */
	public Map<String,Object> findAll() {
		List<Taxi>taxiList = (List<Taxi>)taxiDao.findAll();
        return PageUtils.getPageMap(taxiList);
	}

    /**
     * @return
     */
    public Taxi findById(String id) { Taxi taxi = taxiDao.findOne(id); return taxi; }

    public List<Taxi> findByIds(List<String> ids) {
        return (List<Taxi>)taxiDao.findAll(ids);
    }

    public void deleteOne(String id) {
        taxiDao.delete(id);
    }

    public void deleteMany(List<Taxi> taxiList) {
        taxiDao.delete(taxiList);
    }

}