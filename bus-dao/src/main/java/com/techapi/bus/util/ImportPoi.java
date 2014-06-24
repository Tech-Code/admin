package com.techapi.bus.util;

import com.techapi.bus.data.ImportPoiService;
import com.techapi.bus.entity.Poi;
import com.techapi.bus.entity.PoiPK;
import com.techapi.bus.entity.PoiType;
import com.techapi.bus.entity.Station;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

/**
 * Created by xuefei on 6/8/14.
 */
public class ImportPoi {

    protected ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            "classpath:META-INF/applicationContext-bus-dao-oracle.xml");

    public ImportPoiService importPoiService = context
            .getBean(ImportPoiService.class);


    public static Map<String,List<Poi>> importPoi(String cityName,List<Station> stationList,
                                      Map<String, PoiType> poiTypeMap) {
        System.out.println("BEGIN -- CityName: " + cityName);
        System.out.println("********************************");
        int lineCount = 1;
        Map<String, List<Poi>> stationPoiListMap = new HashMap<>();
        for (Station station : stationList) {
            List<Poi> poiList = new ArrayList<>();
            System.out.println("BEGIN -- CityName: " + cityName + ",StationName: " + station.getStationName() + "行数: " + lineCount++);
            System.out.println("--------------------------------");
            for(int pageNum = 1; pageNum < 5; pageNum++) {
                System.out.println("第" + pageNum + "页");

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
                String response = HttpUtils.URLGet("http://221.180.144.45:9092/CMPOISearch2/lnm_sisserver.php?", paraMap, "UTF-8");
                // 解析json/xml
                Map result = XMLUtils.readPoiXMLToMap(response);


                List<Map<String, String>> poilistMap = (List) result.get("poilist");
                for (Map<String, String> poiMap : poilistMap) {

//                    System.out.println("BEGIN -- CityName: " + cityName + ",StationName: " + station.getStationName() + ",PoiName:" + poiMap.get("name").toString());
//                    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

                    if(poiList.size() == 5) break;
                    String poiid = poiMap.get("poiid").toString();
                    String name = poiMap.get("name").toString();
                    String otype = poiMap.get("otype").toString();
                    String stype = poiMap.get("stype").toString();
                    String atype = poiMap.get("atype").toString();
                    String x = poiMap.get("x").toString();
                    String y = poiMap.get("y").toString();
                    String adminCode = poiMap.get("adminCode").toString();
                    String distance = poiMap.get("distance").toString();
                    String address = StringUtil.getString(poiMap.get("address").toString());
                    String tel = StringUtil.getString(poiMap.get("telephone").toString());

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

                    PoiPK poiPK = new PoiPK();
                    poiPK.setStationId(station.getStationId());
                    poiPK.setPoiId(poiid);
                    poi.setPoiPK(poiPK);

                    poi.setPoiName(name);

                    if (poiType == null) {
                        continue;
                    }

                    poi.setPoiType1(poiType.getPoiType1());
                    poi.setPoiType2(poiType.getPoiType2());
                    poi.setPoiType3(poiType.getPoiType3());

                    poi.setPoiCoordinate(x + "," + y);
                    poi.setWalkDistance(Double.parseDouble(distance));
                    poi.setOrientation(MapUtil.getDirection(Double.parseDouble(station.getStationLon()),
                            Double.parseDouble(station.getStationLat()),
                            Double.parseDouble(x),
                            Double.parseDouble(y)));
                    poi.setAddress(address);
                    poi.setTel(tel);
                    poiList.add(poi);

                }
                if (poiList.size() == 5) {
                    break;
                }
            }
            stationPoiListMap.put(station.getStationId(), poiList);
            System.out.println("--------------------------------");
        }

        System.out.println("********************************");


        return stationPoiListMap;
    }

    public static void main(String[] args) {
        new ImportPoi().importPoiService.importPoi();
    }
}
