package com.techapi.bus.entity;

import java.util.List;

/**
 * Created by xuefei on 6/9/14.
 */
public class Province implements java.io.Serializable{
    private String provinceName;
    private String provinceId;
    private List<City> cityList;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }
}
