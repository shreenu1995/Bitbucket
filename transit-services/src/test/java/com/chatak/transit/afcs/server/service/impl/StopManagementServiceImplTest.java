package com.chatak.transit.afcs.server.service.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.StopManagementDao;
import com.chatak.transit.afcs.server.dao.model.StopProfile;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.web.StopRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.StopRegistrationResponse;
@RunWith(MockitoJUnitRunner.Silent.class)
public class StopManagementServiceImplTest {

	@InjectMocks
	StopManagementServiceImpl stopManagementServiceImpl;

	@Mock
	StopManagementDao stopManagementDao;

	@Mock
	CustomErrorResolution dataValidation;

	@Mock
	StopRegistrationRequest stopRegistrationRequest;

	@Mock
	StopRegistrationResponse stopRegistrationResponse;

	@Mock
	HttpServletResponse httpResponse;

	@Mock
	StopProfile stopProfile;

	@Test
	public void testStopRegistration() throws IOException {
		stopRegistrationRequest = new StopRegistrationRequest();
		stopRegistrationRequest.setUserId(TestConstants.USER_ID);
		stopProfile = new StopProfile();
		stopRegistrationResponse = new StopRegistrationResponse();
		stopRegistrationResponse.setStopId(TestConstants.LONG_ONE);
		stopRegistrationResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		stopRegistrationResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		Mockito.when(stopManagementDao.validateStopRegistrationRequest(stopRegistrationRequest)).thenReturn(true);
		Assert.assertNotNull(stopRegistrationResponse.getStopId());
		Assert.assertEquals(CustomErrorCodes.SUCCESS.getCustomErrorMsg(), stopRegistrationResponse.getStatusMessage());
		Assert.assertEquals(CustomErrorCodes.SUCCESS.getCustomErrorCode(), stopRegistrationResponse.getStatusCode());
	}

	@Test
	public void testStopRegistrationTransactionIdInvalid() throws IOException {
		Mockito.when(stopManagementDao.validateStopRegistrationRequest(stopRegistrationRequest)).thenReturn(false);
		stopRegistrationResponse = new StopRegistrationResponse();
		stopRegistrationResponse.setStopId(TestConstants.LONG_ONE);
		stopRegistrationResponse.setStatusCode(CustomErrorCodes.INVALID_TRANSACTION_ID.getCustomErrorCode());
		stopRegistrationResponse.setStatusMessage(CustomErrorCodes.INVALID_TRANSACTION_ID.getCustomErrorMsg());
		stopRegistrationResponse = stopManagementServiceImpl.stopRegistration(stopRegistrationRequest, httpResponse,
				stopRegistrationResponse);
	}

	@Test
	public void testStopRegistrationUserIdInvalid() throws IOException {
		stopRegistrationRequest = new StopRegistrationRequest();
		stopRegistrationRequest.setUserId(TestConstants.USER_ID);
		Mockito.when(stopManagementDao.validateStopRegistrationRequest(stopRegistrationRequest)).thenReturn(false);
		stopRegistrationResponse = new StopRegistrationResponse();
		stopRegistrationResponse.setStopId(TestConstants.LONG_ONE);
		stopRegistrationResponse.setStatusCode(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode());
		stopRegistrationResponse.setStatusMessage(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
		stopRegistrationResponse = stopManagementServiceImpl.stopRegistration(stopRegistrationRequest, httpResponse,
				stopRegistrationResponse);
		Assert.assertEquals((CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg()),
				stopRegistrationResponse.getStatusMessage());
		Assert.assertEquals(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode(),
				stopRegistrationResponse.getStatusCode());
	}

	@Test
	public void testStopRegistrationPtoIDValidation() throws IOException {
		stopRegistrationRequest = new StopRegistrationRequest();
		stopRegistrationRequest.setUserId(TestConstants.USER_ID);
		Mockito.when(stopManagementDao.validateStopRegistrationRequest(stopRegistrationRequest)).thenReturn(false);
		Mockito.when(dataValidation.isValidUser(stopRegistrationRequest.getUserId())).thenReturn(true);
		stopRegistrationResponse = new StopRegistrationResponse();
		stopRegistrationResponse.setStopId(TestConstants.LONG_ONE);
		stopRegistrationResponse.setStatusCode(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorCode());
		stopRegistrationResponse.setStatusMessage(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorMsg());
		stopRegistrationResponse = stopManagementServiceImpl.stopRegistration(stopRegistrationRequest, httpResponse,
				stopRegistrationResponse);
	}

	@Test
	public void testStopRegistrationCheckPtoIDValidation() throws IOException {
		stopRegistrationRequest = new StopRegistrationRequest();
		stopRegistrationRequest.setUserId(TestConstants.USER_ID);
		Mockito.when(stopManagementDao.validateStopRegistrationRequest(stopRegistrationRequest)).thenReturn(false);
		Mockito.when(dataValidation.isValidUser(stopRegistrationRequest.getUserId())).thenReturn(true);
		stopRegistrationResponse = new StopRegistrationResponse();
		stopRegistrationResponse.setStopId(TestConstants.LONG_ONE);
		stopRegistrationResponse.setStatusCode(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorCode());
		stopRegistrationResponse.setStatusMessage(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorMsg());
		stopRegistrationResponse = stopManagementServiceImpl.stopRegistration(stopRegistrationRequest, httpResponse,
				stopRegistrationResponse);
	}

}
