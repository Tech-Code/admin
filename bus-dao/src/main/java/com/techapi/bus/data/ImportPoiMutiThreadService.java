package com.techapi.bus.data;

import com.techapi.bus.BusConstants;
import com.techapi.bus.annotation.CacheProxy;
import com.techapi.bus.dao.PoiDao;
import com.techapi.bus.entity.Poi;
import com.techapi.bus.entity.PoiType;
import com.techapi.bus.entity.Station;
import com.techapi.bus.util.FileUtils;
import com.techapi.bus.util.ImportUtils;
import com.techapi.bus.util.TTL;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ImportPoiMutiThreadService {

    @Resource
    private PoiDao poiDao;

    @Qualifier("cacheProxy")
    @Autowired
    private CacheProxy cacheProxy;

    protected static Logger log = Logger.getLogger(ImportPoiMutiThreadService.class);

    private static Map<String, Set<String>> cityPoiIdMap = new HashMap<>();

    public int importPoi(String cityName, Map<String, List<Station>> cityStationMap, Map<String, PoiType> poiTypeMap) {
        int iStartLine = 0;
        Set<String> poiIdSet = poiDao.findPoiIdListByCityName(cityName.trim());
        int insertRows = 0;
        log.debug("BEGIN -- CityName: " + cityName);
        log.debug("********************************");
        List<Station> stationList = cityStationMap.get(cityName);
        while (iStartLine < stationList.size()) {
            List<Station> subStationList = FileUtils.splitListWithStep(stationList, iStartLine, 100);

            if (subStationList != null) {
                Map<String, Map<String, List<Poi>>> stationIdPoiListMap = ImportUtils.getStationIdPoiListMap(cityName, subStationList, poiTypeMap, iStartLine, poiIdSet);
                Iterator stationIdIterator = stationIdPoiListMap.keySet().iterator();
                while (stationIdIterator.hasNext()) {
                    String stationId = stationIdIterator.next().toString();
                    Map<String, List<Poi>> stationPoiListMap = stationIdPoiListMap.get(stationId);
                    List<Poi> outSidePoiList = stationPoiListMap.get("Outside");
                    List<Poi> inSidePoiList = stationPoiListMap.get("Inside");
                    for (Poi outSidePoi : outSidePoiList) {
                        Poi existed = poiDao.findByPoiId(outSidePoi.getPoiId());
                        if (existed == null) {
                            inSidePoiList.add(outSidePoi);
                        }
                    }
                    poiDao.save(inSidePoiList);
                    insertRows += inSidePoiList.size();
                }
            }
            iStartLine += 100;
        }
        log.debug("********************************");
        return insertRows;
    }

    public void insertRedis(List<Poi> poiList) {
        int index = BusConstants.REDIS_INDEX_POI;
        for (Poi poi : poiList) {
            String poiCache = String.format(BusConstants.BUS_GRID_POI, poi.getGridId(), poi.getPoiId());
            Object o = cacheProxy.get(poiCache, index);

            if (o == null) {
                if (poi != null) {
                    //补cache
                    cacheProxy.put(poiCache, poi, -1, index);
                } else {
                    //增加null，防止击穿cache，压力数据库
                    cacheProxy.put(poiCache, new Poi(), TTL._10M.getTime());
                }
            }
        }
    }
}
