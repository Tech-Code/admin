package com.techapi.bus.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Categories.IncludeCategory;
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
	public void testGetAllCityTransstation() {
		busDataAPIService.getAllCityTransstation();
	}

	@Test
	@Ignore
	public void testFindOnePoiBystationID() {
		Poi p = busDataAPIService.findOnePoiBystationID("530800010014027");
		System.out.println(p.getPoiName());
	}

	@Test
	@Ignore
	public void testFindOneTaxiByCityName() {
		Taxi t = busDataAPIService.findOneTaxiByCityName("北京市");
		System.out.println("test");
		// System.out.println(t.getCityName());
	}

	@Test
	@Ignore
	public void testFindSpeedByCityTransportatio() {
		List<Speed> sp = busDataAPIService.findSpeedByCityTransportation("公交",
				"默认");
		System.out.println(sp.get(0).getSpeed());
	}

	@Test
	@Ignore
	public void testFindOneSpeedByCityTransportatio() {
		Speed sp = busDataAPIService.findOneSpeedByCityTransportation("公交",
				"默认", "");
	}

	@Test
	@Ignore
	public void testfindPoiBystationIDList() {
		List<String> ids = new ArrayList<String>();
		Map<String, List<Poi>> mp = busDataAPIService
				.findPoiBystationIDList("530800010013001");
		for (java.util.Map.Entry<String, List<Poi>> e : mp.entrySet()) {
			System.out.println(e.getKey());
		}
	}

	@Test
	public void testFindOneUser() {
		String key = "48e0087d372b5b5676f11ca7aa8a8c43c7bf189d511a418e72591eb7e33f703f04c3fa16df6c90bd";
		Object obj = busDataAPIService.findUserKey(key);
		System.out.println(obj);
	}

}
