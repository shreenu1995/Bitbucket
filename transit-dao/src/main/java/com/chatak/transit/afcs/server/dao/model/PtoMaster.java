/**
 * @author Girmiti Software
 *
 */
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
@Table(name = "pto_master")
public class PtoMaster implements Serializable {

	private static final long serialVersionUID = -1840023577939172431L;

	@Id
	@SequenceGenerator(name = "seq_pto_master", sequenceName = "seq_pto_master")
	@GeneratedValue(generator = "seq_pto_master")
	@Column(name = "pto_master_id")
	private Long ptoMasterId;

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "pto_name")
	private String ptoName;

	@Column(name = "contact_person")
	private String contactPerson;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "state")
	private String state;

	@Column(name = "city")
	private String city;

	@Column(name = "pto_mobile")
	private String ptoMobile;

	@Column(name = "pto_email")
	private String ptoEmail;

	@Column(name = "site_url")
	private String siteUrl;

	@Column(name = "status")
	private String status;

	@Column(name = "reason")
	private String reason;
	
	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "created_date_time")
	private Timestamp createdDateTime;

	@Column(name = "updated_date_time")
	private Timestamp updatedDateTime;

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPtoMobile() {
		return ptoMobile;
	}

	public void setPtoMobile(String ptoMobile) {
		this.ptoMobile = ptoMobile;
	}

	public String getPtoEmail() {
		return ptoEmail;
	}

	public void setPtoEmail(String ptoEmail) {
		this.ptoEmail = ptoEmail;
	}

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getPtoName() {
		return ptoName;
	}

	public void setPtoName(String ptoName) {
		this.ptoName = ptoName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getPtoMasterId() {
		return ptoMasterId;
	}

	public void setPtoMasterId(Long ptoMasterId) {
		this.ptoMasterId = ptoMasterId;
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

	public Timestamp getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Timestamp createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public Timestamp getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(Timestamp updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

}
