package com.techapi.bus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="BUS_SPEED")
public class Speed {

	@Id
    @GeneratedValue(generator = "UUIDGenerator")
    @GenericGenerator(name="UUIDGenerator",strategy="uuid")
    private String id;
	@Column(name="TRANSPORTATION")
	private String transportation;// IS '交通工具';
	@Column(name="TRANSPORTDES")
	private String transportdes;// IS '交通工具明细';
	@Column(name="SPEED")
	private Double speed;// IS '时速';
	@Column(name="CITYCODE")
	private String  citycode;// IS '城市代码';
	@Column(name="CITYNAME")
	private String cityname;// IS '城市名';
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTransportation() {
		return transportation;
	}
	public void setTransportation(String transportation) {
		this.transportation = transportation;
	}
	public String getTransportdes() {
		return transportdes;
	}
	public void setTransportdes(String transportdes) {
		this.transportdes = transportdes;
	}
	public Double getSpeed() {
		return speed;
	}
	public void setSpeed(Double speed) {
		this.speed = speed;
	}
	public String getCitycode() {
		return citycode;
	}
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	public String getCityname() {
		return cityname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
}
