package com.techapi.bus.data;

import com.techapi.bus.dao.StationDao;
import com.techapi.bus.entity.Station;
import com.techapi.bus.util.ConfigUtils;
import com.techapi.bus.util.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class ImportStationService {

    @Resource
    private StationDao stationDao;

    protected static Logger log = Logger.getLogger(ImportStationService.class);

    public void importStation() {
        log.debug("--------------------开始导入POI--------------------");
        // 读站点信息表  Map<cityName,List<StationObject>>
        log.info("开始获取站点数据....");
        Map<String, List<Station>> cityStationMap = FileUtils.getStationData(ConfigUtils.BUS_STATION_DATA);
        log.info("获取站点数据完毕....");
        Iterator cityNameIterator = cityStationMap.keySet().iterator();
        log.info("站点数据入库开始....");
        while (cityNameIterator.hasNext()) {
            String cityName = cityNameIterator.next().toString();
            int start = 0;
            List<Station> stationList = cityStationMap.get(cityName);

            while (start < stationList.size()) {
                log.info("第" + start + "条站点数据");
                List<Station> subStationList = FileUtils.splitListWithStep(stationList, start, 100);
                // 存入站点信息
                stationDao.save(subStationList);
                start += 100;
            }
        }
        log.info("站点数据入库完毕....");

	}
}
