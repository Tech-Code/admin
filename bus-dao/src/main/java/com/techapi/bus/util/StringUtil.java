package com.techapi.bus.util;

import org.springframework.util.StringUtils;

/**
 * Created by xuefei on 6/11/14.
 */
public class StringUtil {
    public static String getString(Object str) {
        if(StringUtils.isEmpty(str)) {
            return "";
        }
        return str.toString();
    }

    public static boolean isNotEmpty(String id) {
        if(id != null && !id.isEmpty()) return true;
        return false;
    }
}
