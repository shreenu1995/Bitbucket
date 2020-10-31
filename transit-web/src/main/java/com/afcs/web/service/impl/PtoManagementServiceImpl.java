package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.PtoManagementService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class PtoManagementServiceImpl implements PtoManagementService {

	@Autowired
	private JsonUtil jsonUtil;

	@Autowired
	Environment properties;
	
	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";

	@Override
	public PtoRegistrationResponse ptoRegistration(PtoRegistrationRequest request) throws AFCSException {
		PtoRegistrationResponse response = jsonUtil.postRequest(request, PtoRegistrationResponse.class, "online/ptoRegistration"); 
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}	
		return response;
		
	}

	@Override
	public PtoSearchResponse searchPto(PtoSearchRequest request) throws AFCSException {
		PtoSearchResponse response = jsonUtil.postRequest(request, PtoSearchResponse.class, "online/searchPto");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}

		return response;
	}

	@Override
	public PtoSearchResponse updatePto(PtoUpdateRequest request) throws AFCSException {
		PtoSearchResponse response = jsonUtil.postRequest(request, PtoSearchResponse.class, "online/updatePtoMaster");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}

		return response;
	}

	@Override
	public PtoListResponse getPtoList(PtoListRequest request) throws AFCSException {
		PtoListResponse response = jsonUtil.postRequest(request, PtoListResponse.class, "online/getPtoList");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public PtoListResponse getPtoByOrganizationIdAndUserId(PtoListRequest request) throws AFCSException {
		PtoListResponse response = jsonUtil.postRequest(request, PtoListResponse.class,
				"online/getPtoByOrganizationIdAndUserId");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public PtoSearchResponse updatePtoStatus(PtoStatusUpdateRequest request) throws AFCSException {
		return jsonUtil.postRequest(request, PtoSearchResponse.class, "online/updatePtoStatus");
	}

	@Override
	public PtoListResponse getPtoListWithStatusNotTerminated(PtoListRequest request) throws AFCSException {
		PtoListResponse response = jsonUtil.postRequest(request, PtoListResponse.class, "online/getPtoListWithStatusNotTerminated");
		if (StringUtil.isNull(response)) {
			throw new AFCSException("AFCS Server not responding");
		}
		return response;
	}

	@Override
	public PtoListResponse getPtoByPtoId(PtoListRequest ptoListRequest) throws AFCSException {
		PtoListResponse response = jsonUtil.postRequest(ptoListRequest, PtoListResponse.class,
				"online/getPtoByPtoId");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public PtoListResponse getPtoListByOrganizationIdAndUserId(PtoListRequest ptoListRequest) throws AFCSException {
		PtoListResponse response = jsonUtil.postRequest(ptoListRequest, PtoListResponse.class,
				"online/getPtoListByOrganizationIdAndUserId");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public PtoListResponse getActivePtoListByOrganizationId(PtoListRequest ptoListRequest) throws AFCSException {
		PtoListResponse response = jsonUtil.postRequest(ptoListRequest, PtoListResponse.class,
				"online/getActivePtoListByOrganizationId");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public PtoListResponse getActivePtoList(PtoListRequest ptoListRequest) throws AFCSException {
		PtoListResponse response = jsonUtil.postRequest(ptoListRequest, PtoListResponse.class,
				"online/getActivePtoList");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}
	
	@Override
	public WebResponse validatePtoId(String ptoId) throws AFCSException {
		WebResponse response = jsonUtil.postRequest(ptoId, WebResponse.class,
				"online/validatePtoId");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public PtoListResponse getActivePtoListByOrgId(PtoListRequest ptoListRequest) throws AFCSException {
		PtoListResponse response = jsonUtil.postRequest(ptoListRequest, PtoListResponse.class,
				"online/getActivePtoListByOrgId");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public PtoListResponse getPtoDataByPtoId(PtoListRequest ptoListRequest) throws AFCSException {
		PtoListResponse response = jsonUtil.postRequest(ptoListRequest, PtoListResponse.class,
				"online/getPtoDataByPtoId");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public PtoListResponse getPtoByPtoMasterId(Long ptoMasterId) throws AFCSException {
		PtoListResponse response = jsonUtil.postRequest(ptoMasterId, PtoListResponse.class,
				"online/getPtoDataByPtoMasterId");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

}
