package com.chatak.transit.afcs.server.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transit_feature")
public class TransitFeature implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "feature_id")
	private Long featureId;

	@Column(name = "feature_level")
	private Long featureLevel;

	@Column(name = "name")
	private String name;

	@Column(name = "role_type")
	private String roleType;

	@Column(name = "status")
	private String status;

	@Column(name = "ref_feature_id")
	private Long refFeatureId;

	@Column(name = "created_date", updatable = false)
	private Timestamp createdDate;

	public Long getFeatureId() {
		return featureId;
	}

	public void setFeatureId(Long featureId) {
		this.featureId = featureId;
	}

	public Long getFeatureLevel() {
		return featureLevel;
	}

	public void setFeatureLevel(Long featureLevel) {
		this.featureLevel = featureLevel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getRefFeatureId() {
		return refFeatureId;
	}

	public void setRefFeatureId(Long refFeatureId) {
		this.refFeatureId = refFeatureId;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

}
