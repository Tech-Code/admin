package com.techapi.bus.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author hongjia.hu
 * @date 2014-2-16
 */
public class PageUtils {

	/**
	 * @param list
	 * @return
	 */
	public static Map<String, Object> getPageMap(List<?> list) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", list == null ? 0 : list.size());
		result.put("rows", list);

		return result;
	}
}
