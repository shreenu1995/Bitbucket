package com.chatak.transit.afcs.server.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "fee_master")
public class FeeMaster implements Serializable {

	private static final long serialVersionUID = 6648630344900471873L;

	@Id
	@SequenceGenerator(name = "seq_fee_master_fee_id", sequenceName = "seq_fee_master_fee_id")
	@GeneratedValue(generator = "seq_fee_master_fee_id")

	@Column(name = "fee_id")
	private Long feeId;

	@Column(name = "fee_name")
	private String feeName;

	@Column(name = "pto_fee_type")
	private String ptoFeeType;

	@Column(name = "pto_fee_value")
	private String ptoFeeValue;

	@Column(name = "org_fee_type")
	private String orgFeeType;

	@Column(name = "org_fee_value")
	private String orgFeeValue;

	@Column(name = "pto_share_type")
	private String ptoShareType;

	@Column(name = "pto_share_value")
	private String ptoShareValue;

	@Column(name = "org_share_type")
	private String orgShareType;

	@Column(name = "org_share_value")
	private String orgShareValue;

	@Column(name = "status")
	private String status;

	@Column(name = "reason")
	private String reason;

	@Column(name = "organization_id")
	private Long organizationId;

	@Column(name = "pto_id")
	private Long ptoId;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getPtoShareType() {
		return ptoShareType;
	}

	public void setPtoShareType(String ptoShareType) {
		this.ptoShareType = ptoShareType;
	}

	public String getPtoShareValue() {
		return ptoShareValue;
	}

	public void setPtoShareValue(String ptoShareValue) {
		this.ptoShareValue = ptoShareValue;
	}

	public String getOrgShareType() {
		return orgShareType;
	}

	public void setOrgShareType(String orgShareType) {
		this.orgShareType = orgShareType;
	}

	public String getOrgShareValue() {
		return orgShareValue;
	}

	public void setOrgShareValue(String orgShareValue) {
		this.orgShareValue = orgShareValue;
	}

	public String getOrgFeeType() {
		return orgFeeType;
	}

	public void setOrgFeeType(String orgFeeType) {
		this.orgFeeType = orgFeeType;
	}

	public String getOrgFeeValue() {
		return orgFeeValue;
	}

	public void setOrgFeeValue(String orgFeeValue) {
		this.orgFeeValue = orgFeeValue;
	}

	public String getPtoFeeType() {
		return ptoFeeType;
	}

	public void setPtoFeeType(String ptoFeeType) {
		this.ptoFeeType = ptoFeeType;
	}

	public String getPtoFeeValue() {
		return ptoFeeValue;
	}

	public Long getFeeId() {
		return feeId;
	}

	public void setFeeId(Long feeId) {
		this.feeId = feeId;
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

	public void setPtoFeeValue(String ptoFeeValue) {
		this.ptoFeeValue = ptoFeeValue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFeeName() {
		return feeName;
	}

	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}

}
