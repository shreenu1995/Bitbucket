package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class StageProfileData implements Serializable {

	private static final long serialVersionUID = -153500484203814905L;
	private Long stageId;
	private String stageName;

	public Long getStageId() {
		return stageId;
	}

	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

}
