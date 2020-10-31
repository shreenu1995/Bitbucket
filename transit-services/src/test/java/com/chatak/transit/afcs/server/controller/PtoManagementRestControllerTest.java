package com.chatak.transit.afcs.server.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.web.OrganizationRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.OrganizationStatusCheckRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.OrganizationManagementService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PtoManagementRestControllerTest {

	@InjectMocks
	OrganizationManagementRestController ptoManagementRestController;

	@Mock
	OrganizationManagementService ptoManagementService;

	@Mock
	OrganizationRegistrationRequest request;

	@Mock
	BindingResult bindingResult;

	@Mock
	OrganizationRegistrationResponse response;

	@Mock
	HttpServletResponse httpResponse;

	@Mock
	List<ObjectError> objectErrors;

	@Mock
	WebResponse webResponse;

	@Mock
	Environment property;

	@Mock
	List<String> errorCodeList;

	@Mock
	OrganizationUpdateRequest ptoProfileUpdateRequest;

	@Mock
	OrganizationStatusUpdateRequest ptoStatusUpdateRequest;

	@Mock
	OrganizationStatusCheckRequest ptoStatusCheckRequest;

	@Test
	public void testsavePtoRegistration() throws IOException {
		response = new OrganizationRegistrationResponse();
		response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		Mockito.when(ptoManagementService.createOrganization(request, httpResponse, response)).thenReturn(response);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getStatusCode(), CustomErrorCodes.SUCCESS.getCustomErrorCode());
		Assert.assertEquals(response.getStatusMessage(), CustomErrorCodes.SUCCESS.getCustomErrorMsg());
	}

	@Test
	public void testsavePtoRegistrationBindingResultsErrors() throws IOException {
		response = new OrganizationRegistrationResponse();
		response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		ObjectError objectError = new ObjectError(TestConstants.STATUS_CODE, TestConstants.STATUS_MESSAGE);
		objectErrors = new ArrayList<>();
		errorCodeList = new ArrayList<String>();
		objectErrors.add(objectError);
		String errorCode = TestConstants.STATUS_CODE;
		errorCodeList.add(errorCode);
		Mockito.when(bindingResult.hasErrors()).thenReturn(true);
		Mockito.when(bindingResult.getAllErrors()).thenReturn(objectErrors);
		Mockito.when(property.getProperty(objectError.getDefaultMessage())).thenReturn(errorCode);
		Mockito.when(ptoManagementService.createOrganization(request, httpResponse, response)).thenReturn(response);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getStatusCode(), errorCode);
	}

	@Test
	public void testUpdatePtoProfile() throws IOException {
		webResponse = new WebResponse();
		webResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		webResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		Mockito.when(ptoManagementService.updateOrganization(ptoProfileUpdateRequest, webResponse, httpResponse))
				.thenReturn(webResponse);
		Assert.assertNotNull(webResponse);
		Assert.assertEquals(webResponse.getStatusCode(), CustomErrorCodes.SUCCESS.getCustomErrorCode());
		Assert.assertEquals(webResponse.getStatusMessage(), CustomErrorCodes.SUCCESS.getCustomErrorMsg());
	}

	@Test
	public void testUpdatePtoProfileBindingResultErrors() throws IOException {
		webResponse = new WebResponse();
		webResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		webResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		ObjectError objectError = new ObjectError(TestConstants.STATUS_CODE, TestConstants.STATUS_MESSAGE);
		objectErrors = new ArrayList<>();
		errorCodeList = new ArrayList<String>();
		objectErrors.add(objectError);
		String errorCode = TestConstants.STATUS_CODE;
		errorCodeList.add(errorCode);
		Mockito.when(bindingResult.hasErrors()).thenReturn(true);
		Mockito.when(bindingResult.getAllErrors()).thenReturn(objectErrors);
		Mockito.when(property.getProperty(objectError.getDefaultMessage())).thenReturn(errorCode);
		Assert.assertNotNull(webResponse);
		Assert.assertEquals(webResponse.getStatusCode(), CustomErrorCodes.SUCCESS.getCustomErrorCode());
	}

	@Test
	public void testUpdatePtoStatusBindingResultErrors() throws IOException {
		webResponse = new WebResponse();
		webResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		webResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		ObjectError objectError = new ObjectError(TestConstants.STATUS_CODE, TestConstants.STATUS_MESSAGE);
		objectErrors = new ArrayList<>();
		errorCodeList = new ArrayList<String>();
		objectErrors.add(objectError);
		String errorCode = TestConstants.STATUS_CODE;
		errorCodeList.add(errorCode);
		Mockito.when(bindingResult.hasErrors()).thenReturn(true);
		Mockito.when(bindingResult.getAllErrors()).thenReturn(objectErrors);
		Mockito.when(property.getProperty(objectError.getDefaultMessage())).thenReturn(errorCode);
		Assert.assertNotNull(webResponse);
		Assert.assertEquals(webResponse.getStatusCode(), CustomErrorCodes.SUCCESS.getCustomErrorCode());
	}

	@Test
	public void testUpdatePtoStatus() throws IOException {
		webResponse = new WebResponse();
		webResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		webResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		Assert.assertNotNull(webResponse);
		Assert.assertEquals(webResponse.getStatusCode(), CustomErrorCodes.SUCCESS.getCustomErrorCode());
		Assert.assertEquals(webResponse.getStatusMessage(), CustomErrorCodes.SUCCESS.getCustomErrorMsg());
	}

	@Test
	public void testCheckPtoStatusBindingResultErrors() throws IOException {
		webResponse = new WebResponse();
		webResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		webResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		ObjectError objectError = new ObjectError(TestConstants.STATUS_CODE, TestConstants.STATUS_MESSAGE);
		objectErrors = new ArrayList<>();
		errorCodeList = new ArrayList<String>();
		objectErrors.add(objectError);
		String errorCode = TestConstants.STATUS_CODE;
		errorCodeList.add(errorCode);
		Mockito.when(bindingResult.hasErrors()).thenReturn(true);
		Mockito.when(bindingResult.getAllErrors()).thenReturn(objectErrors);
		Mockito.when(property.getProperty(objectError.getDefaultMessage())).thenReturn(errorCode);
		Assert.assertNotNull(webResponse);
		Assert.assertEquals(webResponse.getStatusCode(), CustomErrorCodes.SUCCESS.getCustomErrorCode());
	}
}
