package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class MasterFileDownloadResponse extends WebResponse {

	private static final long serialVersionUID = 13123L;

	private int totalNoOfRecords;

	private List<MasterFileDownloadRequest> listOfAfceMasterFileReport;

	public int getTotalNoOfRecords() {
		return totalNoOfRecords;
	}

	public List<MasterFileDownloadRequest> getListOfAfceMasterFileReport() {
		return listOfAfceMasterFileReport;
	}

	public void setTotalNoOfRecords(int totalNoOfRecords) {
		this.totalNoOfRecords = totalNoOfRecords;
	}

	public void setListOfAfceMasterFileReport(List<MasterFileDownloadRequest> listOfAfceMasterFileReport) {
		this.listOfAfceMasterFileReport = listOfAfceMasterFileReport;
	}

}
