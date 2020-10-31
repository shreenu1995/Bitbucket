package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class BlacklistedAccountRequest implements Serializable {
	
	private static final long serialVersionUID = -1204448745286048125L;

	private String issuerName;

	private byte[] browseBlacklistedAccount;

	private String ptoName;

	private String account;

	private String reason;

	private int pageIndex;
	
	private Long issuerId;
	
	private int pageSize;
	
	private String blacklistedErrorCause;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Long getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(Long issuerId) {
		this.issuerId = issuerId;
	}

	public String getIssuerName() {
		return issuerName;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public byte[] getBrowseBlacklistedAccount() {
		return browseBlacklistedAccount;
	}

	public void setBrowseBlacklistedAccount(byte[] browseBlacklistedAccount) {
		this.browseBlacklistedAccount = browseBlacklistedAccount;
	}

	public String getPtoName() {
		return ptoName;
	}

	public void setPtoName(String ptoName) {
		this.ptoName = ptoName;
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

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getBlacklistedErrorCause() {
		return blacklistedErrorCause;
	}

	public void setBlacklistedErrorCause(String blacklistedErrorCause) {
		this.blacklistedErrorCause = blacklistedErrorCause;
	}

}
