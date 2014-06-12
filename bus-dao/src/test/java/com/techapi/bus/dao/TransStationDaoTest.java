package com.techapi.bus.dao;

import com.techapi.bus.entity.Transstation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/applicationContext-bus-dao-oracle.xml")
@Transactional
public class TransStationDaoTest {

	@Resource
    private TransstationDao dao;
	@Test
	public void testFindAll() {
		List<Transstation> transstationList = (List<Transstation>)dao.findAll();
        System.out.println(transstationList.size());
    }

}
