package com.techapi.bus.tools;

import com.techapi.bus.BusConstants;
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
        if(args.length > 0) {
            String flushType = args[0];
            if(flushType.equals(BusConstants.TOOLS_FLUSH_SOLR_POISTATION)) {
                new FlushSolr().flushSolrService.flushPoiStationToSolr("");
            }
            if(flushType.equals(BusConstants.TOOLS_FLUSH_SOLR_CITYSTATION)) {
                new FlushSolr().flushSolrService.flushCityStationToSolr("");
            }
        }

    }
}
