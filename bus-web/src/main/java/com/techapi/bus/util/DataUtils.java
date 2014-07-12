package com.techapi.bus.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
	
	private static SimpleDateFormat timeaF = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		// TODO Auto-generated method stub
		return sdf.format(date);
	}
	
	public static String parseEndTime(String minute,int step){
		String result=null;
		try {
			Date date = timeaF.parse(minute);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			switch(step){
				case 7:
					c.add(Calendar.MONTH, 1);
					Date date1 = c.getTime();
					String dd = timeaF.format(date1);
					result = dd.substring(0,7);
					break;
				case 10:
					c.add(Calendar.DAY_OF_MONTH, 1);
					Date date10 = c.getTime();
					String df10 = timeaF.format(date10);
					result = df10.substring(0,10);
					break;
				case 13:
					c.add(Calendar.HOUR_OF_DAY, 1);
					Date date13 = c.getTime();
					String df13 = timeaF.format(date13);
					result = df13.substring(0,13);
					break;
				case 16:
					c.add(Calendar.MINUTE, 1);
					Date date16 = c.getTime();
					String df16 = timeaF.format(date16);
					result = df16.substring(0,16);
					break;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
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
//		Date date = new Date(1395763199000L);
//		String value = timeF.format(date);
//		System.out.println(value);
		System.out.println(parseEndTime("2014-01-01 00:00:00",7));
		System.out.println(parseEndTime("2014-01-01 00:00:00",10));
		System.out.println(parseEndTime("2014-01-01 00:00:00",13));
		System.out.println(parseEndTime("2014-01-01 00:00:00",16));
	}
}
