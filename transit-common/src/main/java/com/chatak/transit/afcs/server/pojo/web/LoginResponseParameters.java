package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class LoginResponseParameters extends WebResponse {

	private static final long serialVersionUID = -3930019059049991691L;

	private String message;

	private String email;

	private String userId;

	private String walletId;

	private String accessToken;

	private String refreshToken;

	private Long serviceProviderId;

	private Long subServiceProviderId;

	private List<Long> existingFeature;

	private Long userRoleId;

	private Boolean makerCheckerRequired;

	private String userType;

	private String userName;

	private Long loginMode;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Long getServiceProviderId() {
		return serviceProviderId;
	}

	public void setServiceProviderId(Long serviceProviderId) {
		this.serviceProviderId = serviceProviderId;
	}

	public Long getSubServiceProviderId() {
		return subServiceProviderId;
	}

	public void setSubServiceProviderId(Long subServiceProviderId) {
		this.subServiceProviderId = subServiceProviderId;
	}

	public List<Long> getExistingFeature() {
		return existingFeature;
	}

	public void setExistingFeature(List<Long> existingFeature) {
		this.existingFeature = existingFeature;
	}

	public Long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public Boolean getMakerCheckerRequired() {
		return makerCheckerRequired;
	}

	public void setMakerCheckerRequired(Boolean makerCheckerRequired) {
		this.makerCheckerRequired = makerCheckerRequired;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getLoginMode() {
		return loginMode;
	}

	public void setLoginMode(Long loginMode) {
		this.loginMode = loginMode;
	}

}