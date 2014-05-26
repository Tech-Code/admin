package com.techapi.bus;

import java.util.List;

import org.junit.Test;

import com.techapi.bus.dao.LineMatchDao;
import com.techapi.bus.model.LineMatch;
import com.techapi.bus.support.TestSupport;

/**
 * @Description:
 * @author hongjia.hu
 * @date 2014-3-8
 */
public class BusServiceTest extends TestSupport {

	private LineMatchDao lineMatchDao = (LineMatchDao) context
			.getBean(LineMatchDao.class);
	
	@Test
	public void testMatch() {
		LineMatch line = lineMatchDao.findByLineid("000178");
		System.out.println(line);
	}
}
