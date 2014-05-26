package com.techapi.bus.util;

import java.util.ResourceBundle;

/**
 * @Description: 配置信息常量类
 * @author hongjia.hu
 * @date 2014-2-13
 */
public class ConfigUtils {

	/**
	 * 关键字查询URL
	 */
	public static String QUERY_KEYWORD;

	/**
	 * 线路查询URL
	 */
	public static String QUERY_LINE;

	/**
	 * 城市ID
	 */
	private static String CITY_ID;
	
	/**
	 * 发布地址
	 */
	public static String BASE_URL;

	static {
		try {
			ResourceBundle rb = ResourceBundle.getBundle("config");
			CITY_ID = rb.getString("baidu.cityid");
			QUERY_KEYWORD = rb.getString("baidu.query.keyword").replace(
					"{cityid}", CITY_ID);
			QUERY_LINE = rb.getString("baidu.query.line").replace("{cityid}",
					CITY_ID);
			BASE_URL = rb.getString("sftp.url");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
