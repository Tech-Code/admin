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
	private PoiDao PoiDao;

    public void addOrUpdate(Poi poi) {
        PoiDao.save(poi);
    }

	/**
	 * @return
	 */
	public Map<String,Object> findAll() {
		List<Poi>poiList = (List<Poi>)PoiDao.findAll();
        return PageUtils.getPageMap(poiList);
	}

    /**
     * @return
     */
    public Poi findById(String id) {
        Poi poi = PoiDao.findOne(id);
        return poi;
    }

    public List<Poi> findByIds(List<String> ids) {
        return (List<Poi>)PoiDao.findAll(ids);
    }

    public void update(String id) {
        //PoiDao.updatePoi("010","北京");
        //PoiDao.save();
    }

    public void deleteOne(String id) {
        PoiDao.delete(id);
    }

    public void deleteMany(List<Poi> poiList) {
        PoiDao.delete(poiList);
    }

}