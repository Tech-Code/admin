package com.techapi.bus.data;

import com.techapi.bus.dao.PoiStationDao;
import com.techapi.bus.entity.PoiStation;
import com.techapi.bus.solr.BusUpdate;
import com.techapi.bus.util.ConfigUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FlushSolrService {

    @Resource
    private PoiStationDao poiStationDao;

    protected static Logger log = Logger.getLogger(FlushSolrService.class);

    public void flushPoiStationToSolr(String adCode) {
        log.info("刷新solr开始");
        BusUpdate busUpdate = new BusUpdate();
        List<PoiStation> poiStationList;
        if(adCode != null && !adCode.isEmpty()) {
            int totalAdCodeCount = poiStationDao.findCountByAdCode(adCode);

            if (totalAdCodeCount <= ConfigUtils.BUS_POISTATION_FLUSHSOLR_ROWS) {
                poiStationList = poiStationDao.findByAdCode(adCode);
                busUpdate.updatePoiStations(poiStationList);
                log.info("AdCode: " + adCode + "已成功录入Solr");
                return;
            }
        }

        int totalCount;
        if (adCode != null && !adCode.isEmpty()) {
            totalCount = poiStationDao.findCountByAdCode(adCode);
        } else {
            totalCount = poiStationDao.findAllCount();
        }
        int rows = ConfigUtils.BUS_POISTATION_FLUSHSOLR_ROWS;
        int pageSize = totalCount / rows + 1;
        for (int page = 0; page < pageSize; page++) {
            log.info("正在录入第 " + (page + 1) + "页到Solr,每页: " + rows + "行");
            if (adCode != null && !adCode.isEmpty()) {
                poiStationList = poiStationDao.findByAdCodeByPage(page * rows,
                        (page + 1) * rows,adCode);
            } else {
                poiStationList = poiStationDao.findAllByPage(page * rows,
                        (page + 1) * rows);
            }
            busUpdate.updatePoiStations(poiStationList);
            //log.info("录入第 " + (page + 1) + "页到Solr,每页: " + rows + "行完毕");
        }
	}
}
