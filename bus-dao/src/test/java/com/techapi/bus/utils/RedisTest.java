package com.techapi.bus.utils;

import com.techapi.bus.BusConstants;
import com.techapi.bus.annotation.CacheProxy;
import com.techapi.bus.entity.Poi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CH on 4/17/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/applicationContext-bus-dao-oracle.xml")
@Transactional
public class RedisTest {

    @Autowired
    public CacheProxy cacheProxy;

    @Test
    @Rollback(false)
    public void testGetPoiListFromRedis(){

        String stationcache = String.format(BusConstants.BUS_POI_STATIONID, "110100013049001");
        Object o = cacheProxy.get(stationcache);
        if (o != null) {
            List<Poi> poiList = (ArrayList)o;
            System.out.println("poiList.size():" + poiList.size());
        } else {
            System.out.println("缓存中没有");
        }
    }

}
