package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class DeviceTrackingRequest implements Serializable {

	private static final long serialVersionUID = -201609781769999257L;

	private Long ptoId;
	
	private String ptoName;
	
	private String routeId;
	
	private Long deviceSerialNo;
	
	private String status;
	
	private String latitude;
	
	private String longitude;
	
	private int indexPage;
	
	private String userId;
	
	private String userType;
	
	private int pageSize;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Long getPtoId() {
		return ptoId;
	}

	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}

	public String getPtoName() {
		return ptoName;
	}

	public void setPtoName(String ptoName) {
		this.ptoName = ptoName;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public Long getDeviceSerialNo() {
		return deviceSerialNo;
	}

	public void setDeviceSerialNo(Long deviceSerialNo) {
		this.deviceSerialNo = deviceSerialNo;
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

	public int getIndexPage() {
		return indexPage;
	}

	public void setIndexPage(int indexPage) {
		this.indexPage = indexPage;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
