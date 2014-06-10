package com.techapi.bus.util;

import com.techapi.bus.entity.Poi;
import com.techapi.bus.entity.PoiType;
import com.techapi.bus.entity.Station;

import java.util.*;

/**
 * Created by xuefei on 6/8/14.
 */
public class ImportPoi {
    public static List<Poi> importPoi() {
        List<Poi> poiList = new ArrayList<>();

        // 读站点信息表  Map<cityName,List<StationObject>>
        Map<String,List<Station>> cityStationMap = FileUtils.getStationData("/Users/xuefei/Documents/MyDocument/Fun/bus/站点数据-20140609/text");

        // 读取poi类别表
        Map<String,PoiType> poiTypeMap = FileUtils.getPoiType("/Users/xuefei/Documents/MyDocument/Fun/bus/GIS地标分类表-typemap.csv");

        Iterator cityNameIterator = cityStationMap.keySet().iterator();
        while(cityNameIterator.hasNext()) {
            String cityName = cityNameIterator.next().toString();
            System.out.println("BEGIN -- CityName: " + cityName);
            System.out.println("********************************");
            List<Station> stationList = cityStationMap.get(cityName);
            int lineCount = 1;
            for (Station station : stationList) {

                System.out.println("BEGIN -- CityName: " + cityName + ",StationName: " + station.getStationName() + "行数: " + lineCount++);
                System.out.println("--------------------------------");

                Map<String,String> paraMap = new HashMap<>();

                paraMap.put("submit_form","poi_search");
                paraMap.put("query_type","RQBXY");
                paraMap.put("data_type","POI");
                paraMap.put("page_num","5");
                paraMap.put("page","1");
                paraMap.put("x", station.getStationLon());
                paraMap.put("y",station.getStationLat());
                paraMap.put("range","1000");
                paraMap.put("custom_and","false");
                paraMap.put("sort_rule","0");
                paraMap.put("geotype","rectangle");
                paraMap.put("display_type","1");
                // 调用接口 cityCode为三位，按照cityCode处理
                String response = HttpUtils.URLGet("http://221.180.144.45:9092/CMPOISearch2/lnm_sisserver.php?",paraMap,"UTF-8");
                // 解析json/xml
                Map result = XMLUtils.readStringXmlOut(response);


                List<Map<String,String>> poilist = (List)result.get("poilist");
                for (Map<String,String> poiMap : poilist) {

//                    System.out.println("BEGIN -- CityName: " + cityName + ",StationName: " + station.getStationName() + ",PoiName:" + poiMap.get("name").toString());
//                    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");


                    String poiid = poiMap.get("poiid").toString();
                    String name = poiMap.get("name").toString();
                    String otype = poiMap.get("otype").toString();
                    String stype = poiMap.get("stype").toString();
                    String atype = poiMap.get("atype").toString();
                    String x = poiMap.get("x").toString();
                    String y = poiMap.get("y").toString();
                    String adminCode = poiMap.get("adminCode").toString();
                    String provinceName = poiMap.get("provinceName").toString();
                    String _cityName = poiMap.get("cityName").toString();
                    //String cityCode = poiMap.get("cityCode").toString();
                    String adminName = poiMap.get("adminName").toString();
                    String distance = poiMap.get("distance").toString();

                    otype = otype.toUpperCase().replace("0X", "");
                    stype = stype.toUpperCase().replace("0X", "");
                    atype = atype.toUpperCase().replace("0X", "");
                    if(otype.length() == 1) {
                        otype += "0";
                    }
                    if(stype.length() == 1) {
                        stype += "0";
                    }

                    String poiType1 = otype + stype + atype;

                    PoiType poiType = poiTypeMap.get(poiType1);


                    Poi poi = new Poi();
                    poi.setCitycode(adminCode);
                    poi.setStationid(station.getStationId());
                    poi.setPoiid(poiid);
                    poi.setPoiname(name);
                    poi.setPoitype1(poiType1);
                    if(poiType != null) {
                        poi.setPoitype2(poiType.getPoiType2());
                        poi.setPoitype3(poiType.getPoiType3());
                    } else {
                        poi.setPoitype2("");
                        poi.setPoitype3("");
                    }

                    poi.setPoicoordinate(x + "," + y);
                    poi.setWalkdistance(distance);
                    poi.setOrientation("");
                    poiList.add(poi);

//                    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                }
                System.out.println("--------------------------------");
            }

            System.out.println("********************************");
        }

        return poiList;
    }

    public static void main(String[] args) {
        List<Poi> poiList = importPoi();
        System.out.println(poiList.size());
    }
}
