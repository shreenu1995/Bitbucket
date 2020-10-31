package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class VehicleTypeSearchResponse extends WebResponse {

	private static final long serialVersionUID = 1L;

	private int totalNoOfRecords;
	
	private List<VehicleTypeSearchRequest> listOfVehicleType;

	public List<VehicleTypeSearchRequest> getListOfVehicleType() {
		return listOfVehicleType;
	}

	public void setListOfVehicleType(List<VehicleTypeSearchRequest> listOfVehicleType) {
		this.listOfVehicleType = listOfVehicleType;
	}

	public int getTotalNoOfRecords() {
		return totalNoOfRecords;
	}

	public void setTotalNoOfRecords(int totalNoOfRecords) {
		this.totalNoOfRecords = totalNoOfRecords;
	}

}
