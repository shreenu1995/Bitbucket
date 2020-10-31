package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

import com.chatak.transit.afcs.server.pojo.HttResponse;

public class SoftwareVersionCheckResponse extends HttResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1165416130365000342L;

	private String versionAvailable;

	private String checkSum;

	public String getVersionAvailable() {
		return versionAvailable;
	}

	public void setVersionAvailable(String versionAvailable) {
		this.versionAvailable = String.format("%-75s", versionAvailable);
	}

	public String getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(String checkSum) {
		this.checkSum = String.format("%-1s", checkSum);

	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getStatusCode());
		builder.append(getStatusMessage());
		builder.append(getVersionAvailable());
		builder.append(getCheckSum());
		return builder.toString();
	}

}
