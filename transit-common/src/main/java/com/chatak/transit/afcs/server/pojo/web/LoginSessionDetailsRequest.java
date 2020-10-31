package com.chatak.transit.afcs.server.pojo.web;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class LoginSessionDetailsRequest {

	private String userId;

	private Long id;

	private String sessionId;

	private String userName;
	
	private String jSession;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private Timestamp loginTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private Timestamp logoutTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private Timestamp lastActivityTime;

	private String loginStatus;

	private String ipAddress;

	private String browserName;

	public String getBrowserName() {
		return browserName;
	}

	public String getjSession() {
		return jSession;
	}

	public void setjSession(String jSession) {
		this.jSession = jSession;
	}

	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Timestamp getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}

	public Timestamp getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Timestamp logoutTime) {
		this.logoutTime = logoutTime;
	}

	public Timestamp getLastActivityTime() {
		return lastActivityTime;
	}

	public void setLastActivityTime(Timestamp lastActivityTime) {
		this.lastActivityTime = lastActivityTime;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}