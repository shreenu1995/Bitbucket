package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.TransitMasterService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterListDataResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class TransitMasterServiceImpl implements TransitMasterService{

	@Autowired
	JsonUtil jsonUtil;
	
	@Autowired
	Environment properties;

	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";

	@Override
	public TransitMasterRegistrationResponse transitMasterRegistrationResponse(
			TransitMasterRegistrationRequest transitMasterRegistrationRequest) throws AFCSException {
		TransitMasterRegistrationResponse response = jsonUtil.postRequest(transitMasterRegistrationRequest,
				TransitMasterRegistrationResponse.class,"setuptransitmaster/transitMasterRegistration");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public TransitMasterSearchResponse searchTransitMaster(TransitMasterSearchRequest transitMasterSearchRequest)
			throws AFCSException {
		TransitMasterSearchResponse response = jsonUtil.postRequest(transitMasterSearchRequest, TransitMasterSearchResponse.class, "setuptransitmaster/transitMastersearch");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}


	@Override
	public TransitMasterListDataResponse getTransitMasterListDataResponse(TransitMasterRegistrationRequest transitMasterRegistrationRequest) throws AFCSException {
		TransitMasterListDataResponse listOfDataResponse =jsonUtil.postRequest(transitMasterRegistrationRequest, TransitMasterListDataResponse.class,
				"setuptransitmaster/getListInherit");
		if (StringUtil.isNull(listOfDataResponse)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return listOfDataResponse;
	}


	@Override
	public TransitMasterSearchResponse getTransitMasterEditViewData(TransitMasterSearchRequest searchRequest)
			throws AFCSException {
		TransitMasterSearchResponse response =jsonUtil.postRequest(searchRequest, TransitMasterSearchResponse.class,
				"setuptransitmaster/getTransitMasterData");

		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}


	@Override
	public WebResponse updateTransitMaster(TransitMasterUpdateRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class,
				"setuptransitmaster/updateTransitMaster");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}


	@Override
	public WebResponse updateTransitMasterStatus(TransitMasterRegistrationRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class,
				"setuptransitmaster/updateTransitMasterStatus");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}
	
	@Override
	public TransitMasterListDataResponse getVersionNumberByPtoName(
			TransitMasterSearchRequest transitMasterSearchRequest) throws AFCSException {
		TransitMasterListDataResponse response=jsonUtil.postRequest(transitMasterSearchRequest, TransitMasterListDataResponse.class, "setuptransitmaster/getListInherit");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}
	
}
	
