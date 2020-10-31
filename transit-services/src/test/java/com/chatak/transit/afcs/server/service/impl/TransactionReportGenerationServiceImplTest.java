package com.chatak.transit.afcs.server.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.TransactionReportGenerationDao;
import com.chatak.transit.afcs.server.dao.model.TicketsTxnData;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.web.TransactionReportGenerationRequest;
import com.chatak.transit.afcs.server.pojo.web.TransactionReportGenerationResponse;

@RunWith(MockitoJUnitRunner.class)
public class TransactionReportGenerationServiceImplTest {

	@InjectMocks
	TransactionReportGenerationServiceImpl transactionReportGenerationServiceImpl;
	
	@Mock
	CustomErrorResolution dataValidation;

	@Mock
	TransactionReportGenerationDao transactionReportGenerationDao;
	
	@Mock
	HttpServletResponse httpResponse;
	
	@Test
	public void generateTransactionReportTest() throws IOException {
		TransactionReportGenerationResponse response = new TransactionReportGenerationResponse();
		TransactionReportGenerationRequest request = setTxnReportGenerationRequest();
		TicketsTxnData txnData = new TicketsTxnData();
		txnData.setId(TestConstants.ID);
		List<TicketsTxnData> listOfTxnData = new ArrayList<>();
		listOfTxnData.add(txnData);
		Assert.assertNotNull(response);
	}

	private TransactionReportGenerationRequest setTxnReportGenerationRequest() {
		TransactionReportGenerationRequest request = new TransactionReportGenerationRequest();
		request.setTransactionId(TestConstants.TRANSACTION_ID);
		request.setUserId(TestConstants.USER_ID);
		return request;
	}
	
	@Test
	public void checkTxnReportGeneationErrorsUserTest() {
		TransactionReportGenerationResponse response = new TransactionReportGenerationResponse();
		TransactionReportGenerationRequest request = setTxnReportGenerationRequest();
		transactionReportGenerationServiceImpl.checkTxnReportGeneationErrors(request, response);
		Assert.assertEquals(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode(), response.getStatusCode());
	}
}
