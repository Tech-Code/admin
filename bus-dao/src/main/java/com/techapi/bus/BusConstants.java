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
    public static final String BUS_GRID_POI="bus:poi:%s:%s";

    public static final String BUS_TEMP_POI = "bus:poi:%s";

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

    public static final String TRANSPORTATION_TYPE_WALK = "步行";

    /** 返回状态标识 */
    public static final String RESULT_SUCCESS = "0";
    public static final String RESULT_REPEAT = "1"; // 重复记录 是否覆盖
    public static final String RESULT_ERROR = "2";
    public static final String RESULT_NOTEXIST = "3";
    public static final String RESULT_REPEAT_STATION = "4"; // 站点重复记录
    public static final String RESULT_REPEAT_POI = "5"; // 地标点重复记录
    public static final String RESULT_REPEAT_TAXI = "6"; // 出租车重复记录
    public static final String RESULT_REPEAT_SPEED = "7"; // 出租车重复记录
    public static final String RESULT_NO_EXIST_STATION = "8"; // 站点ID不存在
    /**
     * 返回状态文字
     */
    public static final String RESULT_SUCCESS_STR = "操作成功!";
    public static final String RESULT_REPEAT_STR = "信息存在!"; // 重复记录
    public static final String RESULT_ERROR_STR = "操作失败!";
    public static final String RESULT_NOTEXIST_STR = "站点不存在!";
    public static final String RESULT_REPEAT_STATION_STR = "站点在记录中已存在!";

    public static final String RESULT_REPEAT_POI_STR = "该地标点已存在!";
    public static final String RESULT_REPEAT_TAXI_STR = "城市费用已存在!";
    public static final String RESULT_REPEAT_SPEED_STR = "该城市车速已存在!";

    public static final String RESULT_NO_EXIST_STATION_STR = "站点ID不存在!";

    /** redis Index */
    public static final int REDIS_INDEX_KEY = 2;
    public static final int REDIS_INDEX_POI = 3;

    /** tools type */
    public static final String TOOLS_FLUSH_SOLR_POISTATION = "0";// 刷新POISTATION
    public static final String TOOLS_FLUSH_SOLR_CITYSTATION = "1";// 刷新CITYSTATION

    public static final int DELETE_ALL_USERKEY_STATUS = 0;
    public static final int DELETE_PART_USERKEY_STATUS = 1;
    public static final int DELETE_NONE_USERKEY_STATUS = 2;



}
