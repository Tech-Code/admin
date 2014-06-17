package com.techapi.bus.service;

import com.techapi.bus.dao.PoiDao;
import com.techapi.bus.entity.Poi;
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
public class PoiService {

	@Resource
	private PoiDao poiDao;

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
        Poi poi = poiDao.findOne(id);
        return poi;
    }

    public List<Poi> findByIds(List<String> ids) {
        return (List<Poi>) poiDao.findAll(ids);
    }

    public void update(String id) {
        //PoiDao.updatePoi("010","北京");
        //PoiDao.save();
    }

    public void deleteOne(String id) {
        poiDao.delete(id);
    }

    public void deleteMany(List<Poi> poiList) {
        poiDao.delete(poiList);
    }

}