package com.techapi.bus.dao;

import com.techapi.bus.entity.Taxi;
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
public class TaxiDaoTest {

	@Resource
    private TaxiDao dao;
	@Test
	public void testFindBycityName() {
		dao.findBycityName("北京市");
	}

    @Test
    @Rollback(false)
    public void importTaxiData() {
        File file = new File("/Users/xuefei/Desktop/BUS_TAXI.csv");

        try {
            List<String> lines = org.apache.commons.io.FileUtils.readLines(file);
            List<Taxi> taxiList = new ArrayList<>();
            for (String line : lines) {
                System.out.println("line: " + line);
                String[] taxiData = line.split(",");
                Taxi taxi = new Taxi();
                taxi.setCityCode(taxiData[0]);
                taxi.setCityName(taxiData[1]);
                taxi.setD_timesection(taxiData[2]);
                taxi.setD_s_miles(Integer.parseInt(taxiData[3]));
                taxi.setD_s_cost(Double.parseDouble(taxiData[4]));

                taxi.setD_exceed_s_cost(Double.parseDouble(taxiData[5]));
                taxi.setD_s_exceed_d_cost(Double.parseDouble(taxiData[6]));
                taxi.setN_timesection(taxiData[7]);
                taxi.setN_s_miles(Integer.parseInt(taxiData[8]));
                taxi.setN_s_cost(Double.parseDouble(taxiData[9]));
                taxi.setN_exceed_s_cost(Double.parseDouble(taxiData[10]));
                taxi.setN_s_exceed_d_cost(Double.parseDouble(taxiData[11]));
                taxi.setD_bas(Double.parseDouble(taxiData[12]));
                taxi.setN_bas(Double.parseDouble(taxiData[13]));
                taxi.setD_exceed_distance(Double.parseDouble(taxiData[14]));
                taxi.setN_exceed_distance(Double.parseDouble(taxiData[15]));
                taxiList.add(taxi);
            }

            dao.save(taxiList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
