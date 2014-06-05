package com.techapi.bus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="BUS_TRANSSTATION")
public class Transstation {

	@Id
    @GeneratedValue(generator = "UUIDGenerator")
    @GenericGenerator(name="UUIDGenerator",strategy="uuid")
    private String id;
	@Column(name="TRANSTYPE")
	private String transtype;//	交通工具类型	VARCHAR2(64)
	@Column(name="TRIPS")
	private String trips;//	班次	VARCHAR2(64)
	@Column(name="TRANSDETAIL")
	private String transdetail;//	交通工具小类	VARCHAR2(64)
	@Column(name="CITYCODE")
	private String citycode;//	城市代码	VARCHAR2(64)
	@Column(name="STATION")
	private String station;//	站点	VARCHAR2(64)
	@Column(name="STATIONORDER")
	private String stationorder;//	站序	NUMBER(6)
	@Column(name="COORDINATE")
	private String coordinate;//	站点坐标	VARCHAR2(64)
	@Column(name="ARRIVETIME")
	private String arrivetime;//	'到达时间--格式HH*60+mm'
	@Column(name="DEPARTTIME")
	private String departtime; //发车时间--格式HH*60+mm
	@Column(name="MILES")
	private Double miles;//	里程	DECIMAL(12,1)
	@Column(name="PRICE")
	private Double price;//	票价	DECIMAL(12,1)

	
}
