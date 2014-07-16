package com.techapi.bus.dao;

import com.techapi.bus.entity.PoiStation;
import com.techapi.bus.entity.PoiStationPK;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/applicationContext-bus-dao-oracle.xml")
@Transactional()
public class PoiStationDaoTest {
    @Resource
    private PoiStationDao dao;

    @Test
    @Rollback(false)
    public void testFindBystationID() {
        PoiStation poiStation = new PoiStation();
        poiStation.setAdName("秦皇岛市");
        poiStation.setPoiCoordinate("119.722415,39.967053");
        poiStation.setAdCodeForSolr("130300");
        poiStation.setPoiStationName("秦皇岛山海关机场（停车场）");
        poiStation.setPoiStationPK(new PoiStationPK("1008059502", "130300"));
        poiStation.setPoiType("150100");

        dao.save(poiStation);

    }

}