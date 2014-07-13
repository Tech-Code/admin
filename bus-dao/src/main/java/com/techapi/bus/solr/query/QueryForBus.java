package com.techapi.bus.solr.query;

/**
 * @Description
 * @Author hongjia.hu
 * @Date 2014-5-25
 */
public class QueryForBus {

	private String adName;

	private String poiType;

	private String stationName;

    private String poiStationName;

	private int start = 0;

	private int rows = 10;

	private int type = 0;

	private boolean multi = false;

	public boolean isMulti() {
		return multi;
	}

	public void setMulti(boolean multi) {
		this.multi = multi;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getPoiType() {
        return poiType;
    }

    public void setPoiType(String poiType) {
        this.poiType = poiType;
    }

    public String getPoiStationName() {
        return poiStationName;
    }

    public void setPoiStationName(String poiStationName) {
        this.poiStationName = poiStationName;
    }
}
