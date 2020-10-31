package com.afcs.web.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.StopManagementService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.RouteNameList;
import com.chatak.transit.afcs.server.pojo.web.StageNameList;
import com.chatak.transit.afcs.server.pojo.web.StopNameList;
import com.chatak.transit.afcs.server.pojo.web.StopRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.StopRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.StopSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.StopSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.StopUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class StopManagementServiceImpl implements StopManagementService {

	@Autowired
	private JsonUtil jsonUtil;
	
	@Autowired
	Environment properties;

	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";

	@Override
	public StopRegistrationResponse stopRegistration(StopRegistrationRequest request) throws AFCSException {
		StopRegistrationResponse response = jsonUtil.postRequest(request, StopRegistrationResponse.class, "online/stopRegistration");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}
	
	@Override
	public StopSearchResponse stopSearch(StopSearchRequest request) throws AFCSException {
		StopSearchResponse response = jsonUtil.postRequest(request, StopSearchResponse.class, "online/searchStop");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}
	
	@Override
	public WebResponse updateStopProfile(StopUpdateRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class, "online/updateStop");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}
	
	@Override
	public StopSearchResponse updateStopStatus(StopSearchRequest request) throws AFCSException {
		StopSearchResponse response = jsonUtil.postRequest(request, StopSearchResponse.class, "online/updateStopStatus");
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
	public StageNameList getStageNameList() {
		return jsonUtil.postRequest(StageNameList.class, "online/getStageName");
	}

	@Override
	public StopSearchResponse validateStopCode(String stopCode) throws AFCSException {
		StopSearchResponse response = jsonUtil.postRequest(stopCode, StopSearchResponse.class, "online/validateStopCode");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public StopNameList getStopNameList() {
		return jsonUtil.postRequest(StopNameList.class, "online/getStopName");
	}

	@Override
	public StopSearchResponse getStageByRoute(StopSearchRequest searchRequest) throws AFCSException {
		StopSearchResponse response = jsonUtil.postRequest(searchRequest, StopSearchResponse.class, "online/getStageByRouteId");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}
}
