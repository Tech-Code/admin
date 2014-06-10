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
	private String tranSportation;// IS '交通工具';
	@Column(name="TRANSPORTDES")
	private String tranSportDes;// IS '交通工具明细';
	@Column(name="SPEED")
	private Double speed;// IS '时速';
	@Column(name="CITYCODE")
	private String  cityCode;// IS '城市代码';
	@Column(name="CITYNAME")
	private String cityName;// IS '城市名';
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTranSportation() {
		return tranSportation;
	}
	public void setTranSportation(String tranSportation) {
		this.tranSportation = tranSportation;
	}
	public String getTranSportDes() {
		return tranSportDes;
	}
	public void setTranSportDes(String tranSportDes) {
		this.tranSportDes = tranSportDes;
	}
	public Double getSpeed() {
		return speed;
	}
	public void setSpeed(Double speed) {
		this.speed = speed;
	}
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
}
