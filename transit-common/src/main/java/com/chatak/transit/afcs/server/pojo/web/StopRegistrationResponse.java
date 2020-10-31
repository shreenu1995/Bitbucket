package com.chatak.transit.afcs.server.pojo.web;

public class StopRegistrationResponse extends WebResponse {

	private static final long serialVersionUID = -5775259458779432701L;
	
	private Long stopId;

	public Long getStopId() {
		return stopId;
	}

	public void setStopId(long stopId) {
		this.stopId = stopId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StopRegistrationResponse [stopId=");
		builder.append(stopId);
		builder.append("]");
		return builder.toString();
	}

}
