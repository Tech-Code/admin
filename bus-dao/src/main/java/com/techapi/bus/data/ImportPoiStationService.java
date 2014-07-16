package com.techapi.bus.data;

import com.techapi.bus.dao.AreaDao;
import com.techapi.bus.dao.PoiStationDao;
import com.techapi.bus.entity.Area;
import com.techapi.bus.entity.PoiStation;
import com.techapi.bus.entity.PoiStationPK;
import com.techapi.bus.util.ConfigUtils;
import com.techapi.bus.util.HttpUtils;
import com.techapi.bus.util.XMLUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImportPoiStationService {

    @Resource
    private AreaDao areaDao;

    @Resource
    private PoiStationDao poiStationDao;

    private static final List<String> poiTransTypeList = new ArrayList<>();

    protected static Logger log = Logger.getLogger(ImportPoiStationService.class);

    public void importPoiStation() {
        log.info("--------------------开始站点数据--------------------");
        // 读站点信息表  Map<cityName,List<StationObject>>
        log.info("开始获取地区数据....");
        List<Area> areaList = areaDao.findByType(1);
        log.info("获取地区数据完毕....");
        for (Area area : areaList) {
            long adCode = area.getAdCode();
            String adName = area.getAreaName();
            log.info("BEGIN -- AdCode: " + adCode);
            List<PoiStation> adCodeStationList = getAdCodeStationListMap(Long.valueOf(adCode).toString(),adName);
            poiStationDao.save(adCodeStationList);
            log.info("END -- AdCode: " + adCode);
            log.info("--------------------------------");
        }

        log.info("获取POI站点完毕....");

	}

    public List<PoiStation> getAdCodeStationListMap(String adCode,String adName) {
        if (poiTransTypeList.size() <= 0) {
            String[] transType = ConfigUtils.BUS_POI_TRANS_TYPE.split(",");
            for (String s : transType) {
                poiTransTypeList.add(s);
            }
        }


        List<PoiStation> poiStationList = new ArrayList<>();

        for (String poiTransType : poiTransTypeList) {
            int pageNum = 1;
            log.info("PoiTansType: " + poiTransType);

            do {
                Map<String, String> paraMap = new HashMap<>();

                paraMap.put("submit_form", "poi_search");
                paraMap.put("query_type", "TQUERY");
                paraMap.put("data_type", "POI");
                paraMap.put("category", poiTransType);
                paraMap.put("page_num", "50");
                paraMap.put("page", Integer.toString(pageNum++));
                paraMap.put("range", "3000");
                paraMap.put("custom_and", "true");
                paraMap.put("sort_rule", "0");
                paraMap.put("display_type", "1");
                paraMap.put("city", adCode);

                // 调用接口 cityCode为三位，按照cityCode处理
                String response = HttpUtils.URLGet(ConfigUtils.BUS_POI_SEARCH_URL, paraMap, "UTF-8");
                // 解析json/xml
                Map result = XMLUtils.readPoiXMLToMap(response);

                List<Map<String, String>> poilistMap = (List) result.get("poilist");
                List<PoiStation> poiStationListPerPage = new ArrayList<>();
                for (Map<String, String> poiMap : poilistMap) {

                    String poiid = poiMap.get("poiid").toString();
                    String name = poiMap.get("name").toString();

                    String x = poiMap.get("x").toString();
                    String y = poiMap.get("y").toString();
                    //String adminCode = poiMap.get("adminCode").toString();

                    PoiStation poiStation = new PoiStation();
                    PoiStationPK poiStationPK = new PoiStationPK(poiid,adCode);
                    poiStation.setPoiStationPK(poiStationPK);
                    poiStation.setPoiStationName(name);
                    poiStation.setPoiCoordinate(x + "," + y);
                    poiStation.setPoiType(poiTransType);
                    poiStation.setAdName(adName);
                    poiStation.setAdCodeForSolr(adCode);
                    poiStationListPerPage.add(poiStation);
                }


                poiStationList.addAll(poiStationListPerPage);

                if (poiStationListPerPage.size() < 50) {
                    break;
                }
                int totalCount = Integer.parseInt(result.get("count").toString());
                if(pageNum * 50 >= totalCount) {
                    break;
                }
            } while (true);

        }

        return poiStationList;
    }

}
