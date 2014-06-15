package com.techapi.bus.data;

import java.util.List;
import java.util.Map;

public class CityTransstationRelation {
    
	private String transtype;
	private Map<String,List<TransstationInformation>> transstationInformation;
	public String getTranstype() {
		return transtype;
	}
	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}
	public Map<String, List<TransstationInformation>> getTransstationInformation() {
		return transstationInformation;
	}
	public void setTransstationInformation(
			Map<String, List<TransstationInformation>> transstationInformation) {
		this.transstationInformation = transstationInformation;
	}
}
