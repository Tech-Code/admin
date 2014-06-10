package com.techapi.bus.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.techapi.bus.entity.Poi;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/applicationContext-bus-dao-oracle.xml")
@Transactional
public class PoiDaoTest {

	@Resource
    private PoiDao dao;
	@Test
	public void testFindBystationID() {
		List<Poi> list = dao.findBystationID("110100013427026");
		if(list != null){
			for(Poi poi:list){
				System.out.println(poi.getPoiName());
			}
		}
	}

}
