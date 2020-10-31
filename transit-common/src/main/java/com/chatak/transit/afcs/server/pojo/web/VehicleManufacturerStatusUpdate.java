package com.chatak.transit.afcs.server.pojo.web;

public class VehicleManufacturerStatusUpdate {

	private String userId;

	private Integer vehicleManufacturerId;

	private String vehicleType;

	private String vehicleManufacturerName;

	private String description;

	private String status;

	private Integer pageIndex;

	public Integer getVehicleManufacturerId() {
		return vehicleManufacturerId;
	}

	public void setVehicleManufacturerId(Integer vehicleManufacturerId) {
		this.vehicleManufacturerId = vehicleManufacturerId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getVehicleManufacturerName() {
		return vehicleManufacturerName;
	}

	public void setVehicleManufacturerName(String vehicleManufacturerName) {
		this.vehicleManufacturerName = vehicleManufacturerName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

}
