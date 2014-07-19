package com.techapi.bus.vo;

import com.techapi.bus.annotation.ExcelField;

public class GroupListVO {
	
	private String id;
	@ExcelField
	private String startTime;
	@ExcelField
	private String endTime;
	@ExcelField
	private String name;
	@ExcelField
	private String keyName;
	@ExcelField
	private String total;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
}
