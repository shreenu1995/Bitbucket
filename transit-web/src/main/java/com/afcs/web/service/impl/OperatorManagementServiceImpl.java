package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.OperatorManagementService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.OperatorRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorResponse;
import com.chatak.transit.afcs.server.pojo.web.OperatorSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class OperatorManagementServiceImpl implements OperatorManagementService {

	@Autowired
	JsonUtil jsonUtil;

	@Autowired
	Environment properties;

	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";

	@Override
	public WebResponse createOperator(OperatorRegistrationRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class, "operator/operatorRegistration");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public OperatorResponse searchOperator(OperatorSearchRequest request) throws AFCSException {
		OperatorResponse response = jsonUtil.postRequest(request, OperatorResponse.class, "operator/searchOperator");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public WebResponse updateOperatorProfile(OperatorUpdateRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class, "operator/updateOperatorProfile");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public OperatorResponse updateOperatorStatus(OperatorStatusChangeRequest request) throws AFCSException {
		OperatorResponse response = jsonUtil.postRequest(request, OperatorResponse.class, "operator/updateOperatorStatus");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

}
