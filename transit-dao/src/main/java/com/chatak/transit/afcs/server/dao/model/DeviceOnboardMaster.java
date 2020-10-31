package com.chatak.transit.afcs.server.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "device_onboard_master")
@IdClass(value = IdForDeviceOnboard.class)
public class DeviceOnboardMaster implements Serializable {

	private static final long serialVersionUID = -1061523050259053018L;

	@Id
	@SequenceGenerator(name = "device_onboard_id_seq", sequenceName = "device_onboard_id_seq")
	@GeneratedValue(generator = "device_onboard_id_seq")
	@Column(name = "device_onboard_id")
	private Long deviceOnboardId;

	@Column(name = "organization_id")
	private Long organizationId;

	@Column(name = "status")
	private String status;

	@Column(name = "reason")
	private String reason;

	@Column(name = "device_type_id")
	private Long deviceTypeId;

	@Column(name = "device_manufacturer_id")
	private Long deviceManufacturerId;

	@Column(name = "device_model_id")
	private Long deviceModelId;

	@Column(name = "pto_id")
	private Long ptoId;

	public Long getPtoId() {
		return ptoId;
	}

	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}

	public Long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public Long getDeviceManufacturerId() {
		return deviceManufacturerId;
	}

	public void setDeviceManufacturerId(Long deviceManufacturerId) {
		this.deviceManufacturerId = deviceManufacturerId;
	}

	public Long getDeviceModelId() {
		return deviceModelId;
	}

	public void setDeviceModelId(Long deviceModelId) {
		this.deviceModelId = deviceModelId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getDeviceOnboardId() {
		return deviceOnboardId;
	}

	public void setDeviceOnboardId(Long deviceOnboardId) {
		this.deviceOnboardId = deviceOnboardId;
	}

}

class IdForDeviceOnboard implements Serializable {

	private static final long serialVersionUID = -3229506170759136584L;
	Long deviceOnboardId;

}
