package com.techapi.bus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description
 * @Author hongjia.hu
 * @Date 2014-6-13
 */
@Entity
//@Table(name = "T_AREA", schema = "bussys")
@Table(name = "T_AREA")
public class TArea {
    @Id
    @Column(name = "AREA_ID")
    private long AREA_ID;

    @Column(name = "PARENT_ID")
    private long PARENT_ID;

    @Column(name = "AREA_NAME")
    private String AREA_NAME;

    @Column(name = "SERIAL_NUMBER")
    private int SERIAL_NUMBER;

    @Column(name = "AD_CODE")
    private long AD_CODE;

    @Column(name = "AREA_CODE")
    private String AREA_CODE;

    @Column(name = "AREA_TYPE")
    private String AREA_TYPE;

    @Column(name = "DELETE_FLAG")
    private String DELETE_FLAG;

    public long getAREA_ID() {
        return AREA_ID;
    }

    public void setAREA_ID(long aREA_ID) {
        AREA_ID = aREA_ID;
    }

    public long getPARENT_ID() {
        return PARENT_ID;
    }

    public void setPARENT_ID(long pARENT_ID) {
        PARENT_ID = pARENT_ID;
    }

    public String getAREA_NAME() {
        return AREA_NAME;
    }

    public void setAREA_NAME(String aREA_NAME) {
        AREA_NAME = aREA_NAME;
    }

    public int getSERIAL_NUMBER() {
        return SERIAL_NUMBER;
    }

    public void setSERIAL_NUMBER(int sERIAL_NUMBER) {
        SERIAL_NUMBER = sERIAL_NUMBER;
    }

    public long getAD_CODE() {
        return AD_CODE;
    }

    public void setAD_CODE(long aD_CODE) {
        AD_CODE = aD_CODE;
    }

    public String getAREA_CODE() {
        return AREA_CODE;
    }

    public void setAREA_CODE(String aREA_CODE) {
        AREA_CODE = aREA_CODE;
    }

    public String getAREA_TYPE() {
        return AREA_TYPE;
    }

    public void setAREA_TYPE(String aREA_TYPE) {
        AREA_TYPE = aREA_TYPE;
    }

    public String getDELETE_FLAG() {
        return DELETE_FLAG;
    }

    public void setDELETE_FLAG(String dELETE_FLAG) {
        DELETE_FLAG = dELETE_FLAG;
    }
}
