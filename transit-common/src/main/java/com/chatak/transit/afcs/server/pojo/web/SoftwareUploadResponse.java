package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class SoftwareUploadResponse extends WebResponse implements Serializable {

	private static final long serialVersionUID = -2867468945104349475L;

	private String softwareURL;

	public String getSoftwareURL() {
		return softwareURL;
	}

	public void setSoftwareURL(String softwareURL) {
		this.softwareURL = softwareURL;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(" SoftwareUploadResponse  [  ");
		builder.append(" StatusCode = ");
		builder.append(getStatusCode());
		builder.append(" ,  StatusMessage = ");
		builder.append(getStatusMessage());
		builder.append(" ,  SoftwareURL = ");
		builder.append(getSoftwareURL());
		builder.append("   ]");
		return builder.toString();
	}
}
