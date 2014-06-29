package com.techapi.bus.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    public static Map<String, Object> getPageMap(Page<?> list) {
        // TODO Auto-generated method stub
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", list.getTotalElements());
        result.put("rows", list.getContent());
        return result;
    }
    
    /**
	 * @param list
	 * @return
	 */
	public static Map<String, Object> getPageMap(List<?> list,Pageable pageable) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", list == null ? 0 : list.size());
		if(list==null||list.size()==0){
			result.put("rows",list );
		}else{
			int start=(pageable.getPageNumber())*pageable.getPageSize();
			int end = (pageable.getPageNumber()+1)*pageable.getPageSize();
			if(end>list.size()){
				end = list.size();
			}
			System.out.println("start-------:"+start+"--------end---:"+end);
			result.put("rows",list.subList(start, end));
		}
		return result;
	}
    
    
}
