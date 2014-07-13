package com.techapi.bus.entity;

import org.apache.solr.client.solrj.beans.Field;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="BUS_CITYSTATION")
public class CityStation implements java.io.Serializable{
    @Id
    @GeneratedValue(generator = "UUIDGenerator")
    @GenericGenerator(name="UUIDGenerator",strategy="uuid")
    @Field
    private String id;

    @Column(name="CITYCODE")
	private String cityCode;
	@Column(name="CITYNAME")
	private String cityName;
	@Column(name="TRANSTYPE")
	private String transType;
	@Column(name="STATIONNAME")
    @Field("cityStationName")
	private String cityStationName;
	@Column(name="TRANSDETAIL")
	private String transdetail;
	@Column(name="COORDINATE")
	private String coordinate;

    //@OneToMany(cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},mappedBy= "cityStation")
    //@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER,mappedBy= "cityStation")
    //@OneToMany(mappedBy = "cityStation")
    //private Set<Transstation> transstations = new HashSet<>();


    public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getCityStationName() {
		return cityStationName;
	}
	public void setCityStationName(String cityStationName) {
		this.cityStationName = cityStationName;
	}
	public String getTransdetail() {
		return transdetail;
	}
	public void setTransdetail(String transdetail) {
		this.transdetail = transdetail;
	}
	public String getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}



    //public Set<Transstation> getTransstations() {
    //    return transstations;
    //}
    //
    //public void setTransstations(Set<Transstation> transstations) {
    //    this.transstations = transstations;
    //}
    //
    //public void addTransstation(Transstation transstation) {
    //    this.transstations.add(transstation);
    //}
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CityStation that = (CityStation) o;

        if (cityCode != null ? !cityCode.equals(that.cityCode) : that.cityCode != null) return false;
        if (cityName != null ? !cityName.equals(that.cityName) : that.cityName != null) return false;
        if (coordinate != null ? !coordinate.equals(that.coordinate) : that.coordinate != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (cityStationName != null ? !cityStationName.equals(that.cityStationName) : that.cityStationName != null) return false;
        if (transType != null ? !transType.equals(that.transType) : that.transType != null) return false;
        if (transdetail != null ? !transdetail.equals(that.transdetail) : that.transdetail != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cityCode != null ? cityCode.hashCode() : 0);
        result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
        result = 31 * result + (transType != null ? transType.hashCode() : 0);
        result = 31 * result + (cityStationName != null ? cityStationName.hashCode() : 0);
        result = 31 * result + (transdetail != null ? transdetail.hashCode() : 0);
        result = 31 * result + (coordinate != null ? coordinate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return this.cityCode + this.cityName + "(" + this.coordinate + ")";
    }


}
