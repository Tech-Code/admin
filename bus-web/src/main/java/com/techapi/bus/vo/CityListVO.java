package com.techapi.bus.vo;

/***
 * 城市查询类别
 * @author jiayusun
 *
 */
public class CityListVO {

	private String id;
	/**
	 * 
	 */
	private String typeName;
	/**
	 * 名称查询
	 */
	private int name;
	/**
	 * 站点查询
	 */
	private int poi;
	/**
	 * 查询车站
	 */
	private int station;
	/**
	 * 周边站点
	 */
	private int near;
	/**
	 * 周边路线
	 */
	private int line;
	/**
	 * 步行导航
	 */
	private int walk;
	/***
	 * 城市名称
	 */
	private String city;
	/***
	 * 总量
	 */
	private int total;
	
	public void putTotal(String serverName,int timetotal){
		if("name".equals(serverName)){
			this.name+=timetotal;
		}else if("poi".equals(serverName)){
			this.poi+=timetotal;
		}else if("station".equals(serverName)){
			this.station+=timetotal;
		}else if("near".equals(serverName)){
			this.near+=timetotal;
		}else if("line".equals(serverName)){
			this.line+=timetotal;
		}else if("walk".equals(serverName)){
			this.walk+=timetotal;
		}
		total+=timetotal;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public int getPoi() {
		return poi;
	}

	public void setPoi(int poi) {
		this.poi = poi;
	}

	public int getStation() {
		return station;
	}

	public void setStation(int station) {
		this.station = station;
	}

	public int getNear() {
		return near;
	}

	public void setNear(int near) {
		this.near = near;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getWalk() {
		return walk;
	}

	public void setWalk(int walk) {
		this.walk = walk;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
