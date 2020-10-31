package com.chatak.transit.afcs.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.dao.TransactionReportGenerationDao;
import com.chatak.transit.afcs.server.dao.impl.PtoManagementDaoImpl;
import com.chatak.transit.afcs.server.dao.model.TicketsTxnData;
import com.chatak.transit.afcs.server.pojo.web.PassengerAnalysisRequest;
import com.chatak.transit.afcs.server.pojo.web.PassengerAnalysisResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoSearchResponse;
import com.chatak.transit.afcs.server.service.PassengerAnalysisService;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;

@Service
public class PassengerAnalysisServiceImpl implements PassengerAnalysisService {

	@Autowired
	TransactionReportGenerationDao transactionReportGenerationDao;

	@Autowired
	PtoManagementDaoImpl ptoManagementDaoImpl;

	@Override
	public PassengerAnalysisResponse searchPassengerAnalysisReport(PassengerAnalysisRequest request) {
		PassengerAnalysisResponse passengerAnalysisResponse = new PassengerAnalysisResponse();
		PtoSearchRequest ptoSearchRequest = new PtoSearchRequest();
		ptoSearchRequest.setUserType(request.getUserType());
		PtoSearchResponse ptoSearchResponse = null;
		ptoSearchRequest.setOrgId(request.getOrgId());
		ptoSearchRequest.setPtoMasterId(request.getPtoMasterId());
		ptoSearchRequest.setPageIndex(request.getIndexPage());
		ptoSearchResponse = ptoManagementDaoImpl.searchPto(ptoSearchRequest);
		List<PassengerAnalysisRequest> list = new ArrayList<>();
		for (PtoSearchRequest ptoSearchRequest2 : ptoSearchResponse.getPtosearchList()) {
			PassengerAnalysisRequest passengerAnalysisRequest = new PassengerAnalysisRequest();
			Long ptoId = ptoSearchRequest2.getPtoMasterId();
			getPassengerAnalysisRequest(ptoSearchRequest2, passengerAnalysisRequest, ptoId);
			list.add(passengerAnalysisRequest);
		}
		passengerAnalysisResponse.setListOfPassengerAnalysisRequest(list);
		passengerAnalysisResponse.setTotalNoOfRecords(ptoSearchResponse.getTotalRecords());
		if (!StringUtil.isNull(passengerAnalysisResponse)) {
			passengerAnalysisResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			passengerAnalysisResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		} else {
			passengerAnalysisResponse.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			passengerAnalysisResponse.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
		return passengerAnalysisResponse;
	}

	private void getPassengerAnalysisRequest(PtoSearchRequest ptoSearchRequest2,
			PassengerAnalysisRequest passengerAnalysisRequest, Long ptoId) {
		List<TicketsTxnData> generalTickets = transactionReportGenerationDao.getReportByPtoIdAndTransactionId(ptoId,
				"TT01");
		passengerAnalysisRequest.setGeneralCount(
				(generalTickets == null || generalTickets.isEmpty()) ? 0 : Long.valueOf(generalTickets.size()));
		List<TicketsTxnData> childrenTickets = transactionReportGenerationDao
				.getReportByPtoIdAndTransactionId(ptoId, "TT02");
		passengerAnalysisRequest.setChildrenCount(
				(childrenTickets == null || childrenTickets.isEmpty()) ? 0 : Long.valueOf(childrenTickets.size()));
		List<TicketsTxnData> baggageTickets = transactionReportGenerationDao.getReportByPtoIdAndTransactionId(ptoId,
				"TL01");
		passengerAnalysisRequest.setBaggageCount(
				(childrenTickets == null || baggageTickets.isEmpty()) ? 0 : Long.valueOf(baggageTickets.size()));
		List<TicketsTxnData> seniorTickets = transactionReportGenerationDao.getReportByPtoIdAndTransactionId(ptoId,
				"TP01");
		passengerAnalysisRequest.setSeniorCitizenCount(
				(seniorTickets == null || seniorTickets.isEmpty()) ? 0 : Long.valueOf(seniorTickets.size()));
		passengerAnalysisRequest.setStudentCount((childrenTickets == null || childrenTickets.isEmpty()) ? 0
				: Long.valueOf(childrenTickets.size() + 1l));
		passengerAnalysisRequest.setOrganizationName(ptoSearchRequest2.getOrganizationName());
		passengerAnalysisRequest.setPtoName(ptoSearchRequest2.getPtoName());
	}

}
