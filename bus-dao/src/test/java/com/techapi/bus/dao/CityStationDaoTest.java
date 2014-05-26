package com.techapi.bus.dao;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    public void testList() {
        Iterable<CityStation> ecs = dao.findByCityCode("010");
        Assert.assertNotNull(ecs);
        for (CityStation ec : ecs) {
            System.out.println(ec);
        }
    }

}
