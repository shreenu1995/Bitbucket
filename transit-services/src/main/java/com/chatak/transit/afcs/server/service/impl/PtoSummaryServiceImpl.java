package com.chatak.transit.afcs.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.dao.impl.PtoManagementDaoImpl;
import com.chatak.transit.afcs.server.dao.impl.RouteManagementDaoImpl;
import com.chatak.transit.afcs.server.dao.impl.TransactionReportGenerationDaoImpl;
import com.chatak.transit.afcs.server.dao.model.TicketsTxnData;
import com.chatak.transit.afcs.server.pojo.web.PtoSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoSummaryRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoSummaryResponse;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteSearchResponse;
import com.chatak.transit.afcs.server.service.PtoSummaryService;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;

@Service
public class PtoSummaryServiceImpl implements PtoSummaryService {

	@Autowired
	RouteManagementDaoImpl routeManagementDaoImpl;

	@Autowired
	PtoManagementDaoImpl ptoManagementDaoImpl;

	@Autowired
	TransactionReportGenerationDaoImpl transactionReportGenerationDaoImpl;

	@Override
	public PtoSummaryResponse searchPto(PtoSummaryRequest request) {
		PtoSummaryResponse response = new PtoSummaryResponse();
		PtoSearchRequest ptoSearchRequest = new PtoSearchRequest();
		ptoSearchRequest.setUserType(request.getUserType());
		PtoSearchResponse ptoSearchResponse = null;
		ptoSearchRequest.setOrgId(request.getOrgId());
		ptoSearchRequest.setPtoMasterId(request.getPtoMasterId());
		ptoSearchRequest.setPageIndex(request.getIndexPage());
		ptoSearchResponse = ptoManagementDaoImpl.searchPto(ptoSearchRequest);
		RouteSearchRequest routeSearchRequest = new RouteSearchRequest();
		List<PtoSummaryRequest> list = new ArrayList<>();
		for (PtoSearchRequest ptoSearchRequest2 : ptoSearchResponse.getPtosearchList()) {
			PtoSummaryRequest ptoSummaryRequest = new PtoSummaryRequest();
			routeSearchRequest.setPtoId(ptoSearchRequest2.getPtoMasterId());
			routeSearchRequest.setOrganizationId(ptoSearchRequest2.getOrgId());
			routeSearchRequest.setUserType(request.getUserType());
			RouteSearchResponse routeSearchResponse = routeManagementDaoImpl
					.getRouteSearchListForPtoResult(routeSearchRequest);
			Long ptoId = ptoSearchRequest2.getPtoMasterId();
			List<TicketsTxnData> ticketIssuedList = transactionReportGenerationDaoImpl
					.getReportByPtoIdAndOrganizationId(ptoId, String.valueOf(ptoSearchRequest2.getOrgId()));
			double amount = 0;
			for (TicketsTxnData ticketsTxnData : ticketIssuedList) {
				amount = amount + ticketsTxnData.getTicketFareAmount();
			}
			long size = ticketIssuedList == null ? 0 : Long.valueOf(ticketIssuedList.size());
			ptoSummaryRequest.setNoOfRoutes(routeSearchResponse.getListORoutes() == null ? 0
					: Long.valueOf(routeSearchResponse.getListORoutes().size()));
			ptoSummaryRequest.setTicketsIssued(size);
			ptoSummaryRequest.setTotalRevenue((long) amount);
			ptoSummaryRequest.setOrganizationName(ptoSearchRequest2.getOrganizationName());
			ptoSummaryRequest.setPtoName(ptoSearchRequest2.getPtoName());
			list.add(ptoSummaryRequest);
		}
		response.setListOfPtoSummaryRequest(list);
		response.setTotalNoOfRecords(ptoSearchResponse.getTotalRecords());
		if (!StringUtil.isNull(response)) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
		return response;
	}

}
