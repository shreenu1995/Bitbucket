package com.chatak.transit.afcs.server.pojo.web;

public class TransitMasterListData {

	private String inherit;
	private String pto;
	private String versionNumber;
	
	public String getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}
	public String getInherit() {
		return inherit;
	}
	public void setInherit(String inherit) {
		this.inherit = inherit;
	}
	public String getPto() {
		return pto;
	}
	public void setPto(String pto) {
		this.pto = pto;
	}
}
