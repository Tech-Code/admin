package com.techapi.bus;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class BusConstants {

	public static final String LOG_PERFORMANCE = "performance";
	
	public static final DateTimeFormatter FORMATTER_DATE = DateTimeFormat.forPattern("yyyy-MM-dd");
    public static final DateTimeFormatter FORMATTER_TIME = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter FORMATTER_TIMESTAMP = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss,SSS");
}
