package com.techapi.bus.service;

import com.techapi.bus.BusConstants;
import com.techapi.bus.annotation.CacheProxy;
import com.techapi.bus.dao.SpeedDao;
import com.techapi.bus.entity.Speed;
import com.techapi.bus.util.PageUtils;
import com.techapi.bus.util.TTL;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SpeedService {

	@Resource
	private SpeedDao speedDao;

    @Autowired
    private CacheProxy cacheProxy;

    public Map<String,String> addOrUpdate(Speed speed) {
        Map<String,String> resultMap = new HashMap<>();
        try {
            String id = speed.getId();
            // 新增模式
            int speedType = speed.getTransportType();
            if (speedType == 0) speed.setTranSportation(BusConstants.TRANSPORTATION_TYPE_SUBWAY);
            if (speedType == 1) speed.setTranSportation(BusConstants.TRANSPORTATION_TYPE_BUS);
            if (speedType == 2) speed.setTranSportation(BusConstants.TRANSPORTATION_TYPE_TAXI);
            if(id == null || id.isEmpty()) {
                List<Speed> speedList = speedDao.findByTransportationAndDetailAndCityName(speed.getTranSportation(),
                        speed.getCityName(),
                        speed.getTranSportDes());
                if (speedList.size() > 0) {
                    resultMap.put("id", speedList.get(0).getId());
                    resultMap.put("result", BusConstants.RESULT_REPEAT);
                    resultMap.put("alertInfo", BusConstants.RESULT_REPEAT_STR);
                    return resultMap;
                }
            }

            String speedcache = String.format(BusConstants.BUS_SPEED_TYPE_CODE, speedType);
            if (speed != null) {
                //补cache
                cacheProxy.put(speedcache, speed);
            } else {
                //增加null，防止击穿cache，压力数据库
                cacheProxy.put(speedcache, new Speed(), TTL._10M.getTime());
            }

            Speed savedSpeed = speedDao.save(speed);
            resultMap.put("id", savedSpeed.getId());
            resultMap.put("result", BusConstants.RESULT_SUCCESS);
            resultMap.put("alertInfo", BusConstants.RESULT_SUCCESS_STR);
            return resultMap;
        } catch(Exception ex) {
            ex.printStackTrace();
            resultMap.put("id", "");
            resultMap.put("result", BusConstants.RESULT_ERROR);
            resultMap.put("alertInfo", BusConstants.RESULT_ERROR_STR);
            return resultMap;
        }

    }

	/**
	 * @return
	 */
	public Map<String,Object> findAll() {
		List<Speed>speedList = (List<Speed>) speedDao.findAll();
        return PageUtils.getPageMap(speedList);
	}

    public Map<String, Object> findSection(int page, int rows) {
        Pageable pager = new PageRequest(page - 1, rows);
        Page<Speed> speedList = speedDao.findAll(pager);

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