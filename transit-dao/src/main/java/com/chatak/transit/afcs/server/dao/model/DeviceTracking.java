package com.chatak.transit.afcs.server.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "device_tracking")
public class DeviceTracking implements Serializable {

	private static final long serialVersionUID = -1640523588630211422L;

	@Id
	@SequenceGenerator(name = "seq_device_tracking", sequenceName = "seq_device_tracking")
	@GeneratedValue(generator = "seq_device_tracking")
	@Column(name = "device_tracking_id")
	private Long deviceTrackingId;
	
	@Column(name = "device_serial")
	private Long deviceSerial;

	@Column(name = "pto_id")
	private Long ptoId;

	@Column(name = "route_id")
	private String routeId;

	@Column(name = "status")
	private String status;

	@Column(name = "latitude")
	private String latitude;

	@Column(name = "longitude")
	private String longitude;

	public Long getDeviceSerial() {
		return deviceSerial;
	}

	public void setDeviceSerial(Long deviceSerial) {
		this.deviceSerial = deviceSerial;
	}

	public Long getPtoId() {
		return ptoId;
	}

	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Long getDeviceTrackingId() {
		return deviceTrackingId;
	}

	public void setDeviceTrackingId(Long deviceTrackingId) {
		this.deviceTrackingId = deviceTrackingId;
	}

}
