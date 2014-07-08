package com.techapi.bus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by xuefei on 6/8/14.
 */
@Entity
@Table(name = "BUS_STATION")
public class Station implements java.io.Serializable{
    @Id
    @Column(name = "STATIONID")
    private String stationId;
    @Column(name="STATIONNAME")
    private String stationName;
    @Column(name = "STATIONLON")
    private String stationLon;
    @Column(name = "STATIONLAT")
    private String stationLat;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationLon() {
        return stationLon;
    }

    public void setStationLon(String stationLon) {
        this.stationLon = stationLon;
    }

    public String getStationLat() {
        return stationLat;
    }

    public void setStationLat(String stationLat) {
        this.stationLat = stationLat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Station station = (Station) o;

        if (stationId != null ? !stationId.equals(station.stationId) : station.stationId != null) return false;
        if (stationLat != null ? !stationLat.equals(station.stationLat) : station.stationLat != null) return false;
        if (stationLon != null ? !stationLon.equals(station.stationLon) : station.stationLon != null) return false;
        if (stationName != null ? !stationName.equals(station.stationName) : station.stationName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stationId != null ? stationId.hashCode() : 0;
        result = 31 * result + (stationName != null ? stationName.hashCode() : 0);
        result = 31 * result + (stationLon != null ? stationLon.hashCode() : 0);
        result = 31 * result + (stationLat != null ? stationLat.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Station{" +
                "stationId='" + stationId + '\'' +
                ", stationName='" + stationName + '\'' +
                ", stationLon='" + stationLon + '\'' +
                ", stationLat='" + stationLat + '\'' +
                '}';
    }
}
