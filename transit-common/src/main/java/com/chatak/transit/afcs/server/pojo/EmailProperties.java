package com.chatak.transit.afcs.server.pojo;

public class EmailProperties {
	
	private String userName;
	
	private String pswd;
	
	private String smptHost;
	
	private String smtpStarttlsEnable;
	
	private String smptPort;
	
	private String fromAddress;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPswd() {
		return pswd;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}

	public String getSmptHost() {
		return smptHost;
	}

	public void setSmptHost(String smptHost) {
		this.smptHost = smptHost;
	}

	public String getSmtpStarttlsEnable() {
		return smtpStarttlsEnable;
	}

	public void setSmtpStarttlsEnable(String smtpStarttlsEnable) {
		this.smtpStarttlsEnable = smtpStarttlsEnable;
	}

	public String getSmptPort() {
		return smptPort;
	}

	public void setSmptPort(String smptPort) {
		this.smptPort = smptPort;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
}
