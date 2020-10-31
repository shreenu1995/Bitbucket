package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class LoginSessionDetailsResponse {

	private List<LoginSessionDetailsRequest> loginSessionDetailsRequest;

	public List<LoginSessionDetailsRequest> getLoginSessionDetailsRequest() {
		return loginSessionDetailsRequest;
	}

	public void setLoginSessionDetailsRequest(List<LoginSessionDetailsRequest> loginSessionDetailsRequest) {
		this.loginSessionDetailsRequest = loginSessionDetailsRequest;
	}

}