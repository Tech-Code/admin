package com.techapi.bus.data;

import java.util.List;

public class CityTransstationRelation {

	private String transtype;
	private List<TransstationInformation> transstationInformation;
	public String getTranstype() {
		return transtype;
	}
	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}
	public List<TransstationInformation> getTransstationInformation() {
		return transstationInformation;
	}
	public void setTransstationInformation(
			List<TransstationInformation> transstationInformation) {
		this.transstationInformation = transstationInformation;
	}
}
