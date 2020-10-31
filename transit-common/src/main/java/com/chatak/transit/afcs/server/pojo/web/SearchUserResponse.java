package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class SearchUserResponse extends WebResponse {

	private static final long serialVersionUID = 1L;

	private List<UserData> listUser;
	
	private int totalRecords;

	public List<UserData> getListUser() {
		return listUser;
	}

	public void setListUser(List<UserData> listUser) {
		this.listUser = listUser;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SearchUserResponse [listUser=");
		builder.append(listUser);
		builder.append("]");
		return builder.toString();
	}
}
