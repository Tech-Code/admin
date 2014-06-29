package com.techapi.bus.service;

import com.techapi.bus.BusConstants;
import com.techapi.bus.annotation.CacheProxy;
import com.techapi.bus.dao.TaxiDao;
import com.techapi.bus.entity.Taxi;
import com.techapi.bus.util.PageUtils;
import com.techapi.bus.util.TTL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author fei.xue
 * @date 2014-3-8
 */
@Service
public class TaxiService {

	@Resource
	private TaxiDao taxiDao;

    @Autowired
    private CacheProxy cacheProxy;

    public void addOrUpdate(Taxi taxi) {
        String taxicache = String.format(BusConstants.BUS_TAXI_CITYCODE, taxi.getCityCode());
        if (taxi != null) {
            //补cache
            cacheProxy.put(taxicache, taxi);
        } else {
            //增加null，防止击穿cache，压力数据库
            cacheProxy.put(taxicache, new Taxi(), TTL._10M.getTime());
        }
        taxiDao.save(taxi);
    }

	/**
	 * @return
	 */
	public Map<String,Object> findAll() {
		List<Taxi>taxiList = (List<Taxi>)taxiDao.findAll();
        return PageUtils.getPageMap(taxiList);
	}

    public Map<String, Object> findSection(int page, int rows) {
        Pageable pager = new PageRequest(page - 1, rows);
        Page<Taxi> taxiList = taxiDao.findAll(pager);

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
        for (Taxi taxi : taxiList) {
            if (taxi != null) {
                String taxicache = String.format(BusConstants.BUS_TAXI_CITYCODE, taxi.getCityCode());
                cacheProxy.delete(taxicache);
            }
        }
        taxiDao.delete(taxiList);
    }

}