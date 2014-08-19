package com.techapi.bus.util;

import com.techapi.bus.entity.PoiType;
import com.techapi.bus.entity.Station;
import com.techapi.bus.tools.ImportPoiMultiThread;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by xuefei on 8/18/14.
 */
public class ExecutorTest {
    public class MyCallable implements Callable<String> {

        private String cityName;

        private Map<String, List<Station>> cityStationMap;

        private Map<String, PoiType> poiTypeMap;

        public MyCallable(String cityName, Map<String, List<Station>> cityStationMap, Map<String, PoiType> poiTypeMap) {
            this.cityName = cityName;
            this.cityStationMap = cityStationMap;
            this.poiTypeMap = poiTypeMap;
        }

        @Override
        public String call() throws Exception {
            System.out.println("thread [" + this.cityName + "] start....");
            new ImportPoiMultiThread().importPoiMutiThreadService.importPoi(cityName, cityStationMap, poiTypeMap);
            return "thread [" + this.cityName + "] end.";
        }

    }

    // 通过ExecutorService调用多线程
    public void doExecutorService_submit(Iterator cityNameIterator, Map<String, List<Station>> cityStationMap, Map<String, PoiType> poiTypeMap) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Deque<Future<?>> futures = new ArrayDeque<Future<?>>();
        while (cityNameIterator.hasNext()) {
            String cityName = cityNameIterator.next().toString();
            futures.add(executorService.submit(new MyCallable(cityName, cityStationMap, poiTypeMap)));
        }
        for (Future<?> future : futures) {
            System.out.println(future.get().toString());
        }
        executorService.shutdown();
    }
}
