package com.techapi.bus.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by xuefei on 7/8/14.
 */
@Embeddable
public class PoiStationPK implements Serializable {
    public PoiStationPK() {
    }

    public PoiStationPK(String poiId, String adCode) {
        this.poiId = poiId;
        this.adCode = adCode;
    }

    @Column(name = "POIID")
    private String poiId;
    @Column(name = "ADCODE")
    private String adCode;

    public String getPoiId() {
        return poiId;
    }

    public void setPoiId(String poiId) {
        this.poiId = poiId;
    }

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PoiStationPK that = (PoiStationPK) o;

        if (adCode != null ? !adCode.equals(that.adCode) : that.adCode != null) return false;
        if (poiId != null ? !poiId.equals(that.poiId) : that.poiId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = poiId != null ? poiId.hashCode() : 0;
        result = 31 * result + (adCode != null ? adCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PoiStationPK{" +
                "poiId='" + poiId + '\'' +
                ", adCode='" + adCode + '\'' +
                '}';
    }
}
