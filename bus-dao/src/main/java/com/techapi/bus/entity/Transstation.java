package com.techapi.bus.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="BUS_TRANSSTATION")
public class Transstation implements java.io.Serializable{

	@Id
    @GeneratedValue(generator = "UUIDGenerator")
    @GenericGenerator(name="UUIDGenerator",strategy="uuid")
    private String id;
	@Column(name="TRANSTYPE")
	private String transType;//	交通工具类型	VARCHAR2(64)
	@Column(name="TRIPS")
	private String trips;//	班次	VARCHAR2(64)
	@Column(name="TRANSDETAIL")
	private String transDetail;//	交通工具小类	VARCHAR2(64)
	@Column(name="CITYCODE")
	private String cityCode;//	城市代码	VARCHAR2(64)
	@Column(name="STATION")
	private String station;//	站点	VARCHAR2(64)
	@Column(name="STATIONORDER")
	private int stationOrder;//	站序	NUMBER(6)
	@Column(name="COORDINATE")
	private String coordinate;//	站点坐标	VARCHAR2(64)
	@Column(name="ARRIVETIME")
	private String arriveTime;//	'到达时间--格式HH*60+mm'
	@Column(name="DEPARTTIME")
	private String departTime; //发车时间--格式HH*60+mm
	@Column(name="MILES")
	private Double miles;//	里程	DECIMAL(12,1)
	@Column(name="PRICE")
	private Double price;//	票价	DECIMAL(12,1)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getTrips() {
		return trips;
	}
	public void setTrips(String trips) {
		this.trips = trips;
	}
	public String getTransDetail() {
		return transDetail;
	}
	public void setTransDetail(String transDetail) {
		this.transDetail = transDetail;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public int getStationOrder() {
		return stationOrder;
	}
	public void setStationOrder(int stationOrder) {
		this.stationOrder = stationOrder;
	}
	public String getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getDepartTime() {
		return departTime;
	}
	public void setDepartTime(String departTime) {
		this.departTime = departTime;
	}
	public Double getMiles() {
		return miles;
	}
	public void setMiles(Double miles) {
		this.miles = miles;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
}
