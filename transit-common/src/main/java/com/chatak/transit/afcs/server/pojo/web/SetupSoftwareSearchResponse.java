package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class SetupSoftwareSearchResponse extends WebResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8023328642068436899L;

	private List<SetupSoftwareSearchRequest> setupSoftwareSearchList;

	private int totalRecords;

	public List<SetupSoftwareSearchRequest> getSetupSoftwareSearchList() {
		return setupSoftwareSearchList;
	}

	public void setSetupSoftwareSearchList(List<SetupSoftwareSearchRequest> setupSoftwareSearchList) {
		this.setupSoftwareSearchList = setupSoftwareSearchList;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

}
