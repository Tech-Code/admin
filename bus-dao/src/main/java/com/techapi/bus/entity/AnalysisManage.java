package com.techapi.bus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="ANALYSIS_MANAGE")
public class AnalysisManage {
	@Id
    @GeneratedValue(generator = "UUIDGenerator")
    @GenericGenerator(name="UUIDGenerator",strategy="uuid")
	private String id;
	@Column(name="NAME")
	private String name;
	@Column(name="EVERTIME")
	private String evertime;
	@Column(name="URL")
	private String url;
	@Column(name="STATUS_CODE")
	private String statusCode;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEvertime() {
		return evertime;
	}
	public void setEvertime(String evertime) {
		this.evertime = evertime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
}
