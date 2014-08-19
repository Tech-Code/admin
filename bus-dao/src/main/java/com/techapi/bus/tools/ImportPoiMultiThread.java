package com.techapi.bus.tools;

import com.techapi.bus.data.ImportPoiMutiThreadService;
import com.techapi.bus.entity.PoiType;
import com.techapi.bus.entity.Station;
import com.techapi.bus.util.ConfigUtils;
import com.techapi.bus.util.ExecutorTest;
import com.techapi.bus.util.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by xuefei on 6/8/14.
 */
public class ImportPoiMultiThread {

    protected ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            "classpath:META-INF/applicationContext-bus-dao-oracle.xml");

    protected static Logger log = Logger.getLogger(ImportPoiMultiThread.class);

    public ImportPoiMutiThreadService importPoiMutiThreadService = context
            .getBean(ImportPoiMutiThreadService.class);

    public static void main(String[] args) {
        long starttime = System.currentTimeMillis();
        log.info("--------------------开始导入POI--------------------");
        log.info("开始获取站点数据....");
        Map<String, List<Station>> cityStationMap = FileUtils.getStationData(ConfigUtils.BUS_STATION_DATA);
        log.info("获取站点数据完毕....");
        log.info("开始获取地标点类别....");
        Map<String, PoiType> poiTypeMap = FileUtils.getPoiType(ConfigUtils.BUS_POITYPE_DATA_CSV);
        log.info("获取地标点类别完毕....");
        Iterator cityNameIterator = cityStationMap.keySet().iterator();
        log.info("开始获取站点周边POI....");

        try {
            new ExecutorTest().doExecutorService_submit(cityNameIterator, cityStationMap, poiTypeMap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        log.info("获取站点周边POI完毕....");
        log.info("总耗时：" + (System.currentTimeMillis() - starttime));

    }
}
