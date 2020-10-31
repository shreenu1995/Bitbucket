package com.chatak.transit.afcs.server.service.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.constants.FinancialTxnConstants;
import com.chatak.transit.afcs.server.constants.ServerConstants;
import com.chatak.transit.afcs.server.controller.HttValidation;
import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.FinancialTransactionDao;
import com.chatak.transit.afcs.server.dao.repository.TicketTxnDataRepository;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.HttpErrorCodes;
import com.chatak.transit.afcs.server.pojo.FinancialTxnDataRequest;
import com.chatak.transit.afcs.server.pojo.FinancialTxnDataResponse;
import com.chatak.transit.afcs.server.service.FinancialTransactionService;
import com.chatak.transit.afcs.server.service.FinancialTxnPartService;

@Service
public class FinancialTransactionServiceImpl extends HttValidation implements FinancialTransactionService {

	Logger logger = LoggerFactory.getLogger(FinancialTransactionServiceImpl.class);

	@Autowired
	FinancialTxnPartService finanPartService;

	@Autowired
	CustomErrorResolution customErrorResolution;

	@Autowired
	TicketTxnDataRepository ticketTxnRepository;

	@Autowired
	FinancialTransactionDao financialDao;

	@Override
	public String saveFinancialTxn(FinancialTxnDataRequest request, HttpServletResponse httpResponse,
			FinancialTxnDataResponse response) throws IOException {
		String checkDefault = null;

		if (request.getFinancialTxnType().equals(ServerConstants.FINANCIAL_TXN_SHIFTEND)) {
			return finanPartService.saveFinancialTxnData(request, httpResponse, response, checkDefault);
		} 

		if (!getShiftEndError(request).equals(ServerConstants.STATUS)) {
			response.setReservedResponse("");
			response.setChecksum("");
			return getShiftEndError(request) + response.getReservedResponse() + response.getChecksum();
		}
		response.setStatusCode(CustomErrorCodes.INVALID_FINANCIAL_TXN_ID.getCustomErrorCode());
		response.setStatusMessage(CustomErrorCodes.INVALID_FINANCIAL_TXN_ID.getCustomErrorMsg());
		response.setReservedResponse("");
		response.setChecksum("");
		httpResponse.sendError(HttpErrorCodes.NOT_FOUND.getHttpErrorCode(), response.toString());
		return response.toString();
	}

	@Override
	public boolean validateFinancialTxnDataLength(String request) {
		return (request.length() == FinancialTxnConstants.FINANCIAL_TXN_DATA_LENGTH);
	}

	@Override
	public void checkFinancialTxnErrors(FinancialTxnDataRequest request, FinancialTxnDataResponse response) {
		if (!customErrorResolution.isValidUser(request.getUserId())) {
			response.setStatusCode(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
		} else if (!customErrorResolution.isValidDeviceModelNumber(request.getDeviceModelNo())) {
			response.setStatusCode(CustomErrorCodes.DEVICE_MODEL_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.DEVICE_MODEL_NOT_EXIST.getCustomErrorMsg());
		} else if (!customErrorResolution.ptoIdValidation(Long.valueOf(request.getPtoOperationId()))) {
			response.setStatusCode(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorMsg());
		}
	}

	@Override
	public void validationAmountOrCountForTxnId(String transactionId, FinancialTxnDataResponse response) {

		switch (transactionId) {
		case FinancialTxnConstants.TXN_TYPE_TT01:
			response.setStatusCode(CustomErrorCodes.INVALID_AMOUNT_COUNT_TXN_ID_TT01.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_AMOUNT_COUNT_TXN_ID_TT01.getCustomErrorMsg());
			break;
		case FinancialTxnConstants.TXN_TYPE_TT02:
			response.setStatusCode(CustomErrorCodes.INVALID_AMOUNT_COUNT_TXN_ID_TT02.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_AMOUNT_COUNT_TXN_ID_TT02.getCustomErrorMsg());
			break;
		case FinancialTxnConstants.TXN_TYPE_TP01:
			response.setStatusCode(CustomErrorCodes.INVALID_AMOUNT_COUNT_TXN_ID_TP01.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_AMOUNT_COUNT_TXN_ID_TP01.getCustomErrorMsg());
			break;
		case FinancialTxnConstants.TXN_TYPE_CP01:
			response.setStatusCode(CustomErrorCodes.INVALID_AMOUNT_COUNT_TXN_ID_CP01.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_AMOUNT_COUNT_TXN_ID_CP01.getCustomErrorMsg());
			break;
		case FinancialTxnConstants.TXN_TYPE_TL01:
			response.setStatusCode(CustomErrorCodes.INVALID_AMOUNT_COUNT_TXN_ID_TL01.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_AMOUNT_COUNT_TXN_ID_TL01.getCustomErrorMsg());
			break;
		case FinancialTxnConstants.TXN_TYPE_TK01:
			response.setStatusCode(CustomErrorCodes.INVALID_AMOUNT_COUNT_TXN_ID_TK01.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_AMOUNT_COUNT_TXN_ID_TK01.getCustomErrorMsg());
			break;
		default:
			response.setStatusCode(CustomErrorCodes.INVALID_TRANSACTION_ID.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_TRANSACTION_ID.getCustomErrorMsg());
			break;
		}
	}
}
