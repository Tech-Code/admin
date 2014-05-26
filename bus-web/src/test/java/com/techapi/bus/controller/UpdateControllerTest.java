package com.techapi.bus.controller;

import java.util.Date;

import org.junit.Test;

import com.techapi.bus.dao.UpdateDao;
import com.techapi.bus.model.UpdateInfo;
import com.techapi.bus.support.TestSupport;

/**
 * @Description:
 * @author hongjia.hu
 * @date 2014-3-26
 */
public class UpdateControllerTest extends TestSupport {

	private UpdateDao updateDao = (UpdateDao) context.getBean(UpdateDao.class);

	@Test
	public void testInsertInfo() {
		UpdateInfo info = new UpdateInfo();
		info.setVer("1.2.0");
		info.setUptime(new Date().getTime());
		info.setMemo("版本升级内容：修复地图卡死");
		info.setUrl("http://datamobile.xxx");

		updateDao.insert(info);
	}
}
