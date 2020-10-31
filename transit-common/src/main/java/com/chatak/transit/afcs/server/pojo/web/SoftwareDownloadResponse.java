package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

import com.chatak.transit.afcs.server.pojo.HttResponse;

public class SoftwareDownloadResponse extends HttResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4194624620396182266L;

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
		builder.append(getStatusCode());
		builder.append(getStatusMessage());
		return builder.toString();
	}

}
