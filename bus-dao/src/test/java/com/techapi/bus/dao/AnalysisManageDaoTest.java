package com.techapi.bus.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.techapi.bus.entity.AnalysisManage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/applicationContext-bus-dao-oracle.xml")
@Transactional
public class AnalysisManageDaoTest {
	
	@Resource
    private AnalysisManageDao dao;
	@Test
	public void testFindByName(){
//		List<AnalysisManage> la=dao.findByTimeAndNameAndSearchKey(1, 10,"2014-07-01 00:00:00","2015-07-01 00:00:00","1617610aa3f930281889eb824d81e3bcc67f4e9781c69212880f2e985e1dedf869c2483ece723d68","%station%");
//		System.out.println(la.size());
		
		Pageable pageable = new PageRequest(0, 2);
		Page<AnalysisManage> la = dao.findByTimePage("2014-07-01 00:00:00","2015-07-01 00:00:00", pageable);
	}

}
