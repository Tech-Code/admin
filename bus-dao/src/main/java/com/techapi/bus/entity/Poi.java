package com.techapi.bus.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="BUS_POI")
public class Poi implements java.io.Serializable{

    @EmbeddedId
    private PoiPK poiPK;
    @Column(name = "ID")
    private String id;
    @Column(name="CITYCODE")
	private String cityCode;// IS '城市代码';
	@Column(name="POINAME")
	private String poiName;// IS '地标名称';
	@Column(name="POITYPE1")
	private String poiType1;// IS 'POI大类';
	@Column(name="POITYPE2")
	private String poiType2;// IS 'POI中类';
	@Column(name="POITYPE3")
	private String poiType3;// IS 'POI小类';
	@Column(name="POICOORDINATE")
	private String poiCoordinate;// IS 'POI坐标';
	@Column(name="WALKDISTANCE")
	private double walkDistance;// IS '距离站点的步行距离(米)';
	@Column(name="ORIENTATION")
	private String orientation;// IS '方位';
    @Column(name = "ADDRESS")
    private String address;// IS '地址';
    @Column(name = "TEL")
    private String tel;// IS '电话';
    @Transient
    private String cityName;

    public Poi(PoiPK poiPK, String id, String cityCode, String poiName, String poiType1, String poiType2, String poiType3, String poiCoordinate, double walkDistance, String orientation, String address, String tel, String cityName) {
        this.poiPK = poiPK;
        this.id = id;
        this.cityCode = cityCode;
        this.poiName = poiName;
        this.poiType1 = poiType1;
        this.poiType2 = poiType2;
        this.poiType3 = poiType3;
        this.poiCoordinate = poiCoordinate;
        this.walkDistance = walkDistance;
        this.orientation = orientation;
        this.address = address;
        this.tel = tel;
        this.cityName = cityName;
    }

    public PoiPK getPoiPK() {
        return poiPK;
    }

    public void setPoiPK(PoiPK poiPK) {
        this.poiPK = poiPK;
    }

    public String getId() {
        return id;
    }

    @PrePersist
    @PreUpdate
    public void setId() {
        this.id = UUID.randomUUID().toString();
    }

    public void setId(String id){
        this.id = id;
    }

    public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}


    public Poi() {
    }

    public Poi(String stationId,String poiId, String cityCode, String poiName, String poiType1, String poiType2, String poiType3, String poiCoordinate, double walkDistance, String orientation, String address, String tel) {
        this.poiPK = new PoiPK(stationId,poiId);
        this.cityCode = cityCode;
        this.poiName = poiName;
        this.poiType1 = poiType1;
        this.poiType2 = poiType2;
        this.poiType3 = poiType3;
        this.poiCoordinate = poiCoordinate;
        this.walkDistance = walkDistance;
        this.orientation = orientation;
        this.address = address;
        this.tel = tel;
    }


	public String getPoiName() {
		return poiName;
	}
	public void setPoiName(String poiName) {
		this.poiName = poiName;
	}
	public String getPoiType1() {
		return poiType1;
	}
	public void setPoiType1(String poiType1) {
		this.poiType1 = poiType1;
	}
	public String getPoiType2() {
		return poiType2;
	}
	public void setPoiType2(String poiType2) {
		this.poiType2 = poiType2;
	}
	public String getPoiType3() {
		return poiType3;
	}
	public void setPoiType3(String poiType3) {
		this.poiType3 = poiType3;
	}
	public String getPoiCoordinate() {
		return poiCoordinate;
	}
	public void setPoiCoordinate(String poiCoordinate) {
		this.poiCoordinate = poiCoordinate;
	}
	public double getWalkDistance() {
		return walkDistance;
	}
	public void setWalkDistance(double walkDistance) {
		this.walkDistance = walkDistance;
	}
	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Poi poi = (Poi) o;

        if (poiPK != null ? !poiPK.equals(poi.poiPK) : poi.poiPK != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return poiPK != null ? poiPK.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Poi{" +
                "poiPK=" + poiPK +
                ", id='" + id + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", poiName='" + poiName + '\'' +
                ", poiType1='" + poiType1 + '\'' +
                ", poiType2='" + poiType2 + '\'' +
                ", poiType3='" + poiType3 + '\'' +
                ", poiCoordinate='" + poiCoordinate + '\'' +
                ", walkDistance=" + walkDistance +
                ", orientation='" + orientation + '\'' +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
