package com.techapi.bus.utils;

import com.techapi.bus.BusConstants;
import com.techapi.bus.annotation.CacheProxy;
import com.techapi.bus.entity.Poi;
import com.techapi.bus.entity.Speed;
import com.techapi.bus.entity.Station;
import com.techapi.bus.entity.UserKey;
import com.techapi.bus.util.ConfigUtils;
import com.techapi.bus.util.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

        String stationcache = String.format(BusConstants.BUS_GRID_POI, "110100013049001","");
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

    @Test
    @Rollback(false)
    public void testGetKeyFromRedis() {
        String keycache = String.format(BusConstants.BUS_CTL_KEY, "r00t4pb83k9t6g931uizluvnkemmlkk0v7mn3ek0zlas998foms0nf8isnhatzeth12zp0tbb23md7f7");
        Object o = cacheProxy.get(keycache,BusConstants.REDIS_INDEX_KEY);
        if (o != null) {
            UserKey userKey = (UserKey) o;
            System.out.println("userKey.toString():" + userKey.toString());
        } else {
            System.out.println("缓存中没有");
        }
    }

    @Test
    @Rollback(false)
    public void testDelPoiRedis() {
        Map<String, List<Station>> cityStationMap = FileUtils.getStationData(ConfigUtils.BUS_STATION_DATA);
        Iterator cityNameIterator = cityStationMap.keySet().iterator();
        while (cityNameIterator.hasNext()) {
            String cityName = cityNameIterator.next().toString();
            List<Station> stationList = cityStationMap.get(cityName);
            int count = 1;
            for(Station station : stationList) {
                String keycache = String.format(BusConstants.BUS_TEMP_POI, station.getStationId());
                Object o = cacheProxy.get(keycache);
                if(o != null) {
                    if(o instanceof Poi) {
                        if(((Poi) o).getPoiId() != null && !((Poi) o).getPoiId().isEmpty()) {
                            boolean result = cacheProxy.delete(keycache);
                            if (result) {
                                System.out.println("删除成功,第" + count + "个");
                            } else {
                                System.out.println("删除失败,key:" + keycache);
                            }
                        }
                    }
                    if(o instanceof ArrayList<?>) {
                        boolean result = cacheProxy.deleteList(keycache);
                        if (result) {
                            System.out.println("删除成功,第" + count + "个");
                        } else {
                            System.out.println("删除失败,key:" + keycache);
                        }
                    }

                } else {
                    System.out.println("无记录,第" + count + "个");
                }

                count++;
            }
        }

    }

}
