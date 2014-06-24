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

	static {
		try {
			ResourceBundle rb = ResourceBundle.getBundle("config");

            SOLR_URL = rb.getString("solr.url");

            BUS_DATA = rb.getString("bus.data");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
