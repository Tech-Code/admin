package com.techapi.bus.util;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @Description:
 * @author hongjia.hu
 * @date 2014-3-13
 */
public class MapUtilsTest {
	private static double sLon = 116.39042;
	private static double sLat = 39.90461;
	private static double eLo = 116;
	private static double eLa = 39;

	/**
	 * @return
	 */
	public static double[] getRandom() {
		// TODO Auto-generated method stub
		double[] lonlat = new double[2];
		while (true) {
			double eLon = eLo + new Random().nextDouble();
			double eLat = eLa + new Random().nextDouble();

			double dis = getDistance(sLon, sLat, eLon, eLat);
			if (dis <= 5000) {
				BigDecimal bg = new BigDecimal(eLon);
				lonlat[0] = bg.setScale(6, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				BigDecimal bg1 = new BigDecimal(eLat);
				lonlat[1] = bg1.setScale(6, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				break;
			}
		}

		return lonlat;
	}

	public static double getDistance(double startLon, double startLat,
			double endLon, double endLat) {

		double radLat1 = startLat * Math.PI / 180;
		double radLat2 = endLat * Math.PI / 180;
		double a = radLat1 - radLat2;
		double b = startLon * Math.PI / 180 - endLon * Math.PI / 180;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * 6378137.0;//
		s = Math.round(s * 10000) / (double) 10000;
		return s;
	}

}
