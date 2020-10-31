package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.DepotManagementService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.DepotProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.DepotSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DepotStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class DepotManagementServiceImpl implements DepotManagementService{
	
	@Autowired
	JsonUtil jsonUtil;
	
	@Autowired
	Environment properties;
	
	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";

	@Override
	public DepotRegistrationResponse depotManagementRegistration(DepotRegistrationRequest request) {
		return jsonUtil.postRequest(request, DepotRegistrationResponse.class, "online/depotRegistration");
	}

	@Override
	public DepotSearchResponse searchDepot(DepotSearchRequest depotSearchRequest) throws AFCSException {
		DepotSearchResponse response = jsonUtil.postRequest(depotSearchRequest, DepotSearchResponse.class, "online/searchDepot");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public WebResponse updateDepotProfile(DepotProfileUpdateRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class, "online/updateDepotProfile");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public DepotSearchResponse updateDepotStatus(DepotStatusUpdateRequest request) throws AFCSException {
		DepotSearchResponse response = jsonUtil.postRequest(request, DepotSearchResponse.class, "online/updateDepotStatus");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

}
