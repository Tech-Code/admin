package com.techapi.bus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="BUS_TRANSSTATION")
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
}
