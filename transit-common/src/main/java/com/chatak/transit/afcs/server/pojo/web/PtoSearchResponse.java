package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class PtoSearchResponse extends WebResponse {

	private static final long serialVersionUID = 1L;

	private List<PtoSearchRequest> ptosearchList;

	private int totalRecords;

	private String ptoName;

	public String getPtoName() {
		return ptoName;
	}

	public void setPtoName(String ptoName) {
		this.ptoName = ptoName;
	}

	public List<PtoSearchRequest> getPtosearchList() {
		return ptosearchList;
	}

	public void setPtosearchList(List<PtoSearchRequest> ptosearchList) {
		this.ptosearchList = ptosearchList;
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
		builder.append("PtoOperationSearchResponse [ptoOperationList=");
		builder.append(ptosearchList);
		builder.append("]");
		return builder.toString();
	}

}
