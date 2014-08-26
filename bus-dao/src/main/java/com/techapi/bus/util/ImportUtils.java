package com.techapi.bus.util;

import com.techapi.bus.entity.Poi;
import com.techapi.bus.entity.PoiType;
import com.techapi.bus.entity.Station;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by xuefei on 8/23/14.
 */
public class ImportUtils {
    protected static Logger log = Logger.getLogger(ImportUtils.class);

    public static Map<String, Map<String,List<Poi>>> getStationIdPoiListMap(String cityName, List<Station> stationList,
                                                         Map<String, PoiType> poiTypeMap, int start, Set<String> poiIdSet) {
        int lineCount = 1;
        Map<String, Map<String, List<Poi>>> stationPoiListMap = new HashMap<>();
        for (Station station : stationList) {
            int poiListCount = 0; // 请求的产生的poi列表
            List<Poi> notExistPoiList = new ArrayList<>(); // 去掉重复的poi列表
            List<Poi> outOfCityPoiList = new ArrayList<>(); // 本城市之外poi列表
            log.debug("BEGIN -- CityName: " + cityName + ",StationName: " + station.getStationName() + "行数: " + (start + lineCount++));
            log.debug("--------------------------------");
            for (int pageNum = 1; pageNum < 5; pageNum++) {
                log.debug("第" + pageNum + "页");

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
                if(poilistMap == null) continue;
                for (Map<String, String> poiMap : poilistMap) {
                    if (poiListCount == 5) break;
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
                    String realCityName = StringUtil.getString(poiMap.get("cityName").toString());

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
                    poi.setCityName(realCityName);
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
                    if (poiIdSet.add(poi.getPoiId())) {
                        if (!realCityName.equals(cityName)) {
                            outOfCityPoiList.add(poi);
                        } else {
                            notExistPoiList.add(poi);
                        }
                    }

                    poiListCount++;

                }
                if (poiListCount == 5) {
                    break;
                }
            }
            log.debug(station.getStationName() + "的poi条数: " + notExistPoiList.size());
            Map<String,List<Poi>> allPoiMap = new HashMap<>();
            allPoiMap.put("Inside", notExistPoiList);
            allPoiMap.put("Outside", outOfCityPoiList);
            stationPoiListMap.put(station.getStationId(), allPoiMap);
            log.debug("--------------------------------");
        }
        return stationPoiListMap;
    }
}
