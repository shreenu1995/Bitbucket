package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class VehicleManufacturerSearchResponse extends WebResponse {

	private static final long serialVersionUID = 1L;
	private List<VehicleManufacturerResponse> listOfVehicleManuf;
	private int totalNoOfCount;

	public List<VehicleManufacturerResponse> getListOfVehicleManuf() {
		return listOfVehicleManuf;
	}

	public void setListOfVehicleManuf(List<VehicleManufacturerResponse> listOfVehicleManuf) {
		this.listOfVehicleManuf = listOfVehicleManuf;
	}

	public int getTotalNoOfCount() {
		return totalNoOfCount;
	}

	public void setTotalNoOfCount(int totalNoOfCount) {
		this.totalNoOfCount = totalNoOfCount;
	}

}
