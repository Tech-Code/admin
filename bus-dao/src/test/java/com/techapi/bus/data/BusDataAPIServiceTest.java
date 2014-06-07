package com.techapi.bus.data;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.techapi.bus.entity.Speed;
import com.techapi.bus.entity.Taxi;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/applicationContext-bus-dao-oracle.xml")
@Transactional
public class BusDataAPIServiceTest {
	@Resource
	public BusDataAPIService busDataAPIService;
	@Test
	public void testGetAllCityTransstation() {
		busDataAPIService.getAllCityTransstation();
	}
	/*
	@Test
	public void testFindOnePoiBystationID() {
		Poi p =busDataAPIService.findOnePoiBystationID("141203");
		System.out.println(p.getPoiname());
	}
	*/
	
	@Test
	public void testFindOneTaxiByCityName() {
		Taxi t = busDataAPIService.findOneTaxiByCityName("北京市");
		System.out.println(t.getCityname());
	}

	@Test
	public void testFindSpeedByCityTransportatio() {
		List<Speed> sp =busDataAPIService.findSpeedByCityTransportation("公交", "默认");
		System.out.println(sp.get(0).getSpeed());
	}

	@Test
	public void testFindOneSpeedByCityTransportatio() {
		Speed sp =busDataAPIService.findOneSpeedByCityTransportation("公交", "默认","");
	}

}
