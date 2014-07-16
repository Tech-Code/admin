package com.techapi.bus.entity;

import org.apache.solr.client.solrj.beans.Field;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by xuefei on 6/8/14.
 */

@Entity
@Table(name = "BUS_POISTATION")
public class PoiStation implements java.io.Serializable{

    @EmbeddedId
    private PoiStationPK poiStationPK;
    @Column(name = "ID")
    @Field
    private String id;
    @Column(name = "POINAME")
    @Field("poiStationName")
    private String poiStationName;
    @Column(name = "POITYPE")
    @Field("poiType")
    private String poiType;
    @Column(name = "POICOORDINATE")
    @Field("poiCoordinate")
    private String poiCoordinate;
    @Column(name = "ADNAME")
    @Field("adName")
    private String adName;
    @Column(name = "ADCODEFORSOLR")
    @Field("adCodeForSolr")
    private String adCodeForSolr;

    public String getId() {
        return id;
    }
    @PrePersist
    public void setId() {
        this.id = UUID.randomUUID().toString();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getPoiStationName() {
        return poiStationName;
    }

    public void setPoiStationName(String poiStationName) {
        this.poiStationName = poiStationName;
    }

    public String getPoiCoordinate() {
        return poiCoordinate;
    }

    public void setPoiCoordinate(String poiCoordinate) {
        this.poiCoordinate = poiCoordinate;
    }

    public String getPoiType() {
        return poiType;
    }

    public void setPoiType(String poiType) {
        this.poiType = poiType;
    }

    public PoiStationPK getPoiStationPK() {
        return poiStationPK;
    }

    public void setPoiStationPK(PoiStationPK poiStationPK) {
        this.poiStationPK = poiStationPK;
    }

    public String getAdCodeForSolr() {
        return adCodeForSolr;
    }

    public void setAdCodeForSolr(String adCodeForSolr) {
        this.adCodeForSolr = adCodeForSolr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PoiStation that = (PoiStation) o;

        if (adCodeForSolr != null ? !adCodeForSolr.equals(that.adCodeForSolr) : that.adCodeForSolr != null)
            return false;
        if (adName != null ? !adName.equals(that.adName) : that.adName != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (poiCoordinate != null ? !poiCoordinate.equals(that.poiCoordinate) : that.poiCoordinate != null)
            return false;
        if (poiStationName != null ? !poiStationName.equals(that.poiStationName) : that.poiStationName != null)
            return false;
        if (poiStationPK != null ? !poiStationPK.equals(that.poiStationPK) : that.poiStationPK != null) return false;
        if (poiType != null ? !poiType.equals(that.poiType) : that.poiType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = poiStationPK != null ? poiStationPK.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (poiStationName != null ? poiStationName.hashCode() : 0);
        result = 31 * result + (poiType != null ? poiType.hashCode() : 0);
        result = 31 * result + (poiCoordinate != null ? poiCoordinate.hashCode() : 0);
        result = 31 * result + (adName != null ? adName.hashCode() : 0);
        result = 31 * result + (adCodeForSolr != null ? adCodeForSolr.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PoiStation{" +
                "poiStationPK=" + poiStationPK +
                ", id='" + id + '\'' +
                ", poiStationName='" + poiStationName + '\'' +
                ", poiType='" + poiType + '\'' +
                ", poiCoordinate='" + poiCoordinate + '\'' +
                ", adName='" + adName + '\'' +
                ", adCodeForSolr='" + adCodeForSolr + '\'' +
                '}';
    }
}
