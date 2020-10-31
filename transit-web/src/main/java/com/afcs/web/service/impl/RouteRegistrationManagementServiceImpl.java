package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.RouteRegistrationManagementService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.BulkUploadRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.RouteStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class RouteRegistrationManagementServiceImpl implements RouteRegistrationManagementService {

	@Autowired
	private JsonUtil jsonUtil;

	@Autowired
	Environment properties;

	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";

	@Override
	public RouteRegistrationResponse routeRegistration(RouteRegistrationRequest request) throws AFCSException {
		RouteRegistrationResponse response = jsonUtil.postRequest(request, RouteRegistrationResponse.class, "/online/route/routeRegistration");

		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public RouteSearchResponse searchRoute(RouteSearchRequest request) throws AFCSException {
		RouteSearchResponse response = jsonUtil.postRequest(request, RouteSearchResponse.class, "/online/route/searchRouteRegistration");

		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public WebResponse updateRouteProfile(RouteRegistrationRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class, "online/route/updateRouteProfile");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public RouteSearchResponse updateRouteStatus(RouteStatusUpdateRequest request) throws AFCSException {
		RouteSearchResponse response = jsonUtil.postRequest(request, RouteSearchResponse.class, "online/route/updateRouteStatus");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}
	
	
	@Override
	public WebResponse deleteStageById(RouteRegistrationRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class, "online/route/deleteStage");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public RouteSearchResponse viewRoute(RouteSearchRequest request) throws AFCSException {
		RouteSearchResponse response = jsonUtil.postRequest(request, RouteSearchResponse.class, "/online/route/viewRouteRegistration");

		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}
	
	@Override
	public RouteSearchResponse viewStageOnRoute(RouteSearchRequest request) throws AFCSException {
		RouteSearchResponse response = jsonUtil.postRequest(request, RouteSearchResponse.class, "/online/route/getStageByRouteId");

		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}
	
	@Override
	public RouteSearchResponse validateRouteCode(BulkUploadRequest bulkUploadRequest) throws AFCSException {
		RouteSearchResponse response = jsonUtil.postRequest(bulkUploadRequest, RouteSearchResponse.class, "/online/route/validateRouteCode");

		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}
}
