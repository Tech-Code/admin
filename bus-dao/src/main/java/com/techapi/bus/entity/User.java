package com.techapi.bus.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="BUS_USER")
public class User implements java.io.Serializable{
    @Id
    @GeneratedValue(generator = "UUIDGenerator")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid")
	private String id;
	@Column(name="USERNAME")
	private String userName;
    @Column(name = "LOGINNAME")
    private String loginName;
	@Column(name="PASSWORD")
	private String password;
	@Column(name="TIME")
	private String time;
	@Column(name="EMAIL")
	private String email;
    @Column(name = "ROLE")
    private String role;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
