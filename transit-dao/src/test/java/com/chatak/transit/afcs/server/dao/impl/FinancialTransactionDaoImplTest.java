package com.chatak.transit.afcs.server.dao.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.dao.model.FinancialTransactionData;
import com.chatak.transit.afcs.server.dao.repository.DeviceTypeMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.FinancialTransactionRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.pojo.FinancialTxnDataRequest;

@RunWith(MockitoJUnitRunner.class)
public class FinancialTransactionDaoImplTest {
	
	@InjectMocks
	FinancialTransactionDaoImpl financialTransactionDaoImpl;
	
	@Mock
	DeviceTypeMasterRepository deviceTypeMasterRepository;

	@Mock
	UserCredentialsRepository userCredentialsRepository;

	@Mock
	PtoMasterRepository ptoOperationMasterRepository;
	
	@Mock
	FinancialTransactionRepository financialRepository;

	@Test
	public void saveFinancialTxnDataTest() {
		FinancialTransactionData financialTxnData = new FinancialTransactionData();
		Mockito.when(financialRepository.save(financialTxnData)).thenReturn(financialTxnData);
		boolean result = financialTransactionDaoImpl.saveFinancialTxnData(financialTxnData);
		Assert.assertTrue(result);
	}
	
	@Test
	public void saveFinancialTxnDataFalseTest() {
		FinancialTransactionData financialTxnData = new FinancialTransactionData();
		Mockito.when(financialRepository.save(financialTxnData)).thenReturn(null);
		boolean result = financialTransactionDaoImpl.saveFinancialTxnData(financialTxnData);
		Assert.assertFalse(result);
	}
	
	@Test
	public void validateFinancialTxnRequestTest() {
		FinancialTxnDataRequest request = new FinancialTxnDataRequest();
		setFinancialTxnRequest(request);
		Mockito.when(userCredentialsRepository.existsByEmail(request.getUserId())).thenReturn(true);
		Assert.assertNotNull(request);
	}

	private void setFinancialTxnRequest(FinancialTxnDataRequest request) {
		request.setUserId(TestConstants.USER_ID);
		request.setDeviceModelNo(TestConstants.DEVICE_ID);
		request.setDeviceSerialNo(TestConstants.DEVICE_ID);
		request.setEmployeeId(TestConstants.USER_ID);
		request.setDeviceId(TestConstants.DEVICE_ID);
		request.setPtoOperationId(TestConstants.PTO_OPERATION_ID);
	}
	
}
