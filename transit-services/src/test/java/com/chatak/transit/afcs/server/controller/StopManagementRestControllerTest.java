package com.chatak.transit.afcs.server.controller;

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
import org.springframework.core.env.Environment;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.web.StopRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.StopRegistrationResponse;
import com.chatak.transit.afcs.server.service.StopManagementService;

@RunWith(MockitoJUnitRunner.class)
public class StopManagementRestControllerTest {

	@InjectMocks
	StopManagementRestController stopManagementRestController;

	@Mock
	StopRegistrationRequest stopRegistrationRequest;

	@Mock
	StopRegistrationResponse stopRegistrationResponse;

	@Mock
	HttpServletResponse httpResponse;

	@Mock
	BindingResult bindingResult;

	@Mock
	StopManagementService stopManagementService;
	
	@Mock
	Environment property;

	@Mock
	List<String> errorCodeList;

	@Mock
	List<ObjectError> objectErrors;

	private static final int INT_ONE = 1;

	@Test
	public void testStopRegistration() throws IOException {
		stopRegistrationResponse = new StopRegistrationResponse();
		stopRegistrationResponse.setStopId(INT_ONE);
		stopRegistrationResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		stopRegistrationResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		Mockito.when(
				stopManagementService.stopRegistration(stopRegistrationRequest, httpResponse, stopRegistrationResponse))
				.thenReturn(stopRegistrationResponse);
		stopRegistrationResponse = stopManagementRestController.stopRegistration(stopRegistrationRequest, bindingResult, httpResponse,
				stopRegistrationResponse);
		Assert.assertNotNull(stopRegistrationResponse.getStopId());
		Assert.assertEquals(CustomErrorCodes.SUCCESS.getCustomErrorCode(), stopRegistrationResponse.getStatusCode());
		Assert.assertEquals(CustomErrorCodes.SUCCESS.getCustomErrorMsg(), stopRegistrationResponse.getStatusMessage());

	}
	
	@Test
	public void testStopRegistrationBindingResultErrors() throws IOException {
		stopRegistrationResponse = new StopRegistrationResponse();
		stopRegistrationResponse.setStopId(INT_ONE);
		stopRegistrationResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		stopRegistrationResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		ObjectError objectError = new ObjectError(TestConstants.STATUS_CODE, TestConstants.STATUS_MESSAGE);
		errorCodeList = new ArrayList<String>();
		objectErrors = new ArrayList<>();
		objectErrors.add(objectError);
		String errorCode = TestConstants.STATUS_CODE;
		errorCodeList.add(errorCode);
		Mockito.when(bindingResult.getAllErrors()).thenReturn(objectErrors);
		Mockito.when(property.getProperty(objectError.getDefaultMessage())).thenReturn(errorCode);
		Mockito.when(bindingResult.hasErrors()).thenReturn(true);
		stopRegistrationResponse = stopManagementRestController.stopRegistration(stopRegistrationRequest, bindingResult, httpResponse,
				stopRegistrationResponse);
		Assert.assertNotNull(stopRegistrationResponse.getStopId());
		Assert.assertEquals(CustomErrorCodes.SUCCESS.getCustomErrorCode(), stopRegistrationResponse.getStatusCode());

	}
}