package com.techapi.bus.support;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description:
 * @author hongjia.hu@chetuobang.com
 * @date 2013-8-14
 */
public class TestSupport {
	protected static ApplicationContext context;

	@BeforeClass
	public static void setUp() {
		context = new ClassPathXmlApplicationContext("/spring/spring.xml");
	}

	@AfterClass
	public static void tearsDown() {
		context = null;
	}
}
