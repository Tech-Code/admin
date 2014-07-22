package com.techapi.bus.solr;

import com.techapi.bus.entity.PoiStation;
import com.techapi.bus.solr.basic.BaseOperate;
import com.techapi.bus.solr.query.QueryForBus;
import com.techapi.bus.solr.result.PoiStationResult;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @Description
 * @Author hongjia.hu
 * @Date 2014-5-25
 */
public class BusSearch extends BaseOperate {

	protected Logger log = Logger.getLogger(BusSearch.class);

	private QueryForBus query;

	public BusSearch(QueryForBus query) {
		new BaseOperate();
		this.query = query;
	}

	/**
	 * @return
	 */
	public PoiStationResult searchPoiStationList() {
        //String q = "";
        //// 设置城市查询条件
        //if (query.getAdName() != null && !query.getAdName().isEmpty()) {
        //    q = q + "adName:\"" + query.getAdName() + "\"";
        //}
        //
        //// 设置城市查询条件
        //if (query.getPoiType() != null && !query.getPoiType().isEmpty()) {
        //    q = q + " AND poiType:\"" + query.getPoiType() + "\"";
        //}
        String poiType = query.getPoiType();
        String[] poiTypes = poiType.split(";");
        String q = "(adName:\"" + query.getAdName() + "\"";

        StringBuilder queryForPoiType = new StringBuilder();
        for(int i = 0;i < poiTypes.length;i++) {
            if(i == 0) queryForPoiType.append(" AND (poiType:\"" + poiTypes[i] + "\"");
            else {
                queryForPoiType.append(" OR poiType:\"" + poiTypes[i] + "\"");
            }
        }
        queryForPoiType.append(")");

        q = q + queryForPoiType.toString() + ")";

		// 设置城市查询条件
		if (query.getPoiStationName() != null && !query.getPoiStationName().isEmpty()) {
			q = q + " AND poiStationName:\"" + query.getPoiStationName() + "\"";
		}

		return execute(q);
	}

    /**
     * @param q
     * @return
     */
    private PoiStationResult execute(String q) {
        log.debug("Query:" + q);

        List<PoiStation> list = (List)queryBeans(q,query.getStart(), query.getRows(), PoiStation.class);

        PoiStationResult result = new PoiStationResult();
        result.setTotal(getNumFound());
        result.setList(list);
        return result;
    }
}
