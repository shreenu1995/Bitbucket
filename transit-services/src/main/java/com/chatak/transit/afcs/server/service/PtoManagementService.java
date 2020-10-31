package com.chatak.transit.afcs.server.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoStatusCheckRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface PtoManagementService {

	public PtoRegistrationResponse savePtoRegistration(
			PtoRegistrationRequest ptoOperationRegRequest, HttpServletResponse httpResponse,HttpSession session,
			PtoRegistrationResponse response) throws IOException;

	public PtoListResponse getPtoList(PtoListRequest ptoOperationListRequest,
			HttpServletResponse httpResponse, PtoListResponse ptoOperationListResponse) throws IOException;

	PtoSearchResponse updatePtoStatus(PtoStatusUpdateRequest request, WebResponse response) throws IOException;

	void errorPtoOperationStatusCheck(PtoStatusCheckRequest request, WebResponse response);

	PtoSearchResponse searchPto(PtoSearchRequest request);

	void validatePtoRegistrationErrors(PtoRegistrationRequest request,
			PtoRegistrationResponse response);

	void errorPtoOperationStatusUpdate(PtoStatusUpdateRequest request, WebResponse response);

	WebResponse updatePtoMaster(PtoUpdateRequest request, WebResponse response,
			HttpServletResponse httpResponse) throws IOException;

	void validatePtoOperationProfileUpdate(PtoUpdateRequest request, WebResponse response);

	public PtoListResponse getPtoByOrganizationId(
			@Valid PtoListRequest ptoOperationListRequest, HttpServletResponse httpResponse,
			PtoListResponse response);

	public PtoListResponse getPtoListWithStatusNotTerminated(@Valid PtoListRequest ptoListRequest,
			HttpServletResponse httpResponse, PtoListResponse response) throws IOException;

	public PtoListResponse getPtoByPtoId(@Valid PtoListRequest ptoListRequest, HttpServletResponse httpResponse,
			PtoListResponse response) throws IOException;

	public PtoListResponse getPtoListByOrganizationId(@Valid PtoListRequest ptoListRequest,
			HttpServletResponse httpResponse, PtoListResponse response);

	public PtoListResponse getActivePtoListByOrganizationId(@Valid PtoListRequest ptoListRequest,
			HttpServletResponse httpResponse, PtoListResponse response);

	public PtoListResponse getActivePtoList(@Valid PtoListRequest ptoListRequest, HttpServletResponse httpResponse,
			PtoListResponse response);
	
	WebResponse validatePtoId(String ptoId);

	public PtoListResponse getPtoDataByPtoId(@Valid PtoListRequest ptoListRequest, HttpServletResponse httpResponse,
			PtoListResponse response);
	
	public PtoListResponse getPtoByPtoMasterId(Long ptoMasterId);

}	
