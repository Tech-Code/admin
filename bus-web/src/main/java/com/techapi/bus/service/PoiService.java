package com.techapi.bus.service;

import com.techapi.bus.annotation.CacheProxy;
import com.techapi.bus.annotation.ServiceCache;
import com.techapi.bus.dao.PoiDao;
import com.techapi.bus.entity.Poi;
import com.techapi.bus.util.PageUtils;
import com.techapi.bus.util.TTL;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PoiService {

	@Resource
	private PoiDao poiDao;
    @Autowired
    public CacheProxy cacheProxy;

    @ServiceCache(TTL._30M)
    public void addOrUpdate(Poi poi) {
        poiDao.save(poi);
    }

	/**
	 * @return
	 */
	public Map<String,Object> findAll() {
		List<Poi>poiList = (List<Poi>) poiDao.findAll();
        return PageUtils.getPageMap(poiList);
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
        poiDao.delete(poiList);

    }

}