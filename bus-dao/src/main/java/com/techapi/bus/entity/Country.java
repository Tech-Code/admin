package com.techapi.bus.entity;

/**
 * Created by xuefei on 6/9/14.
 */
public class Country implements java.io.Serializable{
    private String countryId;
    private String countryName;

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
