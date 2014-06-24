package com.techapi.bus.util;

import java.util.ResourceBundle;

public class ConfigUtils {

	public static String SOLR_URL;

	public static String BUS_DATA;

	static {
		ResourceBundle rb = ResourceBundle.getBundle("config");

		try {
			SOLR_URL = rb.getString("solr.url");
		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			BUS_DATA = rb.getString("bus.data");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
