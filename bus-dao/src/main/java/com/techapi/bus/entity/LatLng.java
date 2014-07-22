package com.techapi.bus.entity;

import org.apache.commons.lang.StringUtils;

public class LatLng {
	private float lat;
	private float lng;
	private float altitude;

	public LatLng() {
	};

	/**
	 * 将字符串解析为经纬度，格式：纬度,经度
	 * 
	 * @param latlng
	 * @return
	 */
	public static LatLng fromString(String latlng) {
		if (StringUtils.isNotBlank(latlng) && StringUtils.contains(latlng, ",")) {
			String[] items = StringUtils.split(latlng, ",");
			try {
				if (items.length >= 3)
					return new LatLng(Float.parseFloat(items[0]),
							Float.parseFloat(items[1]),
							Float.parseFloat(items[2]));
				else if (items.length >= 2)
					return new LatLng(Float.parseFloat(items[0]),
							Float.parseFloat(items[1]));
			} catch (Exception ex) {
			}
		}
		return null;
	}

	public LatLng(float lat, float lng) {
		this.lat = lat;
		this.lng = lng;
	}

	public LatLng(float lat, float lng, float altitude) {
		this(lat, lng);
		this.setAltitude(altitude);
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLng() {
		return lng;
	}

	public void setLng(float lng) {
		this.lng = lng;
	}

	public float getAltitude() {
		return altitude;
	}

	public void setAltitude(float altitude) {
		this.altitude = altitude;
	}

	public float[] toArray() {
		return new float[] { this.lat, this.lng };
	}

	@Override
	public String toString() {
		return this.lng + "," + this.lat;
	}
}
