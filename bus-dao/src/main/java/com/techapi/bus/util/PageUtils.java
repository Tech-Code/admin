package com.techapi.bus.util;

import com.techapi.bus.entity.BasicEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuefei on 7/10/14.
 */
public class PageUtils {
    public static List<List<BasicEntity>> subListByPageRows(int rows, List<BasicEntity> originList) {
        List<List<BasicEntity>> subListByRows = new ArrayList<>();
        int totalSize = originList.size();
        int pageSize = totalSize / rows + 1;

        for (int page = 0; page < pageSize; page++) {
            List<BasicEntity> subPoiStationList = originList.subList(page * rows,
                    (page + 1) * rows - 1);
            subListByRows.add(subPoiStationList);
        }
        return subListByRows;
    }
}
