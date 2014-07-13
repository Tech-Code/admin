package com.techapi.bus.solr;

import com.techapi.bus.solr.query.QueryForBus;
import com.techapi.bus.solr.result.PoiStationResult;
import junit.framework.TestCase;

public class BusSearchTest extends TestCase {

    public void testSearchPoiStationList() throws Exception {
        QueryForBus queryForBus = new QueryForBus();
        queryForBus.setPoiType("150400");
        queryForBus.setAdName("海北藏族自治州");
        queryForBus.setPoiStationName("海晏西海汽车站");

        BusSearch busSearch = new BusSearch(queryForBus);
        PoiStationResult poiStationResult = busSearch.searchPoiStationList();
        System.out.println("poiStationResult.size:" + poiStationResult.getTotal());
    }


}