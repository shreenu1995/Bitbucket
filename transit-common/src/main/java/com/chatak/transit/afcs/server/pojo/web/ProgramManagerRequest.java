package com.chatak.transit.afcs.server.pojo.web;

public class ProgramManagerRequest {

	private Long id;
	
	private String programManagerName;

	public Long getId() {
		return id;
	}

	public String getProgramManagerName() {
		return programManagerName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setProgramManagerName(String programManagerName) {
		this.programManagerName = programManagerName;
	}
}
