package com.techapi.bus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BUS_POI")
public class Poi {

    @Column(name="CITYCODE")
	private String cityCode;// IS '城市代码';
    @Id
	@Column(name="STATIONID")
	private String stationId;// IS '站点ID';
	@Column(name="POIID")
	private String poiId;// IS 'POIID';
	@Column(name="POINAME")
	private String poiName;// IS '地标名称';
	@Column(name="POITYPE1")
	private String poiType1;// IS 'POI大类';
	@Column(name="POITYPE2")
	private String poiType2;// IS 'POI中类';
	@Column(name="POITYPE3")
	private String poiType3;// IS 'POI小类';
	@Column(name="POICOORDINATE")
	private String poiCoordinate;// IS 'POI坐标';
	@Column(name="WALKDISTANCE")
	private String walkDistance;// IS '距离站点的步行距离(米)';
	@Column(name="ORIENTATION")
	private String orientation;// IS '方位';
    @Column(name = "ADDRESS")
    private String address;// IS '地址';
    @Column(name = "TEL")
    private String tel;// IS '电话';


    public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getPoiId() {
		return poiId;
	}
	public void setPoiId(String poiId) {
		this.poiId = poiId;
	}
	public String getPoiName() {
		return poiName;
	}
	public void setPoiName(String poiName) {
		this.poiName = poiName;
	}
	public String getPoiType1() {
		return poiType1;
	}
	public void setPoiType1(String poiType1) {
		this.poiType1 = poiType1;
	}
	public String getPoiType2() {
		return poiType2;
	}
	public void setPoiType2(String poiType2) {
		this.poiType2 = poiType2;
	}
	public String getPoiType3() {
		return poiType3;
	}
	public void setPoiType3(String poiType3) {
		this.poiType3 = poiType3;
	}
	public String getPoiCoordinate() {
		return poiCoordinate;
	}
	public void setPoiCoordinate(String poiCoordinate) {
		this.poiCoordinate = poiCoordinate;
	}
	public String getWalkDistance() {
		return walkDistance;
	}
	public void setWalkDistance(String walkDistance) {
		this.walkDistance = walkDistance;
	}
	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
