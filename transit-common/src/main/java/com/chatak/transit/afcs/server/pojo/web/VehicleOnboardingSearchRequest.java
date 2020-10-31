package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class VehicleOnboardingSearchRequest extends LoginResponseParameters implements Serializable {

	private static final long serialVersionUID = -9091811052956778261L;

	private Long vehicleOnboardingId;

	private String organizationName;

	private String organizationId;

	private String ptoName;

	private String vehicleType;

	private String vehicleManufacturerName;

	private String vehicleModelName;

	private String status;

	private int pageIndex;

	private Long ptoId;

	private Long orgId;

	private Long vehicleTypeId;

	private Long vehicleModelId;

	private Integer vehicleManufacturerId;
	
	private int pageSize;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getVehicleManufacturerId() {
		return vehicleManufacturerId;
	}

	public void setVehicleManufacturerId(Integer vehicleManufacturerId) {
		this.vehicleManufacturerId = vehicleManufacturerId;
	}

	public Long getPtoId() {
		return ptoId;
	}

	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(Long vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public Long getVehicleModelId() {
		return vehicleModelId;
	}

	public void setVehicleModelId(Long vehicleModelId) {
		this.vehicleModelId = vehicleModelId;
	}

	public Long getVehicleOnboardingId() {
		return vehicleOnboardingId;
	}

	public void setVehicleOnboardingId(Long vehicleOnboardingId) {
		this.vehicleOnboardingId = vehicleOnboardingId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getPtoName() {
		return ptoName;
	}

	public void setPtoName(String ptoName) {
		this.ptoName = ptoName;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

}
