package com.techapi.bus.dao;

import com.techapi.bus.entity.Poi;
import com.techapi.bus.util.StringUtil;
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
public class PoiDaoTest {

	@Resource
    private PoiDao dao;
	@Test
	public void testFindBystationID() {
        // TODO 需要修改
		List<Poi> list = dao.findBystationID();
		if(list != null){
			for(Poi poi:list){
				System.out.println(poi.getPoiName());
			}
		}
	}

    @Test
    public void testFindAll() {
        List<Poi> list = (List<Poi>)dao.findAll();
        System.out.println("list.size: " + list.size());
    }

    @Test
    @Rollback(false)
    public void importPoiData() {
        File file = new File("/Users/xuefei/Desktop/BUS_POI.csv");

        try {
            List<String> lines = org.apache.commons.io.FileUtils.readLines(file);
            List<Poi> poiList = new ArrayList<>();
            for (String line : lines) {
                System.out.println("line: " + line);
                String[] poiData = line.split(",");
                Poi poi = new Poi();
                poi.setCityCode(poiData[0]);

                poi.setPoiId(poiData[2]);

                poi.setPoiName(poiData[3]);
                poi.setPoiType1(poiData[4]);
                poi.setPoiType2(poiData[5]);
                poi.setPoiType3(poiData[6]);
                poi.setPoiCoordinate((poiData[7] + "," + poiData[8]).replace("\"", ""));
                // TODO
                poi.setAddress(StringUtil.getString(poiData[9]));
                poi.setTel(StringUtil.getString(poiData[10]));

                poiList.add(poi);
            }

            dao.save(poiList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
