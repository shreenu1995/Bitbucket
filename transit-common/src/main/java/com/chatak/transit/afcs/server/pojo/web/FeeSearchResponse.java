package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class FeeSearchResponse extends WebResponse {

	private static final long serialVersionUID = -9107970405717117728L;

	private List<FeeSearchRequest> feeList;

	private int totalRecords;

	private String feeName;

	public String getFeeName() {
		return feeName;
	}

	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}

	public List<FeeSearchRequest> getFeeList() {
		return feeList;
	}

	public void setFeeList(List<FeeSearchRequest> feeList) {
		this.feeList = feeList;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

}
