package com.techapi.bus.utils;

import com.techapi.bus.dao.PoiDao;
import com.techapi.bus.entity.Poi;
import com.techapi.bus.entity.PoiType;
import com.techapi.bus.entity.Station;
import com.techapi.bus.util.FileUtils;
import com.techapi.bus.util.ImportPoi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by CH on 4/17/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/applicationContext-bus-dao-oracle.xml")
@Transactional
public class ImportPoiTest {
	
    @Resource
    private PoiDao dao;
    
    @Test
    @Rollback(false)
    public void testImportPoi(){
        // 读站点信息表  Map<cityName,List<StationObject>>
        Map<String, List<Station>> cityStationMap = FileUtils.getStationData("/Users/xuefei/Documents/MyDocument/Fun/bus/站点数据-20140609/text");

        // 读取poi类别表
        Map<String, PoiType> poiTypeMap = FileUtils.getPoiType("/Users/xuefei/Documents/MyDocument/Fun/bus/GIS地标分类表-typemap.csv");

        Iterator cityNameIterator = cityStationMap.keySet().iterator();

        Map<String, List<Station>> subCityStationMap = new HashMap<>();
        while (cityNameIterator.hasNext()) {
            String cityName = cityNameIterator.next().toString();
            int start = 0;
            List<Station> stationList = cityStationMap.get(cityName);
            while (start < stationList.size()) {
                List<Station> subStationList = FileUtils.splitListWithStep(stationList, start, 100);
                if (subStationList != null) {
                    subCityStationMap.put(cityName, subStationList);
                    List<Poi> poiList = ImportPoi.importPoi(subCityStationMap, poiTypeMap);
                    dao.save(poiList);
                    start += 100;
                }
            }

        }


        System.out.println("33333");
        System.out.println("3");
        System.out.println("444444111111111");
        System.out.println("333333333111111111");
        System.out.println("1111");
        System.out.println("777777777777");



    }

}
