package com.techapi.bus.util;

import com.techapi.bus.entity.PoiType;
import com.techapi.bus.entity.Station;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by xuefei on 6/9/14.
 */
public class FileUtils {
    public static Map<String,List<Station>> getStationData(String filePath) {
        File fileDir = new File(filePath);
        File[] files = fileDir.listFiles();

        Map<String,List<Station>> cityStationMap = new HashMap<>();
        try {
            for (File file : files) {
                if(file.isHidden()) continue;
                String fileName = file.getName();
                fileName = fileName.substring(0,fileName.lastIndexOf("."));
                List<Station> stationList = new ArrayList<>();
                List<String> lines;

                lines = org.apache.commons.io.FileUtils.readLines(file);
                for (String line : lines) {
                    String[] stationData = line.split(",");
                    Station station = new Station();
                    station.setStationId(stationData[0]);
                    station.setStationName(stationData[1]);
                    station.setStationLat(stationData[3]);
                    station.setStationLon(stationData[2]);
                    stationList.add(station);
                }
                cityStationMap.put(fileName,stationList);
            }
        } catch(IOException e){
            e.printStackTrace();
        }

        return cityStationMap;
    }

    public static Map<String,PoiType> getPoiType(String filePath) {
        File file = new File(filePath);
        Map<String,PoiType> poiTypeMap = new HashMap<>();
        try {
            List<String> lines = org.apache.commons.io.FileUtils.readLines(file,"UTF-8");
            for (String line : lines) {
                String[] poiTypes = line.split(",");
                PoiType poiType = new PoiType();
                poiType.setPoiType1(poiTypes[1]);
                poiType.setPoiType2(poiTypes[2]);
                poiType.setPoiType3(poiTypes[3]);
                poiTypeMap.put(poiTypes[0],poiType);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return poiTypeMap;
    }

    public static List<?> splitListWithStep(List<?> stationList, int start, int step) {

        if (stationList == null || stationList.isEmpty()) {
            return null;
        }
        int startIndex = start;
        int endIndex = start + step;
        if (startIndex > endIndex || startIndex > stationList.size()) {
            return null;
        }
        if (endIndex > stationList.size()) {
            endIndex = stationList.size();
        }

        return stationList.subList(startIndex,endIndex);

    }
    public static void main(String[] args) {
        Map<String,List<Station>> map = FileUtils.getStationData("/Users/xuefei/Documents/MyDocument/Fun/bus/站点数据-20140609/text");
        System.out.println(map.size());

//        Map<String,PoiType> map1 = FileUtils.getPoiType("/Users/xuefei/Documents/MyDocument/Fun/bus/GIS地标分类表-typemap.csv");
//        System.out.println(map1.size());
    }
}
