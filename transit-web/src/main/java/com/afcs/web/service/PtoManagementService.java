package com.afcs.web.service;

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

public interface PtoManagementService {

	PtoRegistrationResponse ptoRegistration(PtoRegistrationRequest request) throws AFCSException;
	
	PtoSearchResponse searchPto(PtoSearchRequest request) throws AFCSException;
	
	PtoSearchResponse updatePto(PtoUpdateRequest request) throws AFCSException;

	PtoSearchResponse updatePtoStatus(PtoStatusUpdateRequest request) throws AFCSException;
	
	PtoListResponse getPtoList(PtoListRequest request) throws AFCSException;

	PtoListResponse getPtoByOrganizationIdAndUserId(PtoListRequest ptoOperationListRequest) throws AFCSException;

	PtoListResponse getPtoListWithStatusNotTerminated(PtoListRequest ptoListRequest) throws AFCSException;

	PtoListResponse getPtoByPtoId(PtoListRequest ptoListRequest) throws AFCSException;

	PtoListResponse getPtoListByOrganizationIdAndUserId(PtoListRequest ptoListRequest) throws AFCSException;

	PtoListResponse getActivePtoListByOrganizationId(PtoListRequest ptoListRequest) throws AFCSException;

	PtoListResponse getActivePtoList(PtoListRequest ptoListRequest) throws AFCSException;
	
	WebResponse validatePtoId(String ptoId) throws AFCSException;

	PtoListResponse getActivePtoListByOrgId(PtoListRequest ptoListRequest) throws AFCSException;

	PtoListResponse getPtoDataByPtoId(PtoListRequest ptoListRequest) throws AFCSException;
	
	PtoListResponse getPtoByPtoMasterId(Long ptoMasterId) throws AFCSException;

}
