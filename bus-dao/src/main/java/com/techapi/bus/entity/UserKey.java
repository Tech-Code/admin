package com.techapi.bus.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="BUS_KEY")
public class UserKey implements java.io.Serializable{
    @Id
    @GeneratedValue(generator = "UUIDGenerator")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid")
    private String id;
    @Column(name = "CREATEDATE")
    private String createDate;
    @Column(name = "BUSINESSNAME")
    private String businessName;
    @Column(name = "BUSINESSSUBNAME")
    private String businessSubName;
    @Column(name = "BUSINESSFLAG")
    private String businessFlag;
    @Column(name = "BUSINESSTYPE")
    private String businessType;
    @Column(name = "USEDAPI")
    private String usedApi;
    @Column(name = "PROVINCE")
    private String province;
    @Column(name = "STATUS")
    private String status; // 正式
    @Column(name = "FIRM")
    private String firm;
    @Column(name = "BUSINESSURL")
    private String businessUrl;
    @Column(name = "KEY")
    private String key;
    @Column(name = "CONTACT")
    private String contact;
    @Column(name = "BUSINESSRESOURCE")
    private String businessResource;
    @Column(name = "SOURCE")
    private int source;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessSubName() {
        return businessSubName;
    }

    public void setBusinessSubName(String businessSubName) {
        this.businessSubName = businessSubName;
    }

    public String getBusinessFlag() {
        return businessFlag;
    }

    public void setBusinessFlag(String businessFlag) {
        this.businessFlag = businessFlag;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getUsedApi() {
        return usedApi;
    }

    public void setUsedApi(String usedApi) {
        this.usedApi = usedApi;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    public String getBusinessUrl() {
        return businessUrl;
    }

    public void setBusinessUrl(String businessUrl) {
        this.businessUrl = businessUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getBusinessResource() {
        return businessResource;
    }

    public void setBusinessResource(String businessResource) {
        this.businessResource = businessResource;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Key{" +
                "id='" + id + '\'' +
                ", createDate='" + createDate + '\'' +
                ", businessName='" + businessName + '\'' +
                ", businessSubName='" + businessSubName + '\'' +
                ", businessFlag='" + businessFlag + '\'' +
                ", businessType='" + businessType + '\'' +
                ", usedApi='" + usedApi + '\'' +
                ", province='" + province + '\'' +
                ", status='" + status + '\'' +
                ", firm='" + firm + '\'' +
                ", businessUrl='" + businessUrl + '\'' +
                ", key='" + key + '\'' +
                ", contact='" + contact + '\'' +
                ", businessResource='" + businessResource + '\'' +
                '}';
    }
}
