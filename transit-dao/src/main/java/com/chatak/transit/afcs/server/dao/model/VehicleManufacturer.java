package com.chatak.transit.afcs.server.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "vehicle_manufacturer_profile")
public class VehicleManufacturer {

	@Id
	@SequenceGenerator(name = "seq_vehicle_manufacturer_id", sequenceName = "seq_vehicle_manufacturer_id")
	@GeneratedValue(generator = "seq_vehicle_manufacturer_id")
	@Column(name = "vehicle_manufacturer_id")
	private int vehicleManufacturerId;

	@Column(name = "vehicle_type_id")
	private Long vehicleTypeId;

	@Column(name = "vehicle_manufacturer_name")
	private String vehicleManufacturerName;

	@Column(name = "description")
	private String description;

	@Column(name = "status")
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getVehicleManufacturerId() {
		return vehicleManufacturerId;
	}

	public void setVehicleManufacturerId(int vehicleManufacturerId) {
		this.vehicleManufacturerId = vehicleManufacturerId;
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

	public Long getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(Long vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

}
