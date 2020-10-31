package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import java.util.List;

public class TransitMasterListDataResponse extends WebResponse implements Serializable {

	private static final long serialVersionUID = -4655661062793272016L;
	private List<TransitMasterRegistrationRequest> listOfInheritAndPto;
	
	public List<TransitMasterRegistrationRequest> getListOfInheritAndPto() {
		return listOfInheritAndPto;
	}

	public void setListOfInheritAndPto(List<TransitMasterRegistrationRequest> listOfInheritAndPto) {
		this.listOfInheritAndPto = listOfInheritAndPto;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TransitMasterListDataResponse [listOfInheritAndPto=");
		builder.append(listOfInheritAndPto);
		builder.append(", getStatusCode()=");
		builder.append(getStatusCode());
		builder.append(", getStatusMessage()=");
		builder.append(getStatusMessage());
		builder.append("]");
		return builder.toString();
	}
}
