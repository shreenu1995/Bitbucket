package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class OperatorResponse extends WebResponse {

	private List<OperatorSearchRequest> listOfOperators;

	private Long operatorId;

	private int totalRecords;

	private String operatorName;

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public List<OperatorSearchRequest> getListOfOperators() {
		return listOfOperators;
	}

	public void setListOfOperators(List<OperatorSearchRequest> listOfOperators) {
		this.listOfOperators = listOfOperators;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

}
