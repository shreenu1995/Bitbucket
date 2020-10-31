package com.chatak.transit.afcs.server.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "vehicle_onboarding_profile")
public class VehicleOnboarding {

	@Id
	@SequenceGenerator(name = "seq_vehicle_onboarding_id", sequenceName = "seq_vehicle_onboarding_id")
	@GeneratedValue(generator = "seq_vehicle_onboarding_id")
	@Column(name = "vehicle_onboarding_id")
	private Long vehicleOnboardingId;

	@Column(name = "organization_id")
	private long organizationId;

	@Column(name = "pto_id")
	private long ptoId;

	@Column(name = "vehicle_type_id")
	private long vehicleTypeId;

	@Column(name = "vehicle_manufacturer_id")
	private int vehicleManufacturerId;

	@Column(name = "vehicle_model_id")
	private long vehicleModelId;

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

	public Long getVehicleOnboardingId() {
		return vehicleOnboardingId;
	}

	public void setVehicleOnboardingId(Long vehicleOnboardingId) {
		this.vehicleOnboardingId = vehicleOnboardingId;
	}

	public long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}

	public long getPtoId() {
		return ptoId;
	}

	public void setPtoId(long ptoId) {
		this.ptoId = ptoId;
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

	public long getVehicleModelId() {
		return vehicleModelId;
	}

	public void setVehicleModelId(long vehicleModelId) {
		this.vehicleModelId = vehicleModelId;
	}

}
