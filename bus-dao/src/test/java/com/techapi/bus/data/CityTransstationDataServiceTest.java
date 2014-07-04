package com.techapi.bus.data;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/applicationContext-bus-dao-oracle.xml")
@Transactional
public class CityTransstationDataServiceTest {

	@Autowired
	public CityTransstationDataService cityTransstationDataService;
	
	@Test
	public void testGetAllCityTransstation() {
		List<CityTransstationRelation> city = cityTransstationDataService.getAllCityTransstation();
		if(city!=null&&city.size()>0){
			System.out.println("this transtype:"+city.get(0).getTranstype()+" and count:"+city.get(0).getTransstationInformation().size());
		}
	}

}
