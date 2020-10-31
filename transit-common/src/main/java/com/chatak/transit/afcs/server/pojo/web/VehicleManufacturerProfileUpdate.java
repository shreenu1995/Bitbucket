package com.chatak.transit.afcs.server.pojo.web;

public class VehicleManufacturerProfileUpdate {

	private String userId;

	private int vehicleManufacturerId;

	private Long vehicleTypeId;

	private String vehicleManufacturerName;

	private String description;

	private String status;

	private Integer pageIndex;

	public int getVehicleManufacturerId() {
		return vehicleManufacturerId;
	}

	public void setVehicleManufacturerId(int vehicleManufacturerId) {
		this.vehicleManufacturerId = vehicleManufacturerId;
	}

	public Long getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(Long vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getVehicleManufacturerName() {
		return vehicleManufacturerName;
	}

	public void setVehicleManufacturerName(String vehicleManufacturerName) {
		this.vehicleManufacturerName = vehicleManufacturerName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehicleManufacturerProfileUpdate [userId=");
		builder.append(userId);
		builder.append(", vehicleManufacturerId=");
		builder.append(vehicleManufacturerId);
		builder.append(", vehicleTypeId=");
		builder.append(vehicleTypeId);
		builder.append(", vehicleManufacturerName=");
		builder.append(vehicleManufacturerName);
		builder.append(", description=");
		builder.append(description);
		builder.append(", status=");
		builder.append(status);
		builder.append(", pageIndex=");
		builder.append(pageIndex);
		builder.append("]");
		return builder.toString();
	}

}
