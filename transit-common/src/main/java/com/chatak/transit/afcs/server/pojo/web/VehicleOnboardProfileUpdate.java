package com.chatak.transit.afcs.server.pojo.web;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.chatak.transit.afcs.server.constants.Constants;

public class VehicleOnboardProfileUpdate extends LoginResponseParameters {

	private static final long serialVersionUID = 7164192978009511322L;

	@NotBlank(message = "chatak.afcs.service.user.id.required")
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.user.id.invalid")
	@Size(max = 35, message = "chatak.afcs.service.userid.size.invalid")
	private String userId;

	private String vehicleType;

	private String vehicleManufacturerName;

	private String vehicleModelName;

	private String ptoName;

	private String organizationName;

	private Long vehicleOnboardingId;

	private Long organizationId;

	private Long vehicleTypeId;

	private Integer vehicleManufacturerId;

	private Long vehicleModelId;

	private Long ptoId;

	private Long ptoMasterId;

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getPtoMasterId() {
		return ptoMasterId;
	}

	public void setPtoMasterId(Long ptoMasterId) {
		this.ptoMasterId = ptoMasterId;
	}

	public Long getPtoId() {
		return ptoId;
	}

	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}

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

	public Long getVehicleModelId() {
		return vehicleModelId;
	}

	public void setVehicleModelId(Long vehicleModelId) {
		this.vehicleModelId = vehicleModelId;
	}

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
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

	public String getVehicleModelName() {
		return vehicleModelName;
	}

	public void setVehicleModelName(String vehicleModelName) {
		this.vehicleModelName = vehicleModelName;
	}

	public String getPtoName() {
		return ptoName;
	}

	public void setPtoName(String ptoName) {
		this.ptoName = ptoName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public Long getVehicleOnboardingId() {
		return vehicleOnboardingId;
	}

	public void setVehicleOnboardingId(Long vehicleOnboardingId) {
		this.vehicleOnboardingId = vehicleOnboardingId;
	}

}
