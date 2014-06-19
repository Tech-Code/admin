package com.techapi.bus.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="BUS_CITYSTATION")
public class CityStation implements java.io.Serializable{
    @Id
    @GeneratedValue(generator = "UUIDGenerator")
    @GenericGenerator(name="UUIDGenerator",strategy="uuid")
    private String id;

    @Column(name="CITYCODE")
	private String cityCode;
	@Column(name="CITYNAME")
	private String cityName;
	@Column(name="TRANSTYPE")
	private String transType;
	@Column(name="STATIONNAME")
	private String stationName;
	@Column(name="TRANSDETAIL")
	private String transdetail;
	@Column(name="COORDINATE")
	private String coordinate;
	
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return this.cityCode + this.cityName + "(" + this.coordinate + ")";
    }
}
