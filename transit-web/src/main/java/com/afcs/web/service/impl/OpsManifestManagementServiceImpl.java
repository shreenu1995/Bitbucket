package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.OpsManifestManagementService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.Response;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class OpsManifestManagementServiceImpl implements OpsManifestManagementService{

	@Autowired
	private JsonUtil jsonUtil;
	
	@Autowired
	Environment properties;
	
	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";

	@Override
	public OpsManifestRegistrationResponse opsmanifestRegistration(
			OpsManifestRegistrationRequest opsManifestRegistrationRequest) throws AFCSException {
		OpsManifestRegistrationResponse response = jsonUtil.postRequest(opsManifestRegistrationRequest, OpsManifestRegistrationResponse.class, "online/opsManifestRegistration");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public OpsManifestSearchResponse searchOpsManifest(OpsManifestSearchRequest opsManifestSearchRequest) throws AFCSException {
		OpsManifestSearchResponse response = jsonUtil.postRequest(opsManifestSearchRequest, OpsManifestSearchResponse.class, "online/searchOpsManifest");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public WebResponse updateOpsManifestStatus(OpsManifestStatusChangeRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class, "online/updateOpsManifestStatus");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public WebResponse updateOpsManifest(OpsManifestUpdateRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class, "online/updateOpsManifestProfile");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public String deviceOnboardIdExistCheck(Long deviceNo) {
		OpsManifestRegistrationRequest opsManifestRegistrationRequest = new OpsManifestRegistrationRequest();
		opsManifestRegistrationRequest.setDeviceNo(deviceNo);
		Response response = jsonUtil.postRequest(opsManifestRegistrationRequest, Response.class, "online/deviceOnboardIdExistCheck");
		return response.getResult();
	}

}
