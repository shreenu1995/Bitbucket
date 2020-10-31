package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import java.util.List;

public class DepotListViewResponse extends WebResponse implements Serializable {

	private static final long serialVersionUID = -574781143454900348L;

	private List<DepotData> listOfDepotInformation;

	public List<DepotData> getListOfDepotInformation() {
		return listOfDepotInformation;
	}

	public void setListOfDepotInformation(List<DepotData> listOfDepotInformation) {
		this.listOfDepotInformation = listOfDepotInformation;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DepotListViewResponse [listOfDepotInformation=");
		builder.append(listOfDepotInformation);
		builder.append(", getStatusCode()=");
		builder.append(getStatusCode());
		builder.append(", getStatusMessage()=");
		builder.append(getStatusMessage());
		builder.append("]");
		return builder.toString();
	}

}
