package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class DepotData implements Serializable {

	private static final long serialVersionUID = -931867604439737494L;

	private Long depotId;

	private String depotName;

	public Long getDepotId() {
		return depotId;
	}

	public void setDepotId(Long depotId) {
		this.depotId = depotId;
	}

	public String getDepotName() {
		return depotName;
	}

	public void setDepotName(String depotName) {
		this.depotName = depotName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DepotData [depotId=");
		builder.append(depotId);
		builder.append(", depotName=");
		builder.append(depotName);
		builder.append("]");
		return builder.toString();
	}

}
