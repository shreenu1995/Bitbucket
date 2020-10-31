package com.chatak.transit.afcs.server.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.exception.PosException;
import com.chatak.transit.afcs.server.pojo.FinancialTxnDataRequest;
import com.chatak.transit.afcs.server.pojo.FinancialTxnDataResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.FinancialTransactionService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OfflineFinancialTransactionRestControllerTest {

	@InjectMocks
	OfflineFinancialTransactionRestController offlineFinancialTransactionRestController;

	@Mock
	FinancialTransactionService financialTransactionService;

	@Mock
	FinancialTxnDataRequest request;

	@Mock
	BindingResult bindingResult;

	@Mock
	FinancialTxnDataResponse response;

	@Mock
	HttpServletResponse httpResponse;

	@Mock
	WebResponse webResponse;

	@Test
	public void testSaveFinancialTxnDataInvalidRequest() throws IOException, ParseException, PosException {
		response = new FinancialTxnDataResponse();
		response.setStatusCode(CustomErrorCodes.INVALID_PACKET_LENGTH.getCustomErrorCode());
		response.setStatusMessage(CustomErrorCodes.INVALID_PACKET_LENGTH.getCustomErrorMsg());
		Mockito.when(financialTransactionService.validateFinancialTxnDataLength(TestConstants.FINANCIAL_TXN_DATA))
				.thenReturn(false);
		Mockito.when(financialTransactionService.saveFinancialTxn(Matchers.any(), Matchers.any(), Matchers.any()))
				.thenReturn(CustomErrorCodes.INVALID_PACKET_LENGTH.getCustomErrorCode());
		String responses = offlineFinancialTransactionRestController
				.saveFinancialTxnData(TestConstants.FINANCIAL_TXN_DATA, httpResponse, response);
		Assert.assertNotNull(responses);
		Assert.assertEquals(responses.substring(TestConstants.INT_ZERO, TestConstants.INT_ONE),
				CustomErrorCodes.INVALID_PACKET_LENGTH.getCustomErrorCode());
	}

	@Test
	public void testSaveFinancialTxnDataValidRequest() throws IOException, ParseException, PosException {
		String successResponse = TestConstants.STATUS_CODE;
		response = new FinancialTxnDataResponse();
		response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		Mockito.when(financialTransactionService.validateFinancialTxnDataLength(TestConstants.FINANCIAL_TXN_DATA))
				.thenReturn(true);
		Mockito.when(financialTransactionService.saveFinancialTxn(Matchers.any(), Matchers.any(), Matchers.any()))
				.thenReturn(successResponse);
		String responses = offlineFinancialTransactionRestController
				.saveFinancialTxnData(TestConstants.FINANCIAL_TXN_DATA, httpResponse, response);
		Assert.assertNotNull(responses);
		Assert.assertEquals(responses.substring(TestConstants.INT_ZERO, TestConstants.INT_ONE),
				CustomErrorCodes.SUCCESS.getCustomErrorCode());
	}
}
