package com.chatak.transit.afcs.server.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "blacklisted_account")
public class BlacklistedAccount implements Serializable{

	private static final long serialVersionUID = 9160749405323278466L;

	@Id
	@SequenceGenerator(name = "seq_blacklisted_account", sequenceName = "seq_blacklisted_account")
	@GeneratedValue(generator = "seq_blacklisted_account")
	@Column(name = "blacklisted_id")
	private Long blacklistedId;
	
	@Column(name = "issuer_id")
	private Long issuerId;
	
	@Column(name = "issuer_name")
	private String issuerName;
	
	@Column(name = "pto")
	private String pto;
	
	@Column(name = "account")
	private String account;
	
	@Column(name = "reason")
	private String reason;

	public Long getBlacklistedId() {
		return blacklistedId;
	}

	public void setBlacklistedId(Long blacklistedId) {
		this.blacklistedId = blacklistedId;
	}

	public Long getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(Long issuerId) {
		this.issuerId = issuerId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getReason() {
		return reason;
	}

	public String getIssuerName() {
		return issuerName;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public String getPto() {
		return pto;
	}

	public void setPto(String pto) {
		this.pto = pto;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
