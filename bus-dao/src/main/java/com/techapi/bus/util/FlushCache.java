package com.techapi.bus.util;

import com.techapi.bus.data.FlushCacheService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xuefei on 7/4/14.
 */
public class FlushCache {
    protected ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            "classpath:META-INF/applicationContext-bus-dao-oracle.xml");

    public FlushCacheService flushCacheService = context
            .getBean(FlushCacheService.class);

    public static void main(String[] args) {

        new FlushCache().flushCacheService.flushPoi("110100");
    }
}
