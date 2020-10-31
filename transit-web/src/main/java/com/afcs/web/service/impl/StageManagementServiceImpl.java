package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.StageManagementService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteNameList;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.StageRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.StageRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.StageResponse;
import com.chatak.transit.afcs.server.pojo.web.StageSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.StageStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class StageManagementServiceImpl implements StageManagementService {

	@Autowired
	Environment properties;

	@Autowired
	JsonUtil jsonUtil;

	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";

	@Override
	public StageRegistrationResponse stageRegister(StageRegistrationRequest request) {
		return jsonUtil.postRequest(request, StageRegistrationResponse.class, "stage/stageRegistration");
	}

	@Override
	public StageResponse searchStage(StageSearchRequest request) throws AFCSException {
		StageResponse response = jsonUtil.postRequest(request, StageResponse.class, "stage/searchStage");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public RouteNameList getRouteName() {
		return jsonUtil.postRequest(RouteNameList.class, "stage/getRouteName");
	}

	@Override
	public WebResponse updateStage(StageRegistrationRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class, "stage/stageProfileUpdate");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public StageResponse updateStageStatus(StageStatusUpdateRequest request) throws AFCSException {
		StageResponse response = jsonUtil.postRequest(request, StageResponse.class, "stage/stageStatusUpdate");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public StageResponse viewStopOnStage(StageSearchRequest request) throws AFCSException {
		StageResponse response = jsonUtil.postRequest(request, StageResponse.class, "stage/getStopByStageId");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public WebResponse deleteStopById(StageRegistrationRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class, "stage/deleteStop");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}
	
	@Override
	public RouteSearchResponse getRouteAccordingOrgAndPto(RouteSearchRequest routeSearchRequest) throws AFCSException {
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setPtoMasterId(routeSearchRequest.getPtoId());
		RouteSearchResponse response =jsonUtil.postRequest(routeSearchRequest, RouteSearchResponse.class, "/stage/getRouteByPto");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
      return response;	
	}

}
