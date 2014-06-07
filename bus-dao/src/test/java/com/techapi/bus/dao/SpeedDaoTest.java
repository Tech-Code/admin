package com.techapi.bus.dao;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/applicationContext-bus-dao-oracle.xml")
@Transactional
public class SpeedDaoTest {

	@Resource
    private SpeedDao dao;
	
	@Test
	public void testFindByTransportationAndCityName() {
		dao.findByTransportationAndCityName("公交", "默认");
	}

	@Test
	@Ignore
	public void testFindByTransportationAndDetailAndCityName() {
		dao.findByTransportationAndDetailAndCityName("公交", "默认","快速");
	}

}
