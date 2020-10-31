package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class VehicleModelListResponse extends WebResponse {

	private List<VehicleModelResponse> listofModel;

	private List<VehicleManufacturerResponse> listofVehicleManufacturer;

	private int noOfRecords;

	public int getNoOfRecords() {
		return noOfRecords;
	}

	public void setNoOfRecords(int noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

	public List<VehicleModelResponse> getListofModel() {
		return listofModel;
	}

	public void setListofModel(List<VehicleModelResponse> listofModel) {
		this.listofModel = listofModel;
	}

	public List<VehicleManufacturerResponse> getListofVehicleManufacturer() {
		return listofVehicleManufacturer;
	}

	public void setListofVehicleManufacturer(List<VehicleManufacturerResponse> listofVehicleManufacturer) {
		this.listofVehicleManufacturer = listofVehicleManufacturer;
	}

}
