package com.techapi.bus.service;

import com.techapi.bus.dao.SpeedDao;
import com.techapi.bus.entity.Speed;
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
public class SpeedService {

	@Resource
	private SpeedDao speedDao;

    public void addOrUpdate(Speed speed) {
        speedDao.save(speed);
    }

	/**
	 * @return
	 */
	public Map<String,Object> findAll() {
		List<Speed>speedList = (List<Speed>) speedDao.findAll();
        return PageUtils.getPageMap(speedList);
	}

    /**
     * @return
     */
    public Speed findById(String id) {
        Speed speed = speedDao.findOne(id);
        return speed;
    }

    public List<Speed> findByIds(List<String> ids) {
        return (List<Speed>) speedDao.findAll(ids);
    }

    public void update(String id) {
        //SpeedDao.updateSpeed("010","北京");
        //SpeedDao.save();
    }

    public void deleteOne(String id) {
        speedDao.delete(id);
    }

    public void deleteMany(List<Speed> speedList) {
        speedDao.delete(speedList);
    }

}