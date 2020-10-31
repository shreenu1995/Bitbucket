package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class VehicleManufacturerSearchRequest extends VehicleManufacturerSearchResponse implements Serializable {

	private static final long serialVersionUID = -8627701709036780586L;

	private int vehicleManufId;

	private Long vehicleTypeId;

	private String vehicleManufacturerName;

	private String description;

	private int pageIndex;

	private String status;
	
	private int pageSize;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getVehicleManufId() {
		return vehicleManufId;
	}

	public void setVehicleManufId(int vehicleManufId) {
		this.vehicleManufId = vehicleManufId;
	}

	public Long getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(Long vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVehicleManufacturerName() {
		return vehicleManufacturerName;
	}

	public void setVehicleManufacturerName(String vehicleManufacturerName) {
		this.vehicleManufacturerName = vehicleManufacturerName;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

}
