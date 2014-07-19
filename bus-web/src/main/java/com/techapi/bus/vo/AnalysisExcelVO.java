package com.techapi.bus.vo;

import com.techapi.bus.annotation.ExcelField;

public class AnalysisExcelVO {
	@ExcelField
	private String name;
	@ExcelField
	private String keyName;
	@ExcelField
	private String total;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	
}
