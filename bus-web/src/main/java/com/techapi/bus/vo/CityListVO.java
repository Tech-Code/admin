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
	 * 
	 */
	private String keyName;
	/**
	 * 换乘
	 */
	private int transfer;
	/**
	 * 公交查询
	 */
	private int query;
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
		if("transfer".equals(serverName)){
			this.transfer+=timetotal;
		}else if("basic".equals(serverName)){
			this.query+=timetotal;
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

	public int getTransfer() {
		return transfer;
	}

	public void setTransfer(int transfer) {
		this.transfer = transfer;
	}

	public int getQuery() {
		return query;
	}

	public void setQuery(int query) {
		this.query = query;
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

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}	
}
