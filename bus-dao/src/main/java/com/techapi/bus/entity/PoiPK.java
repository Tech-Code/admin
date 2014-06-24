package com.techapi.bus.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by xuefei on 6/21/14.
 */
@Embeddable
public class PoiPK implements Serializable {

    public PoiPK() {
    }

    public PoiPK(String stationId, String poiId) {
        this.stationId = stationId;
        this.poiId = poiId;
    }

    public PoiPK(String stationId) {
        this.stationId = stationId;
    }


    @Column(name = "STATIONID")
    private String stationId;// IS '站点ID';
    @Column(name = "POIID")
    private String poiId;// IS 'POIID';

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getPoiId() {
        return poiId;
    }

    public void setPoiId(String poiId) {
        this.poiId = poiId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PoiPK poiPK = (PoiPK) o;

        if (!poiId.equals(poiPK.poiId)) return false;
        if (!stationId.equals(poiPK.stationId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stationId.hashCode();
        result = 31 * result + poiId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PoiPK{" +
                "stationId='" + stationId + '\'' +
                ", poiId='" + poiId + '\'' +
                '}';
    }
}
