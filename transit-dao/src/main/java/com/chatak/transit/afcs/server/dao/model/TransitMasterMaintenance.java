package com.chatak.transit.afcs.server.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "transit_master")
public class TransitMasterMaintenance implements Serializable {

	private static final long serialVersionUID = 1543704154961700890L;

	@Id
	@SequenceGenerator(name = "seq_transit_master", sequenceName = "seq_transit_master")
	@GeneratedValue(generator = "seq_transit_master")
	@Column(name = "transit_master_id")
	private Long transitMasterId;

	@Column(name = "organizationId")
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

	@Column(name = "reason")
	private String reason;

	public Long getTransitMasterId() {
		return transitMasterId;
	}

	public void setTransitMasterId(Long transitMasterId) {
		this.transitMasterId = transitMasterId;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
