package com.techapi.bus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

@Entity
@Table(name = "server")
public class Server {

    @Id
    private String id;
	private String name;
	private String nodeId;
    
    @Column(unique = true)
	private String logURL;

	public Server() {
	}

	public Server(String serverName, String nodeId, String logURL) {
		this.name = serverName;
		this.nodeId = nodeId;
		this.logURL = logURL;
	}

	public String getServerFullName() {
		return StringUtils.isEmpty(nodeId) ? name : name + "-" + nodeId;
	}

	@PrePersist
	public void setId() {
		this.id = UUID.randomUUID().toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name.toLowerCase();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNodeId() {
		return nodeId.toLowerCase();
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getLogURL() {
		return logURL;
	}

	public void setLogURL(String logURL) {
		this.logURL = logURL;
	}

	@Override
	public String toString() {
		return "Server{" + "name='" + name + '\'' + ", nodeId='" + nodeId + '\'' + ", logURL='" + logURL + '\'' + '}';
	}
}
