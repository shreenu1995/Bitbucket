package com.chatak.transit.afcs.server.pojo;

import java.io.Serializable;

public class TripDetails implements Serializable {

	private static final long serialVersionUID = 6443481992106858267L;

	private Long orginStop;

	private Long destinationStop;

	private Long tripNumber;

	private String routeCode;

	public Long getOrginStop() {
		return orginStop;
	}

	public void setOrginStop(Long orginStop) {
		this.orginStop = orginStop;
	}

	public Long getDestinationStop() {
		return destinationStop;
	}

	public void setDestinationStop(Long destinationStop) {
		this.destinationStop = destinationStop;
	}

	public Long getTripNumber() {
		return tripNumber;
	}

	public void setTripNumber(Long tripNumber) {
		this.tripNumber = tripNumber;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

}
