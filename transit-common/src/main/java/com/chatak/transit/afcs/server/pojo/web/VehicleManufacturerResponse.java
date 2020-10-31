package com.chatak.transit.afcs.server.pojo.web;

public class VehicleManufacturerResponse extends VehicleManufacturerSearchRequest {

	private static final long serialVersionUID = -9209288708769210936L;

	private Integer vehicleManufacturerId;

	private String vehicleType;

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Integer getVehicleManufacturerId() {
		return vehicleManufacturerId;
	}

	public void setVehicleManufacturerId(Integer vehicleManufacturerId) {
		this.vehicleManufacturerId = vehicleManufacturerId;
	}

}
