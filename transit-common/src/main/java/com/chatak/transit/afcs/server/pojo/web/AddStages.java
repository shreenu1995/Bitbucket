package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class AddStages extends LoginResponseParameters implements Serializable {

	private static final long serialVersionUID = -5338345290476570944L;
	private long id;
	private int stageSequenceNumber;
	private String stageName;
	private double distance;

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public int getStageSequenceNumber() {
		return stageSequenceNumber;
	}

	public void setStageSequenceNumber(int stageSequenceNumber) {
		this.stageSequenceNumber = stageSequenceNumber;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
