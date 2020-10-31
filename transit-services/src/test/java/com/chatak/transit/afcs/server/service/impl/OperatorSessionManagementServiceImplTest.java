package com.chatak.transit.afcs.server.service.impl;

import java.text.ParseException;

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
import com.chatak.transit.afcs.server.dao.OperatorSessionManagementDao;
import com.chatak.transit.afcs.server.dao.model.AdminSessionManagement;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.exception.PosException;
import com.chatak.transit.afcs.server.pojo.AdminSessionRequest;
import com.chatak.transit.afcs.server.pojo.AdminSessionResponse;
import com.chatak.transit.afcs.server.pojo.FileDownloadRequest;
import com.chatak.transit.afcs.server.pojo.FileDownloadResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OperatorSessionManagementServiceImplTest {

	@InjectMocks
	OperatorSessionManagementServiceImpl operatorSessionManagementServiceImpl;

	@Mock
	CustomErrorResolution dataValidation;
	
	@Mock
	OperatorSessionManagementDao operatorSessionManagementDao;
	
	@Mock
	AdminSessionRequest request;
	
	@Mock
	HttpServletResponse httpResponse;
	
	@Mock
	AdminSessionManagement adminSessionManagement;
	
	private static final String TRAVEL_TKT_DATA = "0015003996 TK112018-10-19  10:56:23200 2018-10-19P000101001  CHATAK00000110015003   CHATAKOP01     S1   6   T1  03U  1001      >";

	
	@Test
	public void saveAdminSessionRequestTest() throws PosException, ParseException, JsonProcessingException {
		AdminSessionResponse response = new AdminSessionResponse();;
		Mockito.when(operatorSessionManagementDao.validateAdminSessionRequest(request)).thenReturn(true);
		AdminSessionResponse adminResponse = operatorSessionManagementServiceImpl.saveAdminSessionRequest(request, httpResponse, response);
		Assert.assertNotNull(adminResponse);
		Assert.assertEquals(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode(), adminResponse.getStatusCode().trim());
	}
	
	@Test
	public void saveAdminSessionRequestElse() throws PosException, JsonProcessingException {
		AdminSessionResponse response = new AdminSessionResponse();;
		Mockito.when(operatorSessionManagementDao.validateAdminSessionRequest(request)).thenReturn(false);
		AdminSessionResponse adminResponse = operatorSessionManagementServiceImpl.saveAdminSessionRequest(request, httpResponse, response);
		Assert.assertNotNull(adminResponse);
	}
	
	@Test
	public void validateAdminDataLengthTest() {
		boolean result = operatorSessionManagementServiceImpl.validateAdminDataLength(TRAVEL_TKT_DATA);
		Assert.assertTrue(result);
	}
	
	@Test
	public void validateAdminDataLengthFalse() {
		String terminalData = TRAVEL_TKT_DATA+" ";
		boolean result = operatorSessionManagementServiceImpl.validateAdminDataLength(terminalData);
		Assert.assertFalse(result);
	}
	
	@Test
	public void validateFileDataLengthTest() {
		boolean result = operatorSessionManagementServiceImpl.validateFileDataLength(TRAVEL_TKT_DATA);
		Assert.assertTrue(result);
	}
	
	@Test
	public void validateFileDataLengthFalse() {
		String terminalData = TRAVEL_TKT_DATA+" ";
		boolean result = operatorSessionManagementServiceImpl.validateFileDataLength(terminalData);
		Assert.assertFalse(result);
	}
	
	@Test
	public void adminSessionErrorsUserIdTest() {
		setAdminUserData();
		AdminSessionResponse response = new AdminSessionResponse();
		Mockito.when(dataValidation.ptoIdValidation(Long.valueOf(request.getPtoId()))).thenReturn(true);
		Mockito.when(dataValidation.isValidUser(request.getUserId())).thenReturn(false);
		operatorSessionManagementServiceImpl.adminSessionErrors(request, response);
		Assert.assertEquals(response.getStatusCode().trim(), CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode());
		Assert.assertEquals(response.getStatusMessage().trim(), CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
	}
	
	@Test
	public void adminSessionErrorsEmployeeTest() {
		setAdminUserData();
		AdminSessionResponse response = new AdminSessionResponse();
		Mockito.when(dataValidation.ptoIdValidation(Long.valueOf(request.getPtoId()))).thenReturn(true);
		Mockito.when(dataValidation.isValidUser(request.getUserId())).thenReturn(true);
		operatorSessionManagementServiceImpl.adminSessionErrors(request, response);
		Assert.assertNotNull(response);
	}
	
	@Test
	public void adminSessionErrorsDeviceTest() {
		setAdminUserData();
		AdminSessionResponse response = new AdminSessionResponse();
		Mockito.when(dataValidation.ptoIdValidation(Long.valueOf(request.getPtoId()))).thenReturn(true);
		Mockito.when(dataValidation.isValidUser(request.getUserId())).thenReturn(true);
		operatorSessionManagementServiceImpl.adminSessionErrors(request, response);
		Assert.assertNotNull(request);
	}
	
	@Test
	public void adminSessionErrorsTxnIdRequest() {
		request.setUserId(TestConstants.USER_ID);
		setAdminUserData();
		AdminSessionResponse response = new AdminSessionResponse();
		Mockito.when(dataValidation.ptoIdValidation(Long.valueOf(request.getPtoId()))).thenReturn(true);
		Mockito.when(dataValidation.isValidUser(request.getUserId())).thenReturn(true);
		operatorSessionManagementServiceImpl.adminSessionErrors(request, response);
		Assert.assertNotNull(request);
	}
	
	private void setAdminUserData() {
		request.setDeviceId(TestConstants.DEVICE_ID);
		request.setTransactionId(TestConstants.TRANSACTION_ID);
		request.setEmpId(TestConstants.DEVICE_ID);
		request.setPtoId(TestConstants.ID);
	}
	
	@Test
	public void checkFileDownloadErrorsPtoTest() {
		FileDownloadResponse response = new FileDownloadResponse();
		FileDownloadRequest request = setFileDownloadRequest();
		operatorSessionManagementServiceImpl.checkFileDownloadErrors(request, response);
	}
	
	@Test
	public void checkFileDownloadErrorsDeviceTest() {
		FileDownloadResponse response = new FileDownloadResponse();
		FileDownloadRequest request = setFileDownloadRequest();
		operatorSessionManagementServiceImpl.checkFileDownloadErrors(request, response);
	}
	
	@Test
	public void checkFileDownloadErrorsTxnIdTest() {
		FileDownloadResponse response = new FileDownloadResponse();
		FileDownloadRequest request = setFileDownloadRequest();
		operatorSessionManagementServiceImpl.checkFileDownloadErrors(request, response);
	}

	private FileDownloadRequest setFileDownloadRequest() {
		FileDownloadRequest request = new FileDownloadRequest();
		request.setPtoOperationId(TestConstants.PTO_OPERATION_ID);
		request.setDeviceId(TestConstants.DEVICE_ID);
		request.setTransactionId(TestConstants.TRANSACTION_ID);
		return request;
	}
	
}
