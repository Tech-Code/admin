package com.techapi.bus.solr;

import com.techapi.bus.entity.PoiStation;
import com.techapi.bus.entity.PoiStationPK;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class BusUpdateTest extends TestCase {

    public void testUpdatePoiStation() throws Exception {
        PoiStation poiStation = new PoiStation();
        poiStation.setId("487acfc3-faeb-47e3-81c1-baae005f4c52");
        poiStation.setAdName("海北藏族自治州");
        poiStation.setPoiType("150400");
        poiStation.setPoiStationName("峨堡汽车站");
        poiStation.setPoiCoordinate("100.934032,37.965812");
        PoiStationPK poiStationPK = new PoiStationPK("1001773977","632200");
        poiStation.setPoiStationPK(poiStationPK);
        //poiStation.setAdCode(poiStationPK.getAdCode());
        new BusUpdate().updatePoiStation(poiStation);
    }

    public void testDeletePoiStation() throws Exception {
        new BusUpdate().deleteBeanById("487acfc3-faeb-47e3-81c1-baae005f4c52");
    }

    public void testUpdatePoiStations() throws Exception {

        PoiStation poiStation = new PoiStation();
        poiStation.setId("d618358c-6d00-4b83-a1fb-ff2f46925df9");
        poiStation.setAdName("海北藏族自治州");
        poiStation.setPoiType("150400");
        poiStation.setPoiStationName("西海镇汽车站问询处");
        poiStation.setPoiCoordinate("100.895465,36.957462");
        PoiStationPK poiStationPK = new PoiStationPK("1011839431", "632200");
        poiStation.setPoiStationPK(poiStationPK);

        PoiStation poiStation1 = new PoiStation();
        poiStation1.setId("b7a46e8b-db1a-415d-ad81-5ce82df63745");
        poiStation1.setAdName("海北藏族自治州");
        poiStation1.setPoiType("150400");
        poiStation1.setPoiStationName("海北州汽车运输合作公司");
        poiStation1.setPoiCoordinate("101.622000,37.376100");
        PoiStationPK poiStationPK1 = new PoiStationPK("1011832413", "632200");
        poiStation1.setPoiStationPK(poiStationPK1);

        List<PoiStation> poiStationList = new ArrayList<>();
        poiStationList.add(poiStation);
        poiStationList.add(poiStation1);

        new BusUpdate().updatePoiStations(poiStationList);


    }
}