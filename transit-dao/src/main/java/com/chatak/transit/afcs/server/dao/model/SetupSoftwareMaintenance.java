package com.chatak.transit.afcs.server.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "software_maintenance_master")
@IdClass(value = IdForSetupSoftwareMaintenance.class)
public class SetupSoftwareMaintenance implements Serializable {

	private static final long serialVersionUID = 1543704154961700890L;

	@Id
	@SequenceGenerator(name = "seq_software_id_maintenance_master", sequenceName = "seq_software_id_maintenance_master")
	@GeneratedValue(generator = "seq_software_id_maintenance_master")
	@Column(name = "software_id")
	private Long softwareId;
	
	@Column(name = "organization_id")
	private Long organizationId;

	@Column(name = "pto_id")
	private Long ptoId;

	@Column(name = "version_number")
	private String versionNumber;

	@Column(name = "inheritt")
	private String inheritt;

	@Column(name = "full_version")
	private String fullVersion;

	@Column(name = "description")
	private String description;

	@Column(name = "delivery_date")
	private Timestamp deliveryDate;

	@Column(name = "apply_date")
	private Timestamp applyDate;

	@Column(name = "status")
	private String status;

	@Column(name = "created_time")
	private Timestamp createdTime;

	@Column(name = "updated_time")
	private Timestamp updatedTime;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	public Long getSoftwareId() {
		return softwareId;
	}

	public void setSoftwareId(Long softwareId) {
		this.softwareId = softwareId;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getInheritt() {
		return inheritt;
	}

	public void setInheritt(String inheritt) {
		this.inheritt = inheritt;
	}

	public String getFullVersion() {
		return fullVersion;
	}

	public void setFullVersion(String fullVersion) {
		this.fullVersion = fullVersion;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getPtoId() {
		return ptoId;
	}

	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Timestamp deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Timestamp getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Timestamp applyDate) {
		this.applyDate = applyDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public Timestamp getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
}

class IdForSetupSoftwareMaintenance implements Serializable {

	private static final long serialVersionUID = -5293418614184630808L;
	Long softwareId;
}
