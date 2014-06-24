package com.techapi.bus.data;

import com.techapi.bus.BusConstants;
import com.techapi.bus.annotation.CacheProxy;
import com.techapi.bus.dao.PoiDao;
import com.techapi.bus.entity.Poi;
import com.techapi.bus.entity.PoiType;
import com.techapi.bus.entity.Station;
import com.techapi.bus.util.FileUtils;
import com.techapi.bus.util.ImportPoi;
import com.techapi.bus.util.TTL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ImportPoiService {

    @Resource
    private PoiDao dao;

    @Autowired
    private CacheProxy cacheProxy;

    public void importPoi() {
        // 读站点信息表  Map<cityName,List<StationObject>>
        Map<String, List<Station>> cityStationMap = FileUtils.getStationData("/Users/xuefei/Documents/MyDocument/Fun/bus/站点数据-20140609/text");

        // 读取poi类别表
        Map<String, PoiType> poiTypeMap = FileUtils.getPoiType("/Users/xuefei/Documents/MyDocument/Fun/bus/GIS地标分类表-typemap.csv");

        Iterator cityNameIterator = cityStationMap.keySet().iterator();

        while (cityNameIterator.hasNext()) {
            String cityName = cityNameIterator.next().toString();
            int start = 0;
            List<Station> stationList = cityStationMap.get(cityName);

            while (start < stationList.size()) {
                List<Station> subStationList = FileUtils.splitListWithStep(stationList, start, 10);
                if (subStationList != null) {
                    Map<String, List<Poi>> stationIdPoiListMap = ImportPoi.importPoi(cityName, subStationList, poiTypeMap);
                    Iterator stationIdIterator = stationIdPoiListMap.keySet().iterator();
                    while (stationIdIterator.hasNext()) {
                        String stationId = stationIdIterator.next().toString();
                        List<Poi> poiList = stationIdPoiListMap.get(stationId);
                        String stationcache = String.format(BusConstants.BUS_POI_STATIONID, stationId);
                        Object o = cacheProxy.get(stationcache);
                        if (o == null) {
                            if (poiList != null) {
                                //补cache
                                cacheProxy.put(stationcache, poiList);
                            } else {
                                //增加null，防止击穿cache，压力数据库
                                cacheProxy.put(stationcache, new ArrayList<Poi>(), TTL._10M.getTime());
                            }
                        }
                        dao.save(poiList);

                    }
                }
                start += 10;
                break;
            }
        }


	}
}