package com.techapi.bus.data;

import com.techapi.bus.BusConstants;
import com.techapi.bus.annotation.CacheProxy;
import com.techapi.bus.dao.PoiDao;
import com.techapi.bus.entity.Poi;
import com.techapi.bus.entity.PoiType;
import com.techapi.bus.entity.Station;
import com.techapi.bus.util.*;
import com.techapi.bus.util.XMLUtils;
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

    public String importPoi(String cityName, Map<String, List<Station>> cityStationMap, Map<String, PoiType> poiTypeMap) {
        int iStartLine = 0;
        log.info("BEGIN -- CityName: " + cityName);
        log.info("********************************");
        List<Station> stationList = cityStationMap.get(cityName);
        while (iStartLine < stationList.size()) {
            List<Station> subStationList = FileUtils.splitListWithStep(stationList, iStartLine, 100);

            if (subStationList != null) {
                Map<String, List<Poi>> stationIdPoiListMap = getStationIdPoiListMap(cityName, subStationList, poiTypeMap, iStartLine);
                Iterator stationIdIterator = stationIdPoiListMap.keySet().iterator();
                while (stationIdIterator.hasNext()) {
                    String stationId = stationIdIterator.next().toString();
                    List<Poi> poiList = stationIdPoiListMap.get(stationId);
                    poiDao.save(poiList);
                }
            }
            iStartLine += 100;
        }
        log.info("********************************");
        return "0";
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

    public Map<String, List<Poi>> getStationIdPoiListMap(String cityName, List<Station> stationList,
                                                         Map<String, PoiType> poiTypeMap, int start) {
        int lineCount = 1;
        Map<String, List<Poi>> stationPoiListMap = new HashMap<>();
        for (Station station : stationList) {
            List<Poi> poiList = new ArrayList<>();
            log.info("BEGIN -- CityName: " + cityName + ",StationName: " + station.getStationName() + "行数: " + (start + lineCount++));
            log.info("--------------------------------");
            for (int pageNum = 1; pageNum < 5; pageNum++) {
                log.info("第" + pageNum + "页");

                Map<String, String> paraMap = new HashMap<>();

                paraMap.put("submit_form", "poi_search");
                paraMap.put("query_type", "RQBXY");
                paraMap.put("data_type", "POI");
                paraMap.put("page_num", "50");
                paraMap.put("page", Integer.toString(pageNum));
                paraMap.put("x", station.getStationLon());
                paraMap.put("y", station.getStationLat());
                paraMap.put("range", "1000");
                paraMap.put("custom_and", "false");
                paraMap.put("sort_rule", "0");
                paraMap.put("geotype", "rectangle");
                paraMap.put("display_type", "1");
                // 调用接口 cityCode为三位，按照cityCode处理
                String response = HttpUtils.URLGet(ConfigUtils.BUS_POI_SEARCH_URL, paraMap, "UTF-8");
                if (response == null || response.isEmpty()) {
                    continue;
                }

                // 解析json/xml
                Map result = XMLUtils.readPoiXMLToMap(response);

                List<Map<String, String>> poilistMap = (List) result.get("poilist");
                for (Map<String, String> poiMap : poilistMap) {
                    if (poiList.size() == 5) break;
                    String poiid = poiMap.get("poiid").toString();
                    String name = poiMap.get("name").toString();
                    String otype = poiMap.get("otype").toString();
                    String stype = poiMap.get("stype").toString();
                    String atype = poiMap.get("atype").toString();
                    String x = poiMap.get("x").toString();
                    String y = poiMap.get("y").toString();
                    String adminCode = poiMap.get("adminCode").toString();
                    String address = StringUtil.getString(poiMap.get("address").toString());
                    String tel = StringUtil.getString(poiMap.get("telephone").toString());
                    String cityname = StringUtil.getString(poiMap.get("cityName").toString());

                    otype = otype.toUpperCase().replace("0X", "");
                    stype = stype.toUpperCase().replace("0X", "");
                    atype = atype.toUpperCase().replace("0X", "");
                    if (otype.length() == 1) {
                        otype += "0";
                    }
                    if (stype.length() == 1) {
                        stype += "0";
                    }

                    String poiType1 = otype + stype + atype;

                    PoiType poiType = poiTypeMap.get(poiType1);


                    Poi poi = new Poi();
                    poi.setCityCode(adminCode);
                    poi.setCityName(cityname);
                    poi.setPoiId(poiid);

                    poi.setPoiName(name);

                    if (poiType == null) {
                        continue;
                    }

                    poi.setPoiType1(poiType.getPoiType1());
                    poi.setPoiType2(poiType.getPoiType2());
                    poi.setPoiType3(poiType.getPoiType3());

                    poi.setPoiCoordinate(x + "," + y);
                    poi.setAddress(address);
                    poi.setTel(tel);

                    // 添加网格计算
                    poi.setGridId(MapUtil.getGridId(x + "," + y));

                    // 添加通过POIID过滤
                    Set<String> poiIdSet = cityPoiIdMap.get(adminCode);
                    if (poiIdSet == null || poiIdSet.size() == 0) {
                        poiIdSet = poiDao.findPoiIdListByCityCode(adminCode);
                        cityPoiIdMap.put(adminCode, poiIdSet);
                    }
                    if (poiIdSet.add(poi.getPoiId())) {
                        poiList.add(poi);
                    }

                    poiList.add(poi);

                }
                if (poiList.size() == 5) {
                    break;
                }
            }
            log.info(station.getStationName() + "的poi条数: " + poiList.size());
            stationPoiListMap.put(station.getStationId(), poiList);
            log.info("--------------------------------");
        }


        return stationPoiListMap;
    }
}
