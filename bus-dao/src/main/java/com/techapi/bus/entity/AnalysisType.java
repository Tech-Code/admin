package com.techapi.bus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.techapi.bus.annotation.ExcelField;

/***
 * 按照类型分析
 * @author jiayusun
 *
 */
@Entity
@Table(name="ANALYSIS_TYPE")
public class AnalysisType implements java.io.Serializable{
	@Id
    @GeneratedValue(generator = "UUIDGenerator")
    @GenericGenerator(name="UUIDGenerator",strategy="uuid")
	private String id;
	@ExcelField
	@Column(name="NAME")
	private String name;
	@ExcelField
	@Transient
	private String keyName;
	@Column(name="DAY")
	private String day;
	@ExcelField
	@Column(name="TYPE")
	private String type;
	@ExcelField
	@Column(name="TOTAL")
	private String total;
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
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
}
