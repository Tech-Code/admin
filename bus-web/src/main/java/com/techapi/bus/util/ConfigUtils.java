package com.techapi.bus.util;

import java.util.ResourceBundle;

/**
 * @author hongjia.hu
 * @Description: 配置信息常量类
 * @date 2014-2-13
 */
public class ConfigUtils {

    public static String SOLR_URL;

    public static String BUS_DATA;

    public static String BUS_STATION_DATA;

    public static String BUS_KEY_DATA;

    public static String BUS_POITYPE_DATA_XLS;

    public static String BUS_POITYPE_DATA_CSV;

    public static String BUS_POI_SEARCH_URL;


    static {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("config");

            SOLR_URL = rb.getString("solr.url");

            BUS_DATA = rb.getString("bus.data");

            BUS_STATION_DATA = rb.getString("bus.data.station");

            BUS_KEY_DATA = rb.getString("bus.data.key");

            BUS_POITYPE_DATA_XLS = rb.getString("bus.data.poitype.xls");

            BUS_POITYPE_DATA_CSV = rb.getString("bus.data.poitype.csv");

            BUS_POI_SEARCH_URL = rb.getString("bus.data.poi.search.url");


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
