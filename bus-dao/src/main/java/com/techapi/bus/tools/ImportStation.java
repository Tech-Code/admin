package com.techapi.bus.tools;

import com.techapi.bus.data.ImportStationService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xuefei on 7/20/14.
 */
public class ImportStation {
    protected ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            "classpath:META-INF/applicationContext-bus-dao-oracle.xml");

    public ImportStationService importStationService = context
            .getBean(ImportStationService.class);


    public static void main(String[] args) {
        new ImportStation().importStationService.importStation();
    }

}
