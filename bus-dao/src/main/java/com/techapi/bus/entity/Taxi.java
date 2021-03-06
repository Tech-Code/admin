package com.techapi.bus.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="BUS_TAXI")
public class Taxi implements java.io.Serializable{
	@Id
    @GeneratedValue(generator = "UUIDGenerator")
    @GenericGenerator(name="UUIDGenerator",strategy="uuid")
    private String id;
	@Column(name="CITYCODE")
	private String  cityCode;// IS '城市代码';
	@Column(name="CITYNAME")
	private String cityName;// IS '城市名';
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
	@Column(name="D_BAS")
	private double d_bas;// IS '日间燃油费';
    @Column(name = "N_BAS")
    private double n_bas;// IS '夜间燃油费';
    @Column(name = "D_EXCEED_DISTANCE")
    private double d_exceed_distance;// IS '日间规定距离';
    @Column(name = "N_EXCEED_DISTANCE")
    private double n_exceed_distance;// IS '夜间规定距离';
    @Column(name = "N_S_EXCEED_D_COST")
    private double n_s_exceed_d_cost;// IS '夜间超出规定距离单价';
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getD_timesection() {
		return d_timesection;
	}
	public void setD_timesection(String d_timesection) {
		this.d_timesection = d_timesection;
	}
	public int getD_s_miles() {
		return d_s_miles;
	}
	public void setD_s_miles(int d_s_miles) {
		this.d_s_miles = d_s_miles;
	}
	public double getD_s_cost() {
		return d_s_cost;
	}
	public void setD_s_cost(double d_s_cost) {
		this.d_s_cost = d_s_cost;
	}
	public double getD_exceed_s_cost() {
		return d_exceed_s_cost;
	}
	public void setD_exceed_s_cost(double d_exceed_s_cost) {
		this.d_exceed_s_cost = d_exceed_s_cost;
	}
	public double getD_s_exceed_d_cost() {
		return d_s_exceed_d_cost;
	}
	public void setD_s_exceed_d_cost(double d_s_exceed_d_cost) {
		this.d_s_exceed_d_cost = d_s_exceed_d_cost;
	}
	public String getN_timesection() {
		return n_timesection;
	}
	public void setN_timesection(String n_timesection) {
		this.n_timesection = n_timesection;
	}
	public int getN_s_miles() {
		return n_s_miles;
	}
	public void setN_s_miles(int n_s_miles) {
		this.n_s_miles = n_s_miles;
	}
	public double getN_s_cost() {
		return n_s_cost;
	}
	public void setN_s_cost(double n_s_cost) {
		this.n_s_cost = n_s_cost;
	}
	public double getN_exceed_s_cost() {
		return n_exceed_s_cost;
	}
	public void setN_exceed_s_cost(double n_exceed_s_cost) {
		this.n_exceed_s_cost = n_exceed_s_cost;
	}
	public double getN_s_exceed_d_cost() {
		return n_s_exceed_d_cost;
	}
	public void setN_s_exceed_d_cost(double n_s_exceed_d_cost) {
		this.n_s_exceed_d_cost = n_s_exceed_d_cost;
	}

    public double getD_bas() {
        return d_bas;
    }

    public void setD_bas(double d_bas) {
        this.d_bas = d_bas;
    }

    public double getN_bas() {
        return n_bas;
    }

    public void setN_bas(double n_bas) {
        this.n_bas = n_bas;
    }

    public double getD_exceed_distance() {
        return d_exceed_distance;
    }

    public void setD_exceed_distance(double d_exceed_distance) {
        this.d_exceed_distance = d_exceed_distance;
    }

    public double getN_exceed_distance() {
        return n_exceed_distance;
    }

    public void setN_exceed_distance(double n_exceed_distance) {
        this.n_exceed_distance = n_exceed_distance;
    }
}
