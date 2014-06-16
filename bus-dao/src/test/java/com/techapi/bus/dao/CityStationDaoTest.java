package com.techapi.bus.dao;

import com.techapi.bus.entity.CityStation;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        System.out.println("ss");
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

    @Test
    @Rollback(false)
    public void importCityStationData() {
        File file = new File("/Users/xuefei/Desktop/BUS_CITYSTATION.csv");

        try {
            List<String> lines = org.apache.commons.io.FileUtils.readLines(file);
            List<CityStation> cityStationList = new ArrayList<>();
            for (String line : lines) {
                System.out.println("line: " + line);
                String[] cityStationData = line.split(",");
                CityStation cityStation = new CityStation();
                cityStation.setCityCode(cityStationData[0]);
                cityStation.setCityName(cityStationData[1]);
                cityStation.setTransType(cityStationData[2]);
                cityStation.setStationName(cityStationData[3]);
                cityStation.setTransdetail(cityStationData[4]);
                cityStation.setCoordinate((cityStationData[5]+ "," + cityStationData[6]).replace("\"",""));
                cityStationList.add(cityStation);
            }

            dao.save(cityStationList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
