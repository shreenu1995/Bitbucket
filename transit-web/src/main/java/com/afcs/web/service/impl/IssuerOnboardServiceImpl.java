package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.IssuerOnboardService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.IssuerRequest;
import com.chatak.transit.afcs.server.pojo.web.IssuerResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class IssuerOnboardServiceImpl implements IssuerOnboardService {

	@Autowired
	private JsonUtil jsonUtil;

	@Autowired
	Environment properties;

	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";

	@Override
	public IssuerResponse createIssuer(IssuerRequest issuerRequest) throws AFCSException {
		IssuerResponse response = jsonUtil.postRequest(issuerRequest, IssuerResponse.class,
				"issuerOnboarding/createIssuer");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public IssuerResponse searchIssuer(IssuerRequest issuerRequest) throws AFCSException {
		IssuerResponse response = jsonUtil.postRequest(issuerRequest, IssuerResponse.class,
				"issuerOnboarding/searchIssuer");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public IssuerResponse deleteIssuer(IssuerRequest issuerRequest) throws AFCSException {
		IssuerResponse response = jsonUtil.postRequest(issuerRequest, IssuerResponse.class,
				"issuerOnboarding/deleteIssuer");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}
	
	@Override
	public IssuerResponse getAllIssuers() throws AFCSException {
		IssuerResponse response = jsonUtil.postRequest(null, IssuerResponse.class,
				"issuerOnboarding/getAllIssuers");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}
	
	@Override
	public IssuerResponse getIssuerById(IssuerRequest issuerRequest) throws AFCSException {
		IssuerResponse response = jsonUtil.postRequest(issuerRequest, IssuerResponse.class,
				"issuerOnboarding/getIssuerById");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

}
