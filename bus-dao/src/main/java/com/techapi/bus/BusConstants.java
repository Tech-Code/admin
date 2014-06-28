package com.techapi.bus;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public final class BusConstants {

	public static final String LOG_PERFORMANCE = "performance";
	
	public static final DateTimeFormatter FORMATTER_DATE = DateTimeFormat.forPattern("yyyy-MM-dd");
    public static final DateTimeFormatter FORMATTER_TIME = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter FORMATTER_TIMESTAMP = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss,SSS");
    /**
     * 时速表无City默认值
     */
    public static final String DEFAULT_CITYNAME="默认";
    /***
     * poi站点缓存key
     */
    public static final String BUS_POI_STATIONID="bus:poi:%s";

    /** 车速缓存key */
    public static final String BUS_SPEED_TYPE_CODE="bus:speed:%s";

    /** 出租车缓存key */
    public static final String BUS_TAXI_CITYCODE="bus:taxi:%s";

    /**
     * 用户key缓存key
     */
    public static final String BUS_CTL_KEY = "bus:ctl:%s";

    /**
     * 交通工具类型
     */
    public static final String TRANSPORTATION_TYPE_BUS = "公交";

    public static final String TRANSPORTATION_TYPE_SUBWAY = "地铁";

    public static final String TRANSPORTATION_TYPE_TAXI = "出租车";

    /** 返回状态标识 */
    public static final String RESULT_SUCCESS = "0";
    public static final String RESULT_REPEAT = "1"; // 重复记录 是否覆盖
    public static final String RESULT_ERROR = "2";
    public static final String RESULT_NOTEXIST = "3";
    public static final String RESULT_REPEAT_STATION = "4"; // 站点重复记录

    /**
     * 返回状态文字
     */
    public static final String RESULT_SUCCESS_STR = "操作成功!";
    public static final String RESULT_REPEAT_STR = "信息存在!"; // 重复记录
    public static final String RESULT_ERROR_STR = "操作失败!";
    public static final String RESULT_NOTEXIST_STR = "站点不存在!";
    public static final String RESULT_REPEAT_STATION_STR = "站点在记录中已存在!";


    /** redis Index */
    public static final int REDIS_INDEX_KEY = 2;

}
