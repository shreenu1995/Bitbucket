package com.chatak.transit.afcs.server.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "vehicle_type_profile")
public class VehicleTypeProfile {

	@Id
	@SequenceGenerator(name = "seq_vehicle_type_id", sequenceName = "seq_vehicle_type_id")
	@GeneratedValue(generator = "seq_vehicle_type_id")
	@Column(name = "vehicle_type_id")
	private long vehicleTypeId;

	@Column(name = "vehicle_type")
	private String vehicleType;

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

	public long getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(long vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
