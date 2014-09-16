package com.techapi.bus.data;

import com.techapi.bus.BusConstants;
import com.techapi.bus.annotation.CacheProxy;
import com.techapi.bus.dao.PoiDao;
import com.techapi.bus.entity.Poi;
import com.techapi.bus.entity.PoiType;
import com.techapi.bus.entity.Station;
import com.techapi.bus.util.ConfigUtils;
import com.techapi.bus.util.FileUtils;
import com.techapi.bus.util.ImportUtils;
import com.techapi.bus.util.TTL;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ImportPoiService {

    @Resource
    private PoiDao poiDao;

    @Qualifier("cacheProxy")
    @Autowired
    private CacheProxy cacheProxy;

    protected static Logger log = Logger.getLogger(ImportPoiService.class);

    public void importPoi(String startCity, String startLine) {
        log.info("--------------------开始导入POI--------------------");
        // 读站点信息表  Map<cityName,List<StationObject>>
        log.info("开始获取站点数据....");
        Map<String, List<Station>> cityStationMap = FileUtils.getStationData(ConfigUtils.BUS_STATION_DATA);
        log.info("获取站点数据完毕....");
        // 读取poi类别表
        log.info("开始获取地标点类别....");
        Map<String, PoiType> poiTypeMap = FileUtils.getPoiType(ConfigUtils.BUS_POITYPE_DATA_CSV);
        log.info("获取地标点类别完毕....");
        Iterator cityNameIterator = cityStationMap.keySet().iterator();
        Iterator cityName1Iterator = cityStationMap.keySet().iterator();
        log.info("-----------城市列表顺序-----------");
        while (cityName1Iterator.hasNext()) {
            String cityName1 = cityName1Iterator.next().toString();
            log.info("cityName: " + cityName1);
        }
        log.info("开始获取站点周边POI....");
        boolean isSkip = true;
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        if(startCity.equals("")) {
            isSkip = false;
        }
        int iStartLine = 0;
        int totalInsertRows = 0;
        Map<String, Integer> cityNameInsertRowsMap = new HashMap<>();
        while (cityNameIterator.hasNext()) {
            String cityName = cityNameIterator.next().toString();

            if(isSkip) {
                if (cityName.equals(startCity)) {
                    isSkip = false;
                } else {
                    continue;
                }
            }

            Set<String> poiIdSet = poiDao.findPoiIdListByCityName(cityName.trim());
            int insertRows = 0;

            log.info("********************************");
            log.info("BEGIN -- CityName: " + cityName);
            List<Station> stationList = cityStationMap.get(cityName);
            while (iStartLine < stationList.size()) {
                List<Station> subStationList = (List<Station>)FileUtils.splitListWithStep(stationList, iStartLine, 100);

                if (subStationList != null) {
                    Map<String, Map<String,List<Poi>>> stationIdPoiListMap = ImportUtils.getStationIdPoiListMap(cityName, subStationList, poiTypeMap, iStartLine, poiIdSet);
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
            iStartLine = 0;
            log.info("1.数据库更新完毕.....");
            totalInsertRows += insertRows;
            cityNameInsertRowsMap.put(cityName, insertRows);
            // 删掉过期数据
            List<Poi> deletedList = poiDao.findByCityNameAndTimeStamp(cityName, format1.format(new Date()));
            int iDeleteStartLine = 0;
            while (iDeleteStartLine < deletedList.size()) {
                List<Poi> deletedSubList = (List<Poi>) FileUtils.splitListWithStep(deletedList, iDeleteStartLine, 1000);
                if(deletedSubList != null) {
                    poiDao.delete(deletedSubList);
                    iDeleteStartLine += 1000;
                }
            }
            log.info("1.数据库清理过期数据完毕.....");
            log.info("END -- CityName: " + cityName + " 插入条数: " + insertRows);
            log.info("********************************");
        }
        log.info("获取站点周边POI完毕....");
        log.info("总共插入条数: " + totalInsertRows);
        Iterator insertCityNameIterator = cityNameInsertRowsMap.keySet().iterator();
        while (insertCityNameIterator.hasNext()) {
            String insertCityName = insertCityNameIterator.next().toString();
            log.info("CityName: " + insertCityName + " 插入条数: " + cityNameInsertRowsMap.get(insertCityName));
        }

	}

    public void insertRedis(List<Poi> poiList) {
        int index = BusConstants.REDIS_INDEX_POI;
        for(Poi poi : poiList) {
            String poiCache = String.format(BusConstants.BUS_GRID_POI, poi.getGridId(),poi.getPoiId());
            Object o = cacheProxy.get(poiCache,index);

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
