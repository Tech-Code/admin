package com.techapi.bus.utils;

import com.techapi.bus.BusConstants;
import com.techapi.bus.annotation.CacheProxy;
import com.techapi.bus.entity.Poi;
import com.techapi.bus.entity.Speed;
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

    @Test
    @Rollback(false)
    public void testGetSpeedFromRedis() {

        String speedcache = String.format(BusConstants.BUS_SPEED_TYPE_CODE, "1");
        Object o = cacheProxy.get(speedcache);
        if (o != null) {
            Speed speed = (Speed) o;
            System.out.println("speed.toString():" + speed.toString());
        } else {
            System.out.println("缓存中没有");
        }
    }

    @Test
    @Rollback(false)
    public void testRemoveKeyFromRedis() {

        String poicache = String.format(BusConstants.BUS_CTL_KEY, "2butatc35npio4m88x5sy067x1aael8q628beqgn1qlsotl27b70t19x46syrfg0lb08rf34i6tk255t");
        cacheProxy.delete(poicache, BusConstants.REDIS_INDEX_KEY);
    }

}
