package com.techapi.bus.data;

import com.techapi.bus.BusConstants;
import com.techapi.bus.annotation.CacheProxy;
import com.techapi.bus.dao.PoiDao;
import com.techapi.bus.entity.Poi;
import com.techapi.bus.util.TTL;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FlushCacheService {

    @Resource
    private PoiDao poiDao;

    @Qualifier("cacheProxy")
    @Autowired
    private CacheProxy cacheProxy;

    protected static Logger log = Logger.getLogger(FlushCacheService.class);

    public void flushPoi(String cityCode) {
        log.info("开始读取数据库");
        int rows = 300;
        if(cityCode != null && !cityCode.isEmpty()) {
            int poiTotalCount = poiDao.findCountByCityCode(cityCode + "%");
            log.info("CITYCODE:" + cityCode + ",PAGECOUNT:" + poiTotalCount + "ROWS:" + rows);
            int pageCount = poiTotalCount / rows;
            if(poiTotalCount % rows > 0) {
                pageCount += 1;
            }
            for(int page = 0; page < pageCount ; page++) {
                log.info("获取第" + (page+1) + "页开始");
                List<Poi> subPoiList = poiDao.findByCityCode(page * rows, (page + 1)*rows);
                log.info("获取第" + (page + 1) + "页结束");
                // 刷新redis
                log.info("刷新第" + (page + 1) + "页redis开始");
                for(Poi poi : subPoiList) {
                    String poicache = String.format(BusConstants.BUS_GRID_POI,
                                                    poi.getGridId(),poi.getPoiId());
                    if (poi != null) {
                        //补cache
                        cacheProxy.put(poicache, poi);
                    } else {
                        //增加null，防止击穿cache，压力数据库
                        cacheProxy.put(poicache, new Poi(), TTL._10M.getTime());
                    }
                }
                log.info("刷新第" + (page + 1) + "页redis结束");
            }



        }
	}
}
