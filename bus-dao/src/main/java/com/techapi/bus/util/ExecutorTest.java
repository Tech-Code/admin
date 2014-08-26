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
            int insertRows = new ImportPoiMultiThread().importPoiMutiThreadService.importPoi(cityName, cityStationMap, poiTypeMap);
            return this.cityName + "," + insertRows;
        }

    }

    // 通过ExecutorService调用多线程
    public Map<String, Integer> doExecutorService_submit(Iterator cityNameIterator, Map<String, List<Station>> cityStationMap, Map<String, PoiType> poiTypeMap) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(ConfigUtils.BUS_IMPORT_POI_THREAD_COUNT);
        Deque<Future<?>> futures = new ArrayDeque<Future<?>>();
        Map<String, Integer> cityNameInsertRowsMap = new HashMap<>();
        while (cityNameIterator.hasNext()) {
            String cityName = cityNameIterator.next().toString();
            futures.add(executorService.submit(new MyCallable(cityName, cityStationMap, poiTypeMap)));
        }
        for (Future<?> future : futures) {
            String result = future.get().toString();
            String[] resultArray = result.split(",");
            cityNameInsertRowsMap.put(resultArray[0], Integer.valueOf(resultArray[1]));
        }
        executorService.shutdown();

        return cityNameInsertRowsMap;
    }
}
