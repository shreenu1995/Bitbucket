package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class OperatorSessionReportGenerationResponse extends WebResponse {

	private static final long serialVersionUID = -71995183526751225L;

	private int totalNoOfRecords;

	private List<OperatorSessionReportResponse> listOfOperatorSessionResponse;

	public int getTotalNoOfRecords() {
		return totalNoOfRecords;
	}

	public void setTotalNoOfRecords(int totalNoOfRecords) {
		this.totalNoOfRecords = totalNoOfRecords;
	}

	public List<OperatorSessionReportResponse> getListOfOperatorSessionResponse() {
		return listOfOperatorSessionResponse;
	}

	public void setListOfOperatorSessionResponse(List<OperatorSessionReportResponse> listOfOperatorSessionResponse) {
		this.listOfOperatorSessionResponse = listOfOperatorSessionResponse;
	}

	@Override
	public String toString() {
		StringBuilder resposne = new StringBuilder();
		return resposne.append("OperatorSessionReportGenerationResponse [+  getStatusCode()=").append(getStatusCode())
				.append(", getStatusMessage()=").append(getStatusMessage()).append("]").toString();
	}

}
