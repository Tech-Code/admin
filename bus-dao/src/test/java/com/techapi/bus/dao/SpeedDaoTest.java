package com.techapi.bus.dao;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/applicationContext-bus-dao-oracle.xml")
@Transactional
public class SpeedDaoTest {

	@Resource
    private SpeedDao dao;
	
	@Test
	public void testFindByTransportationAndCityName() {
		dao.findByTransportation("公交");
	}

	@Test
	@Ignore
	public void testFindByTransportationAndDetailAndCityName() {
		dao.findByTransportation("公交");
	}

}
