package com.techapi.bus.tools;

import com.techapi.bus.data.ImportPoiService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xuefei on 6/8/14.
 */
public class ImportPoi {

    protected ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            "classpath:META-INF/applicationContext-bus-dao-oracle.xml");

    public ImportPoiService importPoiService = context
            .getBean(ImportPoiService.class);


    public static void main(String[] args) {
        String startCity = "";
        String startLine = "0";
        if(args.length > 0) {
            startCity = args[0];
        }
        if (args.length > 1) {
            startLine = args[1];
        }
        System.out.println("startCity:" + startCity + "startLine:" + startLine);
        new ImportPoi().importPoiService.importPoi(startCity,startLine);

    }
}
