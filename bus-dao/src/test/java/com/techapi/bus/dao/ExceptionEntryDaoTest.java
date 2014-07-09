package com.techapi.bus.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StopWatch;

import com.techapi.bus.entity.ExceptionEntry;

/**
 * Created by CH on 4/17/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/applicationContext-loganalyzer-dao-mysql.xml")
@Transactional
public class ExceptionEntryDaoTest {
	
	private static final DateTimeFormatter FORMATTER_TIME = DateTimeFormat.forPattern("yyyy-MM-dd");

    @Resource
    private ExceptionEntryDao dao;

    @Test
    @Repeat(1)
    @Rollback(false)
    public void testInsert() {
        String id = "bsd-label" + RandomUtils.nextInt(1, 12);
        ExceptionEntry entry = new ExceptionEntry(id, new Date(), "NullPointerException", "Some Action", "msg", "exception details" + RandomStringUtils.randomAlphanumeric(10));
        dao.save(entry);
        ExceptionEntry persisted = dao.findOne(entry.getId());
        System.out.println(persisted);
        Assert.assertNotNull(persisted);
        Assert.assertTrue(dao.exists(entry.getId()));

        id = "www-label" + RandomUtils.nextInt(1, 12);
        entry = new ExceptionEntry(id, new Date(), "NumberFormatException", "Some Action", "msg", "exception details" + RandomStringUtils.randomAlphanumeric(10));
        dao.save(entry);
    }

    @Test
    public void testQuery() {
        StopWatch sw = new StopWatch("query");
        sw.start();
        Pageable pageable = new PageRequest(0, 40, Sort.Direction.DESC, "id.timestamp");
        Page<ExceptionEntry> entries = dao.findByServerAndException("www-label0", "RuntimeException", pageable);
        sw.stop();
        System.out.println("=======================");
        System.out.println(sw.prettyPrint());
        System.out.println("logEntries:" + entries.getSize());
        Assert.assertNotNull(entries);
        for (ExceptionEntry e : entries) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void testTimestampOfLastEntry() {
        StopWatch sw = new StopWatch("query");
        sw.start();
        Pageable pageable = new PageRequest(0, 1, Sort.Direction.DESC, "id.timestamp");
        List<ExceptionEntry> entries = dao.findByServer("www-label0", pageable);
        sw.stop();
        System.out.println("=======================");
        System.out.println(sw.prettyPrint());
        Assert.assertNotNull(entries);
        for (ExceptionEntry entry : entries) {
            System.out.println(entry);
        }
    }

    @Test
    public void testGetExceptionCount() {
        StopWatch sw = new StopWatch("query");
        sw.start();
        DateTime dt = new DateTime(2014, 4, 22, 15, 10, 23, 0);//2014-04-23 12:16:23
        Date date = dt.toDate();
        Long count = dao.getExceptionCountSince("www-label0", "NullPointerException", date);
        sw.stop();
        System.out.println("=======================");
        System.out.println(sw.prettyPrint());
        Assert.assertNotNull(count);
        System.out.println("count: " + count);
    }
    
    @Test
    public void testException() {
    	Date startDate = DateTime.parse("2014-05-01", FORMATTER_TIME).toDate();
    	Date endDate = DateTime.parse("2014-05-15", FORMATTER_TIME).toDate();
    	List<ImmutablePair<String, Long>> ecs = dao.countLocationByServerAndException("bsd-02", "javax.servlet.jsp.JspException", startDate.getTime(), endDate.getTime());
    	Assert.assertNotNull(ecs);
    	for (ImmutablePair<String, Long> ec : ecs) {
    		System.out.println(ec);
    	}
    }
    
    @Test
    public void testFindFirstExceptionTimestamp() {
    	Pageable pageable = new PageRequest(0, 1, Sort.Direction.ASC, "id.timestamp");
    	Page<ExceptionEntry> page = dao.findAll(pageable);
    	if (page.hasContent()) {
    		System.out.println(page.getContent().size());
    		System.out.println(page.getContent().get(0).getId().getTimeString());
    	}
    }
}
