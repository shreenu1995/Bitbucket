/**
 * @author Girmiti Software
 *
 */

package com.chatak.transit.afcs.server.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "fare_master")
public class FareMaster implements Serializable {

	private static final long serialVersionUID = 9127308932622052133L;

	@Id
	@SequenceGenerator(name = "seq_fare_master_fare_id", sequenceName = "seq_fare_master_fare_id")
	@GeneratedValue(generator = "seq_fare_master_fare_id")

	@Column(name = "fare_id")
	private Long fareId;

	@Column(name = "fare_name")
	private String fareName;

	@Column(name = "fare_type")
	private String fareType;

	@Column(name = "difference")
	private String difference;

	@Column(name = "fare_amount")
	private double fareAmount;

	@Column(name = "status")
	private String status;

	@Column(name = "reason")
	private String reason;

	@Column(name = "organization_id")
	private Long organizationId;

	@Column(name = "pto_id")
	private Long ptoId;

	public Long getFareId() {
		return fareId;
	}

	public void setFareId(Long fareId) {
		this.fareId = fareId;
	}

	public String getFareName() {
		return fareName;
	}

	public void setFareName(String fareName) {
		this.fareName = fareName;
	}

	public String getFareType() {
		return fareType;
	}

	public void setFareType(String fareType) {
		this.fareType = fareType;
	}

	public String getDifference() {
		return difference;
	}

	public void setDifference(String difference) {
		this.difference = difference;
	}

	public double getFareAmount() {
		return fareAmount;
	}

	public void setFareAmount(double fareAmount) {
		this.fareAmount = fareAmount;
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

}
