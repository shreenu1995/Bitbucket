package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.chatak.transit.afcs.server.constants.Constants;

public class StageRegistrationRequest extends StopRegistrationRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "chatak.afcs.service.user.id.required")
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.user.id.invalid")
	@Size(min = 8, max = 32, message = "chatak.afcs.service.user.id.length.invalid")
	private String userId;

	private Long routeId;

	private Long organizationId;
	
	private Long ptoId;
	
	private String stageName;

	private Long stageId;

	private int stageSequenceNumber;

	private List<StopRegistrationRequest> dataFieldList;
	
	private String routeName;
	
	@Override
	public String getRouteName() {
		return routeName;
	}

	@Override
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	
	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public Long getRouteId() {
		return routeId;
	}

	@Override
	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	@Override
	public Long getOrganizationId() {
		return organizationId;
	}

	@Override
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	@Override
	public Long getPtoId() {
		return ptoId;
	}

	@Override
	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}

	@Override
	public String getStageName() {
		return stageName;
	}

	@Override
	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	@Override
	public Long getStageId() {
		return stageId;
	}
	
	@Override
	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}

	public int getStageSequenceNumber() {
		return stageSequenceNumber;
	}

	public void setStageSequenceNumber(int stageSequenceNumber) {
		this.stageSequenceNumber = stageSequenceNumber;
	}

	public List<StopRegistrationRequest> getDataFieldList() {
		return dataFieldList;
	}

	public void setDataFieldList(List<StopRegistrationRequest> dataFieldList) {
		this.dataFieldList = dataFieldList;
	}


}
