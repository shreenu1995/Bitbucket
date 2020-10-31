package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class TransitMasterSearchResponse extends WebResponse{

	private List<TransitMasterSearchRequest> transitMasterSearchList;
	
	private int totalRecords;

	public List<TransitMasterSearchRequest> getTransitMasterSearchList() {
		return transitMasterSearchList;
	}

	public void setTransitMasterSearchList(List<TransitMasterSearchRequest> transitMasterSearchList) {
		this.transitMasterSearchList = transitMasterSearchList;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
}
