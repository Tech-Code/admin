package com.techapi.bus.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuefei on 7/11/14.
 */
public class PropertyMapUtils {

    private static Map<String, String> poiTypeMap = new HashMap<>();
    private static Map<String, String> poiTypeNameMap = new HashMap<>();
    private static Map<String, String> businessTypeMap = new HashMap<>();


    public static String getPoiTypeName(String poiType) {
        getPoiTypeMap();
        String poiTypeFormal = poiTypeMap.get(poiType);
        if(poiTypeFormal.equals("飞机")) return "飞机场";
        if(poiTypeFormal.equals("火车")) return "火车站";
        if(poiTypeFormal.equals("轮渡")) return "港口码头";
        if(poiTypeFormal.equals("长途客车")) return "长途汽车站";
        return "";
    }

    /**
     *
     * @return PoiType,PoiTypeName
     */
    public static Map<String, String> getPoiTypeMap() {
        if (poiTypeMap.size() == 0) {
            String poiTransType = ConfigUtils.BUS_POI_TRANS_TYPE;
            String poiTransTypeName = ConfigUtils.BUS_POI_TRANS_TYPE_NAME;

            String[] poiTransTypes = poiTransType.split(",");
            String[] poiTransNames = poiTransTypeName.split(",");

            int poiTypeNameIndex = -1;
            int poiTypeIndex = 0;
            String poiType1 = "";
            while (poiTypeIndex < poiTransTypes.length) {
                String subPoiType = poiTransTypes[poiTypeIndex].substring(0, 4);
                if (!subPoiType.equals(poiType1)) {
                    poiTypeNameIndex++;
                    poiType1 = subPoiType;
                }

                poiTypeMap.put(poiTransTypes[poiTypeIndex++], poiTransNames[poiTypeNameIndex]);

            }
        }
        return poiTypeMap;
    }

    public static Map<String, String> getPoiTypeNameMap() {
        if (poiTypeNameMap.size() == 0) {
            String poiTransType = ConfigUtils.BUS_POI_TRANS_TYPE;
            String poiTransTypeName = ConfigUtils.BUS_POI_TRANS_TYPE_NAME;

            String[] poiTransTypes = poiTransType.split(",");
            String[] poiTransNames = poiTransTypeName.split(",");

            int poiTypeIndex = 0;
            int poiTypeNameIndex = 0;
            String preKey = "";
            while (poiTypeIndex < poiTransTypes.length) {
                if(poiTypeIndex == 0) {
                    poiTypeNameMap.put(poiTransNames[poiTypeNameIndex], poiTransTypes[poiTypeIndex]);
                    preKey = poiTransNames[poiTypeNameIndex];
                    poiTypeNameIndex++;
                } else {
                    String preValue = poiTypeNameMap.get(preKey);
                    if(preValue.contains(poiTransTypes[poiTypeIndex].substring(0, 4))) {
                        preValue += "," + poiTransTypes[poiTypeIndex];
                        poiTypeNameMap.put(preKey,preValue);
                    } else {
                        poiTypeNameMap.put(poiTransNames[poiTypeNameIndex], poiTransTypes[poiTypeIndex]);
                        preKey = poiTransNames[poiTypeNameIndex];
                        poiTypeNameIndex++;
                    }

                }
                poiTypeIndex++;
            }

        }
        return poiTypeNameMap;
    }

    public static Map<String, String> getBusinessTypeMap() {
        if (businessTypeMap.size() == 0) {
            String businessType = ConfigUtils.BUS_KEY_BUSINESS_TYPE;

            String[] businessTypes = businessType.split(",");

            for (String type : businessTypes) {
                businessTypeMap.put(type,type);
            }

        }
        return businessTypeMap;
    }

    public static void main(String[] args) {
        Map<String,String> map = PropertyMapUtils.getPoiTypeNameMap();
        Map<String,String> map1 = PropertyMapUtils.getPoiTypeMap();

        //System.out.println(PropertyMapUtils.getPoiTypeName("150300"));

        System.out.println("111");
    }


}
