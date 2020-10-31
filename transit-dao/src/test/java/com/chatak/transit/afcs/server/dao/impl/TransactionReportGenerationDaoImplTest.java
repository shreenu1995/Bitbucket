package com.chatak.transit.afcs.server.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.dao.model.TicketsTxnData;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.TicketTxnDataRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.pojo.web.TransactionReportGenerationRequest;
import com.chatak.transit.afcs.server.pojo.web.TransactionReportGenerationResponse;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class TransactionReportGenerationDaoImplTest {

	@InjectMocks
	TransactionReportGenerationDaoImpl reportGenerationDaoImpl;
	
	@Mock
	TicketTxnDataRepository ticketTransactionRepository;
	
	@Mock
	PtoMasterRepository ptoOperationMasterRepository;

	@Mock
	UserCredentialsRepository userCredentialsRepository;
	
	public static final String DATE_TIME = "2007-09-23 10:10:10.0";
	
	private List<TicketsTxnData> setTicketsTxnData() {
		TicketsTxnData txnData = new TicketsTxnData();
		txnData.setId(TestConstants.ID);
		List<TicketsTxnData> list = new ArrayList<>();
		list.add(txnData);
		return list;
	}
	
	@Test
	public void getReportDateTimeTest() {
		Timestamp time = Timestamp.valueOf(DATE_TIME);
		List<TicketsTxnData> list = setTicketsTxnData();
		Assert.assertNotNull(list);
	}
	
	@Test
	public void getReportAllPartNullTest() {
		List<TicketsTxnData> list = setTicketsTxnData();
		Timestamp time = Timestamp.valueOf(DATE_TIME);
		Mockito.when(ticketTransactionRepository.findAll()).thenReturn(list);
		List<TicketsTxnData> response = reportGenerationDaoImpl.getReportAllPart(TestConstants.TRANSACTION_ID,time,time,TestConstants.DEPOT_ID);
		Assert.assertNotNull(response);
	}
	
	@Test
	public void validateRequestTest() {
		TransactionReportGenerationRequest request = new TransactionReportGenerationRequest();
		request.setUserId(TestConstants.USER_ID);
		request.setPtoId(TestConstants.DEVICE_IDS);
		Mockito.when(userCredentialsRepository.existsByEmail(TestConstants.USER_ID)).thenReturn(true);
		boolean result = reportGenerationDaoImpl.validateRequest(request);
		Assert.assertTrue(result);
	}
	
	@Test
	public void validateRequestUserIdFalseTest() {
		TransactionReportGenerationRequest request = new TransactionReportGenerationRequest();
		request.setUserId(TestConstants.USER_ID);
		request.setPtoId(TestConstants.DEVICE_IDS);
		Mockito.when(userCredentialsRepository.existsByEmail(TestConstants.USER_ID)).thenReturn(false);
		boolean result = reportGenerationDaoImpl.validateRequest(request);
		Assert.assertFalse(result);
	}
	
}
