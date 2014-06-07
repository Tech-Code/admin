package com.techapi.bus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="BUS_POI")
public class Poi {
	@Id
    @GeneratedValue(generator = "UUIDGenerator")
    @GenericGenerator(name="UUIDGenerator",strategy="uuid")
    private String id;
	@Column(name="CITYCODE")
	private String citycode;// IS '城市代码';
	@Column(name="STATIONID")
	private String stationid;// IS '站点ID';
	@Column(name="POIID")
	private String poiid;// IS 'POIID';
	@Column(name="POINAME")
	private String poiname;// IS '地标名称';
	@Column(name="POITYPE1")
	private String poitype1;// IS 'POI大类';
	@Column(name="POITYPE2")
	private String poitype2;// IS 'POI中类';
	@Column(name="POITYPE3")
	private String poitype3;// IS 'POI小类';
	@Column(name="POICOORDINATE")
	private String poicoordinate;// IS 'POI坐标';
	@Column(name="WALKDISTANCE")
	private String walkdistance;// IS '距离站点的步行距离(米)';
	@Column(name="ORIENTATION")
	private String orientation;// IS '方位';
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCitycode() {
		return citycode;
	}
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	public String getStationid() {
		return stationid;
	}
	public void setStationid(String stationid) {
		this.stationid = stationid;
	}
	public String getPoiid() {
		return poiid;
	}
	public void setPoiid(String poiid) {
		this.poiid = poiid;
	}
	public String getPoiname() {
		return poiname;
	}
	public void setPoiname(String poiname) {
		this.poiname = poiname;
	}
	public String getPoitype1() {
		return poitype1;
	}
	public void setPoitype1(String poitype1) {
		this.poitype1 = poitype1;
	}
	public String getPoitype2() {
		return poitype2;
	}
	public void setPoitype2(String poitype2) {
		this.poitype2 = poitype2;
	}
	public String getPoitype3() {
		return poitype3;
	}
	public void setPoitype3(String poitype3) {
		this.poitype3 = poitype3;
	}
	public String getPoicoordinate() {
		return poicoordinate;
	}
	public void setPoicoordinate(String poicoordinate) {
		this.poicoordinate = poicoordinate;
	}
	public String getWalkdistance() {
		return walkdistance;
	}
	public void setWalkdistance(String walkdistance) {
		this.walkdistance = walkdistance;
	}
	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
}
