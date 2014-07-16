package com.techapi.bus.solr.result;

import com.techapi.bus.entity.PoiStation;

import java.util.List;

/**
 * Created by xuefei on 7/9/14.
 */
public class PoiStationResult {
    private long total;

    private List<PoiStation> list;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<PoiStation> getList() {
        return list;
    }

    public void setList(List<PoiStation> list) {
        this.list = list;
    }
}
