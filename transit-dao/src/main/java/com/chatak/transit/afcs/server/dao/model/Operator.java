package com.chatak.transit.afcs.server.dao.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "operator_management")
public class Operator implements Serializable {

	private static final long serialVersionUID = -1840023577939172431L;

	@Id
	@SequenceGenerator(name = "seq_operator_master_id", sequenceName = "seq_operator_master_id")
	@GeneratedValue(generator = "seq_operator_master_id")
	@Column(name = "operatorId")
	private Long operatorId;

	@Column(name = "organization_id")
	private Long organizationId;

	@Column(name = "pto_id")
	private Long ptoId;

	@Column(name = "operator_name")
	private String operatorName;

	@Column(name = "operator_contact_number")
	private String operatorContactNumber;

	@Column(name = "status")
	private String status;

	@Column(name = "reason")
	private String reason;
	
	@Column(name = "operator_user_id")
	private String operatorUserId;
	
	@Column(name = "operator_password")
	private String operatorPassword;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name= "operator_id", referencedColumnName = "operatorId")
    private Set<BatchSummary> batchSummaryData;
	
	public Set<BatchSummary> getbatchSummaryData() {
		return batchSummaryData;
	}

	public void setbatchSummaryData(Set<BatchSummary> batchSummaryData) {
		this.batchSummaryData = batchSummaryData;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperatorContactNumber() {
		return operatorContactNumber;
	}

	public void setOperatorContactNumber(String operatorContactNumber) {
		this.operatorContactNumber = operatorContactNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperatorUserId() {
		return operatorUserId;
	}

	public void setOperatorUserId(String operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public String getOperatorPassword() {
		return operatorPassword;
	}

	public void setOperatorPassword(String operatorPassword) {
		this.operatorPassword = operatorPassword;
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
