package com.techapi.bus.solr;

import com.techapi.bus.entity.CityStation;
import com.techapi.bus.entity.PoiStation;
import com.techapi.bus.solr.basic.BaseOperate;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author hongjia.hu
 * @Date 2014-5-25
 */
public class BusUpdate extends BaseOperate {

	protected Logger log = Logger.getLogger(BusUpdate.class);

	public BusUpdate() {

	}

	/**
	 * @return
	 */
	public void updatePoiStation(PoiStation poiStation) {
        String id = poiStation.getId();

        updateBean(id, poiStation);
	}

    /**
     * @return
     */
    public void updatePoiStations(List<PoiStation> poiStationList) {
        List<String> ids = new ArrayList<>();
        for(PoiStation poiStation : poiStationList) {
            ids.add(poiStation.getId());
        }

        updateBeans(ids,poiStationList);
    }

    /**
     * @return
     */
    public void updateCityStation(CityStation cityStation) {
        String id = cityStation.getId();

        updateBean(id, cityStation);
    }

    /**
     * @return
     */
    public void updateCityStations(List<CityStation> cityStationList) {
        List<String> ids = new ArrayList<>();
        for (CityStation cityStation : cityStationList) {
            ids.add(cityStation.getId());
        }

        updateBeans(ids, cityStationList);
    }
}
