package com.chatak.transit.afcs.server.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.HttpErrorCodes;
import com.chatak.transit.afcs.server.pojo.FinancialTxnDataRequest;
import com.chatak.transit.afcs.server.pojo.FinancialTxnDataResponse;
import com.chatak.transit.afcs.server.service.FinancialTransactionService;
import com.chatak.transit.afcs.server.util.Helper;

@RestController
@RequestMapping(value = "/offline/")
public class OfflineFinancialTransactionRestController {
	
	private static final String REQUEST_PACKET = "Request Packet : ";
	private static final String RESPONSE_PACKET = "Response Packet:= ";

	private static final Logger logger = LoggerFactory.getLogger(OfflineFinancialTransactionRestController.class);

	@Autowired
	FinancialTransactionService financialTransactionService;

	@PostMapping(value = "financialTxnData")
	public String saveFinancialTxnData(@RequestBody String data, HttpServletResponse httpResponse,
			FinancialTxnDataResponse response) throws IOException{
		logger.info("Entering:: OfflineFinancialTxnRestController:: saveFinancialTxnDataRequest method");
		logger.debug( REQUEST_PACKET,data);
		FinancialTxnDataRequest request = new FinancialTxnDataRequest();

		if (financialTransactionService.validateFinancialTxnDataLength(data)) {
			Helper.parseFinancialTxnRequest(data, request);
			String responseData = financialTransactionService.saveFinancialTxn(request, httpResponse, response);
			logger.debug(RESPONSE_PACKET,responseData);
			return responseData;
		} else {
			response.setStatusCode(CustomErrorCodes.INVALID_PACKET_LENGTH.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_PACKET_LENGTH.getCustomErrorMsg());
			response.setReservedResponse("");
			response.setChecksum("");
			httpResponse.sendError(HttpErrorCodes.INTERNAL_SERVER_ERROR.getHttpErrorCode(), response.toString());
			logger.debug("Failed to save FinancialTxn Data");
			logger.debug("Invalid Financial Transaction Data Length");
		}

		return response.toString();

	}

}
