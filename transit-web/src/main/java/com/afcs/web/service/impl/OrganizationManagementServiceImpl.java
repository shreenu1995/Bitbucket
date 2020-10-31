package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.OrganizationManagementService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.OrganizationRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.OrganizationResponse;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.OrganizationStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class OrganizationManagementServiceImpl implements OrganizationManagementService {

	@Autowired
	private JsonUtil jsonUtil;
	
	@Autowired
	Environment properties;

	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";

	@Override
	public OrganizationRegistrationResponse createOrganization(OrganizationRegistrationRequest request) throws AFCSException {
		OrganizationRegistrationResponse response = jsonUtil.postRequest(request, OrganizationRegistrationResponse.class, "online/organizationRegistration");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public OrganizationSearchResponse searchOrganization(OrganizationSearchRequest request) throws AFCSException {
		OrganizationSearchResponse response = jsonUtil.postRequest(request, OrganizationSearchResponse.class, "online/searchOrganization");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public WebResponse updateOrganization(OrganizationUpdateRequest request) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(request, WebResponse.class, "online/updateOrganizationMaster");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public OrganizationSearchResponse updateOrganizationStatus(OrganizationStatusUpdateRequest request) throws AFCSException {
		OrganizationSearchResponse response = jsonUtil.postRequest(request, OrganizationSearchResponse.class, "online/updateOrganizationStatus");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public OrganizationResponse getOrganizationIdByOrganizationName(OrganizationSearchRequest ptoSearchRequest, OrganizationResponse response) throws AFCSException {
		OrganizationResponse organizationResponse= null;
		organizationResponse = jsonUtil.postRequest(ptoSearchRequest, OrganizationResponse.class, "online/getOrganizationIdByOrganizationName");
		if(StringUtil.isNull(organizationResponse)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return organizationResponse;
	}

	@Override
	public OrganizationSearchResponse getOrganizationList(OrganizationSearchRequest searchPtoRequest) throws AFCSException {
		OrganizationSearchResponse response = jsonUtil.postRequest(searchPtoRequest, OrganizationSearchResponse.class, "online/getOrganizationList");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public OrganizationSearchResponse getOrganizationListWithStatusNotTerminated(
			OrganizationSearchRequest organizationSearchRequest) throws AFCSException {
		OrganizationSearchResponse response = jsonUtil.postRequest(organizationSearchRequest, OrganizationSearchResponse.class, "online/getOrganizationListWithStatusNotTerminated");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public OrganizationSearchResponse getOrganizationListByOrgId(OrganizationSearchRequest organizationSearchRequest)
			throws AFCSException {
		OrganizationSearchResponse response = jsonUtil.postRequest(organizationSearchRequest, OrganizationSearchResponse.class, "online/getOrganizationListByOrgId");
		if(StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}
}
