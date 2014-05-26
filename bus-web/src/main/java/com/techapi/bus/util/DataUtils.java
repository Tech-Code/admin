package com.techapi.bus.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:
 * @author hongjia.hu
 * @date 2014-3-14
 */
public class DataUtils {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

	private static SimpleDateFormat timeF = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");

	/**
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		// TODO Auto-generated method stub
		return sdf.format(date);
	}

	/**
	 * @param startTimeStr
	 * @param label
	 * @return
	 */
	public static long parseDate(String timeStr, boolean label) {
		// TODO Auto-generated method stub
		try {
			String append = "";
			if (label) {
				append = " 23:59:59";
			} else {
				append = " 00:00:00";
			}

			timeStr = timeStr + append;
			return timeF.parse(timeStr).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	public static void main(String[] args) {
		Date date = new Date(1395763199000L);
		String value = timeF.format(date);
		System.out.println(value);
	}
}
