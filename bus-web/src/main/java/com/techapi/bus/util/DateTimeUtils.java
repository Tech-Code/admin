package com.techapi.bus.util;

/**
 * Created by xuefei on 7/12/14.
 */
public class DateTimeUtils {

    public static int formatFromTimeToInt(String time) {
        String[] timeArray = time.split(":");

        String hour = timeArray[0];
        String minutes = timeArray[1];

        int iHour = Integer.parseInt(hour);
        int iMinutes = Integer.parseInt(minutes);

        return iHour * 60 + iMinutes;
    }

    public static String formatFromIntToTime(int time) {
        int iHour = time / 60;
        int iMinutes = time % 60;


        return iHour + ":" + iMinutes;
    }
}
