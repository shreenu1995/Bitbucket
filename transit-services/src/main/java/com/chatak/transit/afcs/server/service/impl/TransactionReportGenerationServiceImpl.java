package com.chatak.transit.afcs.server.service.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.constants.ServerConstants;
import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.TransactionReportGenerationDao;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.web.TransactionReportGenerationRequest;
import com.chatak.transit.afcs.server.pojo.web.TransactionReportGenerationResponse;
import com.chatak.transit.afcs.server.service.TransactionReportGenerationService;

@Service
public class TransactionReportGenerationServiceImpl implements TransactionReportGenerationService {

	@Autowired
	CustomErrorResolution dataValidation;

	@Autowired
	TransactionReportGenerationDao transactionReportGenerationDao;

	@Override
	public TransactionReportGenerationResponse generateTransactionReport(TransactionReportGenerationRequest request,
			HttpServletResponse httpResponse) throws IOException {
		TransactionReportGenerationResponse response = new TransactionReportGenerationResponse();
		if (transactionReportGenerationDao.validateRequest(request)) {
			response = transactionReportGenerationDao.getReportAll(request.getTransactionId(),
					request.getGenerationDateStart(), request.getGenerationDateEnd(), request.getIndexPage(),
					request.getOrganizationId(), request.getPtoId());
			if (response != null) {
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
				httpResponse.setCharacterEncoding(ServerConstants.CHAR_ENCODING_CONS);
				return response;
			}
		}
		checkTxnReportGeneationErrors(request, response);
		return response;
	}

	@Override
	public void checkTxnReportGeneationErrors(TransactionReportGenerationRequest request,
			TransactionReportGenerationResponse response) {
		if (!dataValidation.isValidUser(request.getUserId())) {
			response.setStatusCode(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
		}
	}

}
