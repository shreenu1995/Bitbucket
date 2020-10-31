package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class IssuerResponse extends WebResponse {

	private static final long serialVersionUID = 13123L;

	private int totalNoOfRecords;

	private List<IssuerRequest> listOfAfcsIssuer;

	private String issuerName;

	public String getIssuerName() {
		return issuerName;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public int getTotalNoOfRecords() {
		return totalNoOfRecords;
	}

	public void setTotalNoOfRecords(int totalNoOfRecords) {
		this.totalNoOfRecords = totalNoOfRecords;
	}

	public List<IssuerRequest> getListOfAfcsIssuer() {
		return listOfAfcsIssuer;
	}

	public void setListOfAfcsIssuer(List<IssuerRequest> listOfAfcsIssuer) {
		this.listOfAfcsIssuer = listOfAfcsIssuer;
	}
}
