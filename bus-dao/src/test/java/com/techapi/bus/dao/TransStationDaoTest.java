package com.techapi.bus.dao;

import com.techapi.bus.entity.Transstation;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/applicationContext-bus-dao-oracle.xml")
@Transactional
public class TransStationDaoTest {

	@Resource
    private TransstationDao dao;
	@Test
	public void testFindAll() {
		List<Transstation> transstationList = (List<Transstation>)dao.findAll();
        System.out.println(transstationList.size());
    }

    @Test
    @Rollback(false)
    public void importTransstationData() {
        File file = new File("/Users/xuefei/Desktop/BUS_TRANSSTATION.csv");

        try {
            List<String> lines = org.apache.commons.io.FileUtils.readLines(file);
            List<Transstation> transstationList = new ArrayList<>();
            for (String line : lines) {
                System.out.println("line: " + line);
                String[] transstationData = line.split(",");
                Transstation transstation = new Transstation();
                transstation.setTransType(transstationData[0]);
                transstation.setTrips(transstationData[1]);
                transstation.setTransDetail(transstationData[2]);
                transstation.setCityCode(transstationData[3]);
                transstation.setStation((transstationData[4]));

                transstation.setStationOrder(Integer.parseInt(transstationData[5]));
                transstation.setCoordinate((transstationData[6] + "," + transstationData[7]).replace("\"", ""));
                transstation.setArriveTime(transstationData[8]);
                transstation.setDepartTime(transstationData[9]);
                transstation.setMiles(Double.parseDouble(transstationData[10]));
                transstation.setPrice(Double.parseDouble(transstationData[11]));
                transstationList.add(transstation);
            }

            dao.save(transstationList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
