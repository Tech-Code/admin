package com.techapi.bus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CITYSTATION")
public class CityStation {
	@Id
	@Column(name="CITY_CODE")
	private String cityCode;
	@Column(name="CITY_NAME")
	private String cityName;
	@Column(name="TRANS_TYPE")
	private String transType;
	@Column(name="STATION_NAME")
	private String stationName;
	@Column(name="TRANS_DETAIL")
	private String transdetail;
	@Column(name="COORDINATE")
	private String coordinate;
	
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getTransdetail() {
		return transdetail;
	}
	public void setTransdetail(String transdetail) {
		this.transdetail = transdetail;
	}
	public String getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
}
