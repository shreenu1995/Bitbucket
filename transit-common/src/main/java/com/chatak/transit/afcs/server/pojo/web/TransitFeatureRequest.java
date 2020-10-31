package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import java.sql.Timestamp;

public class TransitFeatureRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long featureId;
	private String name;
	private Long featureLevel;
	private Long refFeatureId;
	private String status;
	private Timestamp createdDate;
	private String roleFeatureId;
	private String roleType;

	public Long getFeatureId() {
		return featureId;
	}

	public void setFeatureId(Long featureId) {
		this.featureId = featureId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getFeatureLevel() {
		return featureLevel;
	}

	public void setFeatureLevel(Long featureLevel) {
		this.featureLevel = featureLevel;
	}

	public Long getRefFeatureId() {
		return refFeatureId;
	}

	public void setRefFeatureId(Long refFeatureId) {
		this.refFeatureId = refFeatureId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getRoleFeatureId() {
		return roleFeatureId;
	}

	public void setRoleFeatureId(String roleFeatureId) {
		this.roleFeatureId = roleFeatureId;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

}
