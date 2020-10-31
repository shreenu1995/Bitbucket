package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class VehicleModelSearchResponse extends WebResponse {

	private static final long serialVersionUID = 1L;
	private List<VehicleModelResponse> listOfVehicleModel;
	private int totalNoOfRecords;
	private String vehicleModelName;

	public String getVehicleModelName() {
		return vehicleModelName;
	}

	public void setVehicleModelName(String vehicleModelName) {
		this.vehicleModelName = vehicleModelName;
	}

	public List<VehicleModelResponse> getListOfVehicleModel() {
		return listOfVehicleModel;
	}

	public void setListOfVehicleModel(List<VehicleModelResponse> listOfVehicleModel) {
		this.listOfVehicleModel = listOfVehicleModel;
	}

	public int getTotalNoOfRecords() {
		return totalNoOfRecords;
	}

	public void setTotalNoOfRecords(int totalNoOfRecords) {
		this.totalNoOfRecords = totalNoOfRecords;
	}

}
