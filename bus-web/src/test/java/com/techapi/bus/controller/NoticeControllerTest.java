package com.techapi.bus.controller;

import java.util.Date;

import org.junit.Test;

import com.techapi.bus.dao.NoticeDao;
import com.techapi.bus.model.NoticeBoard;
import com.techapi.bus.model.UpdateInfo;
import com.techapi.bus.support.TestSupport;

/**
 * @Description:
 * @author hongjia.hu
 * @date 2014-3-26
 */
public class NoticeControllerTest extends TestSupport {

	private NoticeDao noticeDao = (NoticeDao) context.getBean(NoticeDao.class);

	@Test
	public void testInsertInfo() {
		NoticeBoard notice = new NoticeBoard();
		notice.setPubTime(new Date().getTime());
		notice.setTitle("113路车路线变更");
		notice.setContent("沈阳北站修路，113路公交车绕行沈阳南站");
		notice.setType(0);
		
		noticeDao.insert(notice);
	}
}
