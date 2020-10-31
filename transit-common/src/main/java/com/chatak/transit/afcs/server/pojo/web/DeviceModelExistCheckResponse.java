package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class DeviceModelExistCheckResponse extends WebResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5589254799339743387L;

	private Boolean isExist;

	public Boolean getIsExist() {
		return isExist;
	}

	public void setIsExist(Boolean isExist) {
		this.isExist = isExist;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(" DeviceModelExistCheckResponse  [  ");
		builder.append(" StatusCode = ");
		builder.append(getStatusCode());
		builder.append(" ,  StatusMessage = ");
		builder.append(getStatusMessage());
		builder.append(" ,  isExist = ");
		builder.append(getIsExist());
		builder.append("   ]");
		return builder.toString();
	}
}
