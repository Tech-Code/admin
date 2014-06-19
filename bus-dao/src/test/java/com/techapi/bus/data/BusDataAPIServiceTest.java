package com.techapi.bus.data;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.techapi.bus.entity.Poi;
import com.techapi.bus.entity.Speed;
import com.techapi.bus.entity.Taxi;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/applicationContext-bus-dao-oracle.xml")
@Transactional
public class BusDataAPIServiceTest {
	@Resource
	public BusDataAPIService busDataAPIService;
	@Test
	@Ignore
	public void testGetAllCityTransstation() {
		busDataAPIService.getAllCityTransstation();
	}
	
	@Test
	@Ignore
	public void testFindOnePoiBystationID() {
		Poi p =busDataAPIService.findOnePoiBystationID("530800010014027");
		System.out.println(p.getPoiName());
	}
	
	
	@Test
	@Ignore
	public void testFindOneTaxiByCityName() {
		Taxi t = busDataAPIService.findOneTaxiByCityName("北京市");
		System.out.println("test");
//		System.out.println(t.getCityName());
	}

	@Test
	@Ignore
	public void testFindSpeedByCityTransportatio() {
		List<Speed> sp =busDataAPIService.findSpeedByCityTransportation("公交", "默认");
		System.out.println(sp.get(0).getSpeed());
	}

	@Test
	@Ignore
	public void testFindOneSpeedByCityTransportatio() {
		Speed sp =busDataAPIService.findOneSpeedByCityTransportation("公交", "默认","");
	}
	@Test
	public void testfindPoiBystationIDList(){
		Map<String,List<Poi>> mp = busDataAPIService.findPoiBystationIDList("530800010013001", "530800010014027","530800010009012");
		for(java.util.Map.Entry<String, List<Poi>> e:mp.entrySet()){
			System.out.println(e.getKey());
		}
	}

}
