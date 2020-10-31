package com.chatak.transit.afcs.server.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "vehicle_model_profile")
public class VehicleModel {

	@Id
	@SequenceGenerator(name = "seq_vehicle_model_id", sequenceName = "seq_vehicle_model_id")
	@GeneratedValue(generator = "seq_vehicle_model_id")
	@Column(name = "vehicle_model_id")
	private long vehicleModelId;

	@Column(name = "vehicle_type_id")
	private long vehicleTypeId;

	@Column(name = "vehicle_manufacturer_id")
	private int vehicleManufacturerId;

	@Column(name = "vehicle_model_name")
	private String vehicleModelName;

	@Column(name = "vehicle_registration_number")
	private String vehicleRegistrationNumber;

	@Column(name = "vehicle_engine_number")
	private String vehicleEngineNumber;

	@Column(name = "vehicle_chassis_number")
	private String vehicleChassisNumber;

	@Column(name = "description")
	private String description;

	@Column(name = "status")
	private String status;

	@Column(name = "reason")
	private String reason;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public long getVehicleModelId() {
		return vehicleModelId;
	}

	public void setVehicleModelId(long vehicleModelId) {
		this.vehicleModelId = vehicleModelId;
	}

	public long getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(long vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public int getVehicleManufacturerId() {
		return vehicleManufacturerId;
	}

	public void setVehicleManufacturerId(int vehicleManufacturerId) {
		this.vehicleManufacturerId = vehicleManufacturerId;
	}

}
