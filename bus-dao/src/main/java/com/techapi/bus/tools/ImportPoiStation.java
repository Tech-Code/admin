package com.techapi.bus.tools;

import com.techapi.bus.data.ImportPoiStationService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xuefei on 6/8/14.
 */
public class ImportPoiStation {

    protected ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            "classpath:META-INF/applicationContext-bus-dao-oracle.xml");

    public ImportPoiStationService importPoiStationService = context
            .getBean(ImportPoiStationService.class);


    public static void main(String[] args) {
        new ImportPoiStation().importPoiStationService.importPoiStation();
    }
}
