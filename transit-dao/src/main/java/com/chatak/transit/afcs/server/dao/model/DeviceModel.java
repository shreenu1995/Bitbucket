package com.chatak.transit.afcs.server.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "device_model")
@IdClass(value = IdForDeviceModel.class)
public class DeviceModel implements Serializable {

	private static final long serialVersionUID = -1558206402188196128L;

	@Id
	@SequenceGenerator(name = "device_id_seq", sequenceName = "device_id_seq")
	@GeneratedValue(generator = "device_id_seq")
	@Column(name = "device_id")
	private Long deviceId;

	@Column(name = "device_type_id")
	private Long deviceTypeId;

	@Column(name = "device_manufacturer_id")
	private Long deviceManufacturerId;

	@Column(name = "device_model")
	private String deviceModelName;

	@Column(name = "device_imei_number")
	private String deviceIMEINumber;

	@Column(name = "description")
	private String description;

	@Column(name = "created_time")
	private Timestamp createdTime;

	@Column(name = "updated_time")
	private Timestamp updatedTime;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "status")
	private String status;

	@Column(name = "reason")
	private String reason;

	public String getDeviceModelName() {
		return deviceModelName;
	}

	public void setDeviceModelName(String deviceModelName) {
		this.deviceModelName = deviceModelName;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public Timestamp getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
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

	public String getDeviceIMEINumber() {
		return deviceIMEINumber;
	}

	public void setDeviceIMEINumber(String deviceIMEINumber) {
		this.deviceIMEINumber = deviceIMEINumber;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
}

class IdForDeviceModel implements Serializable {

	private static final long serialVersionUID = -6139604995629057594L;
	Long deviceId;

}
