package com.techapi.bus.util;

import java.util.ResourceBundle;

/**
 * @Description: 配置信息常量类
 * @author hongjia.hu
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

    public static String BUS_POI_TRANS_TYPE;

    public static String BUS_POI_TRANS_TYPE_NAME;

    public static int BUS_POISTATION_FLUSHSOLR_ROWS;

    public static String BUS_KEY_BUSINESS_TYPE;




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

            BUS_POI_TRANS_TYPE = rb.getString("bus.data.poi.trans.type");

            BUS_POI_TRANS_TYPE_NAME = new String(rb.getString("bus.data.poi.trans.type.name").getBytes("ISO-8859-1"), "UTF-8");

            BUS_POISTATION_FLUSHSOLR_ROWS = Integer.parseInt(rb.getString("bus.data.poistation.flushsolr.rows"));

            BUS_KEY_BUSINESS_TYPE = new String(rb.getString("bus.data.key.business.type").getBytes("ISO-8859-1"), "UTF-8");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
