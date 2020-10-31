package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import java.util.List;

public class StageResponse extends WebResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<StageSearchRequest> listOfStages;

	private int noOfRecords;

	private String stageName;

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public List<StageSearchRequest> getListOfStages() {
		return listOfStages;
	}

	public void setListOfStages(List<StageSearchRequest> listOfStages) {
		this.listOfStages = listOfStages;
	}

	public int getNoOfRecords() {
		return noOfRecords;
	}

	public void setNoOfRecords(int noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

}
