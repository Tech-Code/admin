package com.techapi.bus.tools;

import com.techapi.bus.data.FlushSolrService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xuefei on 7/10/14.
 */
public class FlushSolr {
    protected ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            "classpath:META-INF/applicationContext-bus-dao-oracle.xml");

    public FlushSolrService flushSolrService = context
            .getBean(FlushSolrService.class);

    public static void main(String[] args) {

        new FlushSolr().flushSolrService.flushPoiStationToSolr("");
    }
}
