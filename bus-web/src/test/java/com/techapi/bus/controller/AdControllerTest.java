package com.techapi.bus.controller;

import java.util.Date;

import org.junit.Test;

import com.techapi.bus.model.Advertise;
import com.techapi.bus.model.TypeEnum;
import com.techapi.bus.service.AdService;
import com.techapi.bus.support.TestSupport;
import com.techapi.bus.util.MapUtilsTest;

/**
 * @Description:
 * @author hongjia.hu
 * @date 2014-3-13
 */
public class AdControllerTest extends TestSupport {
	private AdService adService = (AdService) context.getBean(AdService.class);

	@Test
	public void testInsertAd() {
		Advertise model = new Advertise();

		double[] lonlat = MapUtilsTest.getRandom();
		model.setLng(lonlat[0]);
		model.setLat(lonlat[1]);
		model.setType(TypeEnum.FOOD.intValue());
		model.setTitle("周一了~");
		model.setOvertime(new Date().getTime());
		model.setStartTime(new Date().getTime());
		model.setAdContent("测试内容");

		adService.addOrUpdate(model);
	}

}
