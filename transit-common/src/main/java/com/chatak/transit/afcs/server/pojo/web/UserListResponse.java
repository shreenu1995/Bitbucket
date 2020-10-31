package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class UserListResponse extends WebResponse {

	private static final long serialVersionUID = 8601873306493350853L;
	private List<String> listOfUser;

	public List<String> getListOfUser() {
		return listOfUser;
	}

	public void setListOfUser(List<String> listOfUser) {
		this.listOfUser = listOfUser;
	}

	@Override
	public String toString() {

		StringBuilder response = new StringBuilder();

		return response.append("UserListResponse [listOfUser=").append(listOfUser).append(", getListOfUser()=")
				.append(getListOfUser()).append(", getStatusCode()=").append(getStatusCode())
				.append(", getStatusMessage()=").append(getStatusMessage()).append("]").toString();

	}

}
