package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class MasterFileDownloadRequest implements Serializable {

	private static final long serialVersionUID = 12312L;

	private String ptoName;

	private String organizationId;

	private String fileName;

	private String fileVersion;

	private String deviceSerial;

	private String time;

	private String location;

	private String userType;

	private int pageSize;

	private int pageIndex;

	private Long ptoId;

	private Long orgId;

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

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getUserType() {
		return userType;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getPtoName() {
		return ptoName;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileVersion() {
		return fileVersion;
	}

	public String getDeviceSerial() {
		return deviceSerial;
	}

	public String getTime() {
		return time;
	}

	public String getLocation() {
		return location;
	}

	public void setPtoName(String ptoName) {
		this.ptoName = ptoName;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileVersion(String fileVersion) {
		this.fileVersion = fileVersion;
	}

	public void setDeviceSerial(String deviceSerial) {
		this.deviceSerial = deviceSerial;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
