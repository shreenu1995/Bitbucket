package com.chatak.transit.afcs.server.dao.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "login_session_details")
public class LoginSessionDetails {

	@Id
	@SequenceGenerator(name = "seq_login_session_detail_id", sequenceName = "SEQ_LOGIN_SESSION_DETAILS")
	@GeneratedValue(generator = "seq_login_session_detail_id")
	@Column(name = "id")
	private Long id;

	@Column(name = "session_id")
	private String sessionId;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "login_time")
	private Timestamp loginTime;

	@Column(name = "logout_time")
	private Timestamp logoutTime;

	@Column(name = "last_activity_time")
	private Timestamp lastActivityTime;

	@Column(name = "ip_address")
	private String ipAddress;

	@Column(name = "browser_type")
	private String browserType;

	@Column(name = "login_status")
	private String loginStatus;

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
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

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getBrowserType() {
		return browserType;
	}

	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}

}
