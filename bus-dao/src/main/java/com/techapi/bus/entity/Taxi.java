package com.techapi.bus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="BUS_TAXI")
public class Taxi {
	@Id
    @GeneratedValue(generator = "UUIDGenerator")
    @GenericGenerator(name="UUIDGenerator",strategy="uuid")
    private String id;
	@Column(name="CITYCODE")
	private String  citycode;// IS '城市代码';
	@Column(name="CITYNAME")
	private String cityname;// IS '城市名';
	@Column(name="D_TIMESECTION")
	private String d_timesection;// IS '日间时间区间';
	@Column(name="D_S_MILES")
	private int  d_s_miles;// IS '起步公里数';
	@Column(name="D_S_COST")
	private double d_s_cost;// IS '起步价(元)';
	@Column(name="D_EXCEED_S_COST")
	private double d_exceed_s_cost;//IS '超出起步单价(元/公里)';
	@Column(name="D_S_EXCEED_D_COST")
	private double d_s_exceed_d_cost;//'超出规定距离单价(比如超出15公里后加收50%)';
	@Column(name="N_TIMESECTION")
	private String n_timesection;// IS '夜间时间区间';
	@Column(name="N_S_MILES")
	private int n_s_miles;// IS '夜间起步公里数';
	@Column(name="N_S_COST")
	private double n_s_cost;// IS '夜间起步价(元)';
	@Column(name="N_EXCEED_S_COST")
	private double n_exceed_s_cost;// IS '夜间超出起步单价(元/公里)';
	@Column(name="N_S_EXCEED_D_COST")
	private double n_s_exceed_d_cost;// IS '夜间超出规定距离单价(元/公里)';
}
