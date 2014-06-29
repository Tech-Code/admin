package com.techapi.bus.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/applicationContext-bus-dao-oracle.xml")
@Transactional
public class AnalysisTypeTest{

	@Resource
    private AnalysisTypeDao dao;
	
	@Test
	public void testfindByPosition(){
		dao.findByTimeAndType("111111","2013-01-01 11:11:11","2014-01-01 11:11:11");
	}
}
