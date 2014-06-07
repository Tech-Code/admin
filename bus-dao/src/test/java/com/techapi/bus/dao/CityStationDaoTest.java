package com.techapi.bus.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.techapi.bus.entity.CityStation;

/**
 * Created by CH on 4/17/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/applicationContext-bus-dao-oracle.xml")
@Transactional
public class CityStationDaoTest {
	
    @Resource
    private CityStationDao dao;
    
    @Test
    public void testfindCityAndTransstation(){
    	List<Object[]> o =dao.findCityAndTransstation();
        System.out.println(o.get(0));
    }

    @Test
    public void testFindByCityCode() {
        Iterable<CityStation> ecs = dao.findByCityCode("010");
        Assert.assertNotNull(ecs);
        for (CityStation ec : ecs) {
            System.out.println(ec);
        }
    }

    @Test
    public void testFindAll() {
        Iterable<CityStation> ecs = dao.findAll();
        Assert.assertNotNull(ecs);
        for (CityStation ec : ecs) {
            System.out.println(ec);
        }
    }

    @Test
    @Rollback(false)
    public void testUpdateCityStation() {
        int result = dao.updateCityStation("010","北京");
        //CityStation cityStation = new CityStation();
        //cityStation.setCityCode("010");
        //cityStation.setCityName("天津");
        //cityStation.setCityCode("010");

        //dao.save(cityStation);

        //dao.deleteAll();

        System.out.println("result:" + result);
    }

    @Test
    @Rollback(false)
    public void testAddCityStation() {
        CityStation cityStation = new CityStation();
        cityStation.setCityCode("121");
        cityStation.setCityName("上海");
        cityStation.setStationName("111");
        cityStation.setCoordinate("32.232,112.1212");
//        cityStation.setId("1111");
        cityStation.setTransdetail("111");
        cityStation.setTransType("11");
        dao.save(cityStation);
    }

}
