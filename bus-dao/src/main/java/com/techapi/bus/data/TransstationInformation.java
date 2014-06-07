package com.techapi.bus.data;


public class TransstationInformation {

	private String transtype;//	交通工具类型	VARCHAR2(64)
	private String trips;//	班次	VARCHAR2(64)
	private String transdetail;//	交通工具小类	VARCHAR2(64)
	private String citycode;//	城市代码	VARCHAR2(64)
	private String station;//	站点	VARCHAR2(64)
	private String stationorder;//	站序	NUMBER(6)
	private String coordinate;//	站点坐标	VARCHAR2(64)
	private String arrivetime;//	'到达时间--格式HH*60+mm'
	private String departtime; //发车时间--格式HH*60+mm
	private Double miles;//	里程	DECIMAL(12,1)
	private Double price;//	票价	DECIMAL(12,1)
	private String cityname;//城市名称
	
	
	public TransstationInformation(String transtype, String trips,
			String transdetail, String citycode, String station,
			String stationorder, String coordinate, String arrivetime,
			String departtime, Double miles, Double price, String cityname) {
		this.transtype = transtype;
		this.trips = trips;
		this.transdetail = transdetail;
		this.citycode = citycode;
		this.station = station;
		this.stationorder = stationorder;
		this.coordinate = coordinate;
		this.arrivetime = arrivetime;
		this.departtime = departtime;
		this.miles = miles;
		this.price = price;
		this.cityname = cityname;
	}
	public String getTranstype() {
		return transtype;
	}
	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}
	public String getTrips() {
		return trips;
	}
	public void setTrips(String trips) {
		this.trips = trips;
	}
	public String getTransdetail() {
		return transdetail;
	}
	public void setTransdetail(String transdetail) {
		this.transdetail = transdetail;
	}
	public String getCitycode() {
		return citycode;
	}
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getStationorder() {
		return stationorder;
	}
	public void setStationorder(String stationorder) {
		this.stationorder = stationorder;
	}
	public String getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	public String getArrivetime() {
		return arrivetime;
	}
	public void setArrivetime(String arrivetime) {
		this.arrivetime = arrivetime;
	}
	public String getDeparttime() {
		return departtime;
	}
	public void setDeparttime(String departtime) {
		this.departtime = departtime;
	}
	public Double getMiles() {
		return miles;
	}
	public void setMiles(Double miles) {
		this.miles = miles;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getCityname() {
		return cityname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
}
