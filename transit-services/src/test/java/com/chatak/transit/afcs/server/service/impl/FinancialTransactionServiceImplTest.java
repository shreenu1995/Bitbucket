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
import org.mockito.junit.MockitoJUnitRunner;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.FinancialTransactionDao;
import com.chatak.transit.afcs.server.dao.repository.TicketTxnDataRepository;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.FinancialTxnDataRequest;
import com.chatak.transit.afcs.server.pojo.FinancialTxnDataResponse;
import com.chatak.transit.afcs.server.service.FinancialTxnPartService;

@RunWith(MockitoJUnitRunner.class)
public class FinancialTransactionServiceImplTest {

	@InjectMocks
	FinancialTransactionServiceImpl financialTransactionServiceImpl;

	@Mock
	FinancialTxnPartService finanPartService;

	@Mock
	CustomErrorResolution customErrorResolution;

	@Mock
	TicketTxnDataRepository ticketTxnRepository;

	@Mock
	FinancialTransactionDao financialDao;

	@Mock
	FinancialTxnDataRequest request;

	@Mock
	HttpServletResponse httpResponse;

	@Mock
	FinancialTxnDataResponse response;

	@Mock
	List<String> listOfTxnId;

	@Test
	public void testSaveFinancialTxnShiftEnd() throws IOException {
		String checkDefault = TestConstants.FINANCIAL_TXN_SHIFTEND;
		request = new FinancialTxnDataRequest();
		request.setFinancialTxnType(TestConstants.FINANCIAL_TXN_SHIFTEND);
		response = new FinancialTxnDataResponse();
		response.setStatusCode(CustomErrorCodes.INVALID_FINANCIAL_TXN_ID.getCustomErrorCode());
		response.setStatusMessage(CustomErrorCodes.INVALID_FINANCIAL_TXN_ID.getCustomErrorMsg());
		listOfTxnId = new ArrayList<>();
		listOfTxnId.add(TestConstants.FINANCIAL_TXN_SHIFTEND);
		String financialTxnDataResponse = financialTransactionServiceImpl.saveFinancialTxn(request, httpResponse,
				response);
		Assert.assertNotNull(response);
	}
	

	@Test
	public void testValidateFinancialTxnDataLength() {
		Assert.assertTrue(financialTransactionServiceImpl.validateFinancialTxnDataLength(TestConstants.FINANCIAL_TXN_DATA_REQUEST));
	}
	
	@Test
	public void testValidateFinancialTxnDataLengthInvalid() {
		Assert.assertFalse(financialTransactionServiceImpl.validateFinancialTxnDataLength(TestConstants.FINANCIAL_TXN_TRIPEND));
	}
	
}
