package com.chatak.transit.afcs.server.pojo.web;

public class VehicleModelRegistrationRequest {

	private String vehicleType;

	private String vehicleManufacturerName;

	private String vehicleModelName;

	private String vehicleRegistrationNumber;

	private String vehicleEngineNumber;

	private String vehicleChassisNumber;

	private String description;

	private String status;

	private Long vehicleTypeId;

	private Integer vehicleManufacturerId;

	public Long getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(Long vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public Integer getVehicleManufacturerId() {
		return vehicleManufacturerId;
	}

	public void setVehicleManufacturerId(Integer vehicleManufacturerId) {
		this.vehicleManufacturerId = vehicleManufacturerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getVehicleModelName() {
		return vehicleModelName;
	}

	public void setVehicleModelName(String vehicleModelName) {
		this.vehicleModelName = vehicleModelName;
	}

	public String getVehicleRegistrationNumber() {
		return vehicleRegistrationNumber;
	}

	public void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
		this.vehicleRegistrationNumber = vehicleRegistrationNumber;
	}

	public String getVehicleEngineNumber() {
		return vehicleEngineNumber;
	}

	public void setVehicleEngineNumber(String vehicleEngineNumber) {
		this.vehicleEngineNumber = vehicleEngineNumber;
	}

	public String getVehicleChassisNumber() {
		return vehicleChassisNumber;
	}

	public void setVehicleChassisNumber(String vehicleChassisNumber) {
		this.vehicleChassisNumber = vehicleChassisNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehicleModelRegistrationRequest [vehicleType=");
		builder.append(vehicleType);
		builder.append(", vehicleManufacturerName=");
		builder.append(vehicleManufacturerName);
		builder.append(", vehicleModelname=");
		builder.append(vehicleModelName);
		builder.append(", vehicleRegistrationNumber=");
		builder.append(vehicleRegistrationNumber);
		builder.append(", vehicleEngineNumber=");
		builder.append(vehicleEngineNumber);
		builder.append(", vehicleChassisNumber=");
		builder.append(vehicleChassisNumber);
		builder.append(", description=");
		builder.append(description);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}
