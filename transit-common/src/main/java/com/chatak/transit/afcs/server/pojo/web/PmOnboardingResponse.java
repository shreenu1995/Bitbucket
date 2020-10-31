package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class PmOnboardingResponse {

	private List<ProgramManagerRequest> programManager;

	private String errorCode;

	private String errorMessage;

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<ProgramManagerRequest> getProgramManager() {
		return programManager;
	}

	public void setProgramManager(List<ProgramManagerRequest> programManager) {
		this.programManager = programManager;
	}
}
