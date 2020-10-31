package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class PgResponse extends WebResponse {

	private static final long serialVersionUID = 13123L;

	private int totalNoOfRecords;
	
	private String pgName;

	public String getPgName() {
		return pgName;
	}

	public void setPgName(String pgName) {
		this.pgName = pgName;
	}

	private List<PgRequest> listOfAfcsPg;

	public int getTotalNoOfRecords() {
		return totalNoOfRecords;
	}

	public List<PgRequest> getListOfAfcsPg() {
		return listOfAfcsPg;
	}

	public void setTotalNoOfRecords(int totalNoOfRecords) {
		this.totalNoOfRecords = totalNoOfRecords;
	}

	public void setListOfAfcsPg(List<PgRequest> listOfAfcsPg) {
		this.listOfAfcsPg = listOfAfcsPg;
	}

}
