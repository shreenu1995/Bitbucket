package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class DeviceManufacturerListResponse {

	private List<DeviceManufacturerSearchRequest> listOfManufacturer;

	public List<DeviceManufacturerSearchRequest> getListOfManufacturer() {
		return listOfManufacturer;
	}

	public void setListOfManufacturer(List<DeviceManufacturerSearchRequest> listOfManufacturer) {
		this.listOfManufacturer = listOfManufacturer;
	}

}
