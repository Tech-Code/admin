package com.techapi.bus.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "BUS_POI")
public class Poi implements java.io.Serializable, Comparable<Poi> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "POIID")
	private String poiId;
	@Column(name = "CITYCODE")
	private String cityCode;// IS '城市代码';
	@Column(name = "POINAME")
	private String poiName;// IS '地标名称';
	@Column(name = "POITYPE1")
	private String poiType1;// IS 'POI大类';
	@Column(name = "POITYPE2")
	private String poiType2;// IS 'POI中类';
	@Column(name = "POITYPE3")
	private String poiType3;// IS 'POI小类';
	@Column(name = "POICOORDINATE")
	private String poiCoordinate;// IS 'POI坐标';
	@Column(name = "ADDRESS")
	private String address;// IS '地址';
	@Column(name = "TEL")
	private String tel;// IS '电话';
	@Transient
	private String cityName;
	@Column(name = "GRIDID")
	private String gridId;

	@Transient
	private double walkDistance;// 步行距离
	@Transient
	private String orientation;// 方向

	public Poi(String poiId, String cityCode, String poiName, String poiType1,
			String poiType2, String poiType3, String poiCoordinate,
			String address, String tel, String cityName) {
		this.poiId = poiId;
		this.cityCode = cityCode;
		this.poiName = poiName;
		this.poiType1 = poiType1;
		this.poiType2 = poiType2;
		this.poiType3 = poiType3;
		this.poiCoordinate = poiCoordinate;
		this.address = address;
		this.tel = tel;
		this.cityName = cityName;
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

	public String getPoiId() {
		return poiId;
	}

	public void setPoiId(String poiId) {
		if (poiId == null || poiId.isEmpty()) {
			this.poiId = UUID.randomUUID().toString();
		} else {
			this.poiId = poiId;
		}
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Poi() {
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

	public String getGridId() {
		return gridId;
	}

	public void setGridId(String gridId) {
		this.gridId = gridId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Poi poi = (Poi) o;

		if (address != null ? !address.equals(poi.address)
				: poi.address != null)
			return false;
		if (cityCode != null ? !cityCode.equals(poi.cityCode)
				: poi.cityCode != null)
			return false;
		if (cityName != null ? !cityName.equals(poi.cityName)
				: poi.cityName != null)
			return false;
		if (gridId != null ? !gridId.equals(poi.gridId) : poi.gridId != null)
			return false;
		if (poiCoordinate != null ? !poiCoordinate.equals(poi.poiCoordinate)
				: poi.poiCoordinate != null)
			return false;
		if (poiId != null ? !poiId.equals(poi.poiId) : poi.poiId != null)
			return false;
		if (poiName != null ? !poiName.equals(poi.poiName)
				: poi.poiName != null)
			return false;
		if (poiType1 != null ? !poiType1.equals(poi.poiType1)
				: poi.poiType1 != null)
			return false;
		if (poiType2 != null ? !poiType2.equals(poi.poiType2)
				: poi.poiType2 != null)
			return false;
		if (poiType3 != null ? !poiType3.equals(poi.poiType3)
				: poi.poiType3 != null)
			return false;
		if (tel != null ? !tel.equals(poi.tel) : poi.tel != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = poiId != null ? poiId.hashCode() : 0;
		result = 31 * result + (cityCode != null ? cityCode.hashCode() : 0);
		result = 31 * result + (poiName != null ? poiName.hashCode() : 0);
		result = 31 * result + (poiType1 != null ? poiType1.hashCode() : 0);
		result = 31 * result + (poiType2 != null ? poiType2.hashCode() : 0);
		result = 31 * result + (poiType3 != null ? poiType3.hashCode() : 0);
		result = 31 * result
				+ (poiCoordinate != null ? poiCoordinate.hashCode() : 0);
		result = 31 * result + (address != null ? address.hashCode() : 0);
		result = 31 * result + (tel != null ? tel.hashCode() : 0);
		result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
		result = 31 * result + (gridId != null ? gridId.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Poi{" + "poiId='" + poiId + '\'' + ", cityCode='" + cityCode
				+ '\'' + ", poiName='" + poiName + '\'' + ", poiType1='"
				+ poiType1 + '\'' + ", poiType2='" + poiType2 + '\''
				+ ", poiType3='" + poiType3 + '\'' + ", poiCoordinate='"
				+ poiCoordinate + '\'' + ", address='" + address + '\''
				+ ", tel='" + tel + '\'' + ", cityName='" + cityName + '\''
				+ ", gridId='" + gridId + '\'' + '}';
	}

	@Override
	public int compareTo(Poi o) {
		// TODO Auto-generated method stub
		return (int) (this.getWalkDistance() - o.getWalkDistance());
	}
}
