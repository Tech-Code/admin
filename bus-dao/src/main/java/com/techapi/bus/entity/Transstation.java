package com.techapi.bus.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="BUS_TRANSSTATION")
public class Transstation implements java.io.Serializable{

	@Id
    @GeneratedValue(generator = "UUIDGenerator")
    @GenericGenerator(name="UUIDGenerator",strategy="uuid")
    private String id;
	@Column(name="TRIPS")
	private String trips;//	班次	VARCHAR2(64)
	@Column(name="STATIONORDER")
	private int stationOrder;//	站序	NUMBER(6)
	@Column(name="ARRIVETIME")
	private String arriveTime;//	'到达时间--格式HH*60+mm'
	@Column(name="DEPARTTIME")
	private String departTime; //发车时间--格式HH*60+mm
	@Column(name="MILES")
	private Double miles;//	里程	DECIMAL(12,1)
	@Column(name="PRICE")
	private Double price;//	票价	DECIMAL(12,1)
    //@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @ManyToOne()
    @JoinColumn(name = "citystationid")
    private CityStation cityStation;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getTrips() {
		return trips;
	}
	public void setTrips(String trips) {
		this.trips = trips;
	}
	public int getStationOrder() {
		return stationOrder;
	}
	public void setStationOrder(int stationOrder) {
		this.stationOrder = stationOrder;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getDepartTime() {
		return departTime;
	}
	public void setDepartTime(String departTime) {
		this.departTime = departTime;
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


    public CityStation getCityStation() {
        return cityStation;
    }

    public void setCityStation(CityStation cityStation) {
        this.cityStation = cityStation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transstation that = (Transstation) o;

        if (stationOrder != that.stationOrder) return false;
        if (arriveTime != null ? !arriveTime.equals(that.arriveTime) : that.arriveTime != null) return false;
        if (cityStation != null ? !cityStation.equals(that.cityStation) : that.cityStation != null) return false;
        if (departTime != null ? !departTime.equals(that.departTime) : that.departTime != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (miles != null ? !miles.equals(that.miles) : that.miles != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (trips != null ? !trips.equals(that.trips) : that.trips != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (trips != null ? trips.hashCode() : 0);
        result = 31 * result + stationOrder;
        result = 31 * result + (arriveTime != null ? arriveTime.hashCode() : 0);
        result = 31 * result + (departTime != null ? departTime.hashCode() : 0);
        result = 31 * result + (miles != null ? miles.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (cityStation != null ? cityStation.hashCode() : 0);
        return result;
    }
}
