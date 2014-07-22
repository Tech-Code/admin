package com.techapi.bus.util;

import com.techapi.bus.data.MapService;
import com.techapi.bus.entity.Grid;
import com.techapi.bus.entity.LatLng;

import java.awt.*;

public class MapUtil {

	//静态常量，地球半径，来源：《大气科学常用公式》，P601，附录
    public static double RADIUS         = 6371.004;//地球平均半径，单位：公里(Km)。
    public static double RADIUS_POLAR   = 6356.755;//地球两极半径，单位：公里(Km)。
    public static double RADIUS_EQUATOR = 6373.140;//地球赤道半径，单位：公里(Km)。
    
    private static double[] LON_STEPS = {90.0D, 40.0D, 20.0D, 10.0D, 5.0D, 2.0D, 1.0D, 0.5D, 0.2D, 0.1D, 0.05D, 0.02D, 0.01D, 0.005D};
    private static double[] LAT_STEPS = {90.0D, 32.0D, 16.0D, 8.0D, 4.0D, 1.6D, 0.8D, 0.4D, 0.16D, 0.08000000000000002D, 0.04000000000000001D, 0.016D, 0.008D, 0.004D};
    private static int IMAGE_WIDTH = 300;
    private static int IMAGE_HEIGHT = 300;
    
    private static double  kmPerDegreeX    = 0.0;      //1经度对应的距离(公里)，不同纬度数值不同
    private static double  kmPerDegreeY    = 0.0;      //1纬度对应的距离(公里)，不同纬度数值不同
    private static double  cosineElevation = 1.0;      //仰角的余弦值

       /**
     * 计算地图显示级别
     *
     * @param width  地图宽度
     * @param height 地图高度
     * @param minLat 最小纬度
     * @param minLon 最小经度
     * @param maxLat 最大纬度
     * @param maxLon 最大经度
     * @return 地图显示级别
     */
    public static int getLevel(int width, int height, double minLat, double minLon, double maxLat, double maxLon) {
        double unitLat = 1.1D * (maxLat - minLat) * IMAGE_HEIGHT / height;
        double unitLon = 1.1D * (maxLon - minLon) * IMAGE_WIDTH / width;
        int zmLat = getFitLevel(unitLat, LAT_STEPS);
        int zmLon = getFitLevel(unitLon, LON_STEPS);
        int zmFit = Math.min(zmLat, zmLon);
        return zmFit;
    }

    private static int getFitLevel(double dValue, double[] arrayLevels) {
        int iLevel = 1;
        int levelLen = (arrayLevels == null ? 0 : arrayLevels.length);
        while ((iLevel < levelLen) && (dValue <= arrayLevels[iLevel])) {
            ++iLevel;
        }
        return (iLevel - 1);
    }

    /**
     * 计算地图显示中心点经纬度
     *
     * @param minLat 最小纬度
     * @param minLon 最小经度
     * @param maxLat 最大纬度
     * @param maxLon 最大经度
     * @return 中心点经纬度 double[lon,lat]
     */
    public static double[] getCenter(double minLat, double minLon, double maxLat, double maxLon) {
        double centerlat = 0, centerlon = 0;
        if (minLat > 0 && minLon > 0 && maxLat > 0 && maxLon > 0) {
            centerlat = (minLat + maxLat) / 2;
            centerlon = (minLon + maxLon) / 2;
        }
        double[] center = {centerlon, centerlat};
        return center;
    }

    /**
     * 求两个将经纬度的直线距离,单位M
     * @param startLon 开始点经度
     * @param startLat 开始点纬度
     * @param endLon   结束点经度
     * @param endLat   结束点纬度
     * @return
     */
    public static double getDistance(double startLon, double startLat,double endLon,double endLat) {

        double radLat1 = startLat * Math.PI / 180; 
        double radLat2 = endLat * Math.PI / 180; 
        double a = radLat1 - radLat2; 
        double b = startLon * Math.PI / 180 - endLon * Math.PI / 180; 
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2))); 
        s = s * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m) 
        s = Math.round(s * 10000) / 10000; 
        return s; 

    }
 
    
    /**
      *
      *         扫描平面
      *            /
      *           /
      *          /
      *         /
      *        /
      *       /  仰角
      *      -------------------- 0度平面
      *
      * 如图所示：
      *          扫描平面=>0度平面，需要乘以cos(仰角)
      *          0度平面=>扫描平面，需要除以cos(仰角)
      *
      * 注意，日常显示的雷达图是扫描平面上的图。本类所说的屏幕指扫描平面。
      *
      */
    /**
      * 雷达扫描示意图
      *
      *                    359 0
      *                        |     radius
      *                        |       /
      *                        |      /
      *                        |angle/
      *                        |    /
      *                        | ^ /
      *                        |  /
      *                        | /
      *                        |/
      * 270 -----------------中心----------------- 90
      *                        |
      *                        |
      *                        |
      *                        |
      *                        |
      *                        |
      *                        |
      *                        |
      *                        |
      *                       180
    /**
     * 求终点在起点的方位
     * @param startLon 开始点经度
     * @param startLat 开始点纬度
     * @param endLon   结束点经度
     * @param endLat   结束点纬度
     * @return
     */
    public static String getDirection(double startLon, double startLat,double endLon,double endLat) {
//        double dis = getDistance(startLon, startLat,endLon,endLat);
        
        double agl=0.0;
        if(startLon==endLon&&startLat==endLat)
        	return "正中";
        else if(endLon==startLon)
        	agl=endLat>startLat?360.0:180.0;
        else if(endLat==startLat)
            agl=endLon>startLon?90.0:270.0;
        else{
        	//中心经纬度或仰角发生改变，必须重新计算经向和纬向的1度对应的球面距离
        	kmPerDegreeX    = distanceOfSphere(startLon, startLat, startLon+1.0, startLat) / cosineElevation;
        	kmPerDegreeY    = distanceOfSphere(startLon, startLat, startLon, startLat+1.0)/ cosineElevation;
        	//注：由于经向和纬向的球面距离不等,(华南,经向>纬向),
        	//故点(1,1)和中心点(0,0)的极角不等于45度，而是比45度偏大
        	agl = Math.toDegrees(Math.atan((Math.abs(endLon-startLon)*kmPerDegreeX)/(Math.abs(endLat-startLat)*kmPerDegreeY)));
        	            
        	agl =  endLon > startLon && endLat > startLat ? agl :           //第一象限
        	       endLon < startLon && endLat > startLat ? 360.0 - agl :   //第二象限
        	       endLon < startLon && endLat < startLat ? 180.0 + agl :   //第三象限
        	       endLon > startLon && endLat < startLat ? 180.0 - agl :   //第四象限
        	       agl;
        
        }
//        System.out.println("agl:--"+agl);       
        if(agl<22.5||agl>337.5)
        	return "North";
        else if(agl>=22.5&&agl<=67.5)
        	return "EastNorth";
        else if(agl>67.5&&agl<112.5)
        	return "East";
        else if(agl>=112.5&&agl<=157.5)
        	return "EastSouth";
        else if(agl>157.5&&agl<202.5)
        	return "South";
        else if(agl>=202.5&&agl<=247.5)
        	return "WestSouth";
        else if(agl>247.5&&agl<292.5)
        	return "West";
        else
        	return "WestNorth";

    }
    
    /**
     * 功能：计算球面上两点间的距离(单位：公里)
     * 参数：
     *  lon1,lat1   - 第1点的位置(经纬度)
     *  lon2,lat2   - 第2点的位置(经纬度)
     * 返回值：
     *      球面距离
     */
        public static double distanceOfSphere(double lon1, double lat1, double lon2, double lat2) {
            /*  公式：
                A(x,y)  B(a,b)
                AB点的球面距离=R*{arccos[cos(b)*cos(y)*cos(a-x)+sin(b)*sin(y)]}, by Google
            */

            double  rlon1   = Math.toRadians(lon1);
            double  rlat1   = Math.toRadians(lat1);
            double  rlon2   = Math.toRadians(lon2);
            double  rlat2   = Math.toRadians(lat2);

            return(RADIUS*(Math.acos(Math.cos(rlat2)*Math.cos(rlat1)*Math.cos(rlon2-rlon1)+Math.sin(rlat2)*Math.sin(rlat1))));
        }
        
        public static Point pointInSeg(Point ptStart, Point ptEnd, Point ptFrom) {

    		Point v = new Point(ptEnd.x - ptStart.x, ptEnd.y - ptStart.y);
    		Point w = new Point(ptFrom.x - ptStart.x, ptFrom.y - ptStart.y);
    		long c1 = (long) w.x * v.x + (long) w.y * v.y;

    		if (c1 == 0) {
    			// ptFrom Ƿ start end ķ Χ
    			if (ptStart.x <= ptFrom.x && ptStart.y >= ptFrom.y
    					&& ptEnd.x >= ptFrom.x && ptEnd.y <= ptFrom.y

    			) {
    				return ptFrom;
    			}
    		}

    		// in this case, two vector angle is larger than 80 degree,
    		// point projection is outside the line, return the free point to the
    		// start point as the minimum
    		if (c1 <= 0) {
    			// return ptStart;
    			return null;
    		}
    		// this is square of the length of the segment
    		long c2 = (long) v.x * v.x + (long) v.y * v.y;
    		// if the segment length is shorter, the ending point to the free point
    		// will be the minimum
    		if (c2 <= c1) {
    			// return ptEnd;
    			return null;
    		}
    		// here is the most ugly part, need vertical projection first
    		Point ptProject = new Point((int) (ptStart.x + v.x * c1 / c2),
    				(int) (ptStart.y + v.y * c1 / c2));
    		// then return the distance of the projection
    		return ptProject;
    	}

    	/**
    	 * @param p
    	 * @return
    	 */
    	public static String getStrPoint(Point p) {
    		double[] xy = { p.x / 100000D, p.y / 100000D };
    		return xy[0] + "," + xy[1];
    	}

        public static String getGridId(String coordinate) {
            String[] lonlat = coordinate.split(",");
            Grid grid = MapService.getGirdByLatLng(new LatLng(Float.parseFloat(lonlat[1]), Float.parseFloat(lonlat[0])));
            if(grid != null) {
                return grid.getId();
            }
            return "";
        }

}
