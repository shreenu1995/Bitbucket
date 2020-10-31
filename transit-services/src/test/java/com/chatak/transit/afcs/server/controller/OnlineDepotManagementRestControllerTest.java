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
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.pojo.web.DepotListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotListViewResponse;
import com.chatak.transit.afcs.server.pojo.web.DepotRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotRegistrationResponse;
import com.chatak.transit.afcs.server.service.DepotManagementService;

@RunWith(MockitoJUnitRunner.class)
public class OnlineDepotManagementRestControllerTest {

	@InjectMocks
	OnlineDepotManagementRestController onlineDepotManagementRestController;

	@Mock
	DepotRegistrationRequest request;

	@Mock
	HttpServletResponse httpResponse;

	@Mock
	DepotRegistrationResponse response;

	@Mock
	BindingResult bindingResult;

	@Mock
	DepotManagementService depotManagementService;

	@Mock
	DepotListViewRequest depotListViewRequest;

	@Mock
	DepotListViewResponse depotListViewResponse;
	
	@Mock
	Environment property;

	@Mock
	List<String> errorCodeList;

	@Mock
	List<ObjectError> objectErrors;
	
	@Test
	public void testDepotRegistration() throws IOException {
		request = new DepotRegistrationRequest();
		request.setDepotName(TestConstants.DEPOT_NAME);
		response = new DepotRegistrationResponse();
		response.setStatusCode(TestConstants.STATUS_CODE);
		response.setStatusMessage(TestConstants.STATUS_MESSAGE);
		Mockito.when(depotManagementService.saveDepotRegistrationrequest(request, httpResponse, response))
				.thenReturn(response);
		DepotRegistrationResponse result = onlineDepotManagementRestController.depotRegistration(request, bindingResult, httpResponse,
				response);
		Assert.assertNotNull(result);
		Assert.assertEquals(TestConstants.STATUS_MESSAGE, result.getStatusMessage());
		Assert.assertEquals(TestConstants.STATUS_CODE, result.getStatusCode());
	}

	@Test
	public void testDepotRegistrationBindingResultErrors() throws IOException {
		request = new DepotRegistrationRequest();
		request.setDepotName(TestConstants.DEPOT_NAME);
		response = new DepotRegistrationResponse();
		response.setStatusCode(TestConstants.STATUS_CODE);
		response.setStatusMessage(TestConstants.STATUS_MESSAGE);
		setBindingResultCommonScript();
		DepotRegistrationResponse result = onlineDepotManagementRestController.depotRegistration(request, bindingResult, httpResponse,
				response);
		Assert.assertNotNull(result);
		Assert.assertEquals(TestConstants.STATUS_CODE, result.getStatusCode());
	}
	
	@Test
	public void testDepotListView() throws IOException {
		depotListViewRequest = new DepotListViewRequest();
		depotListViewRequest.setUserId(TestConstants.USER_ID);
		depotListViewResponse = new DepotListViewResponse();
		depotListViewResponse.setStatusCode(TestConstants.STATUS_CODE);
		depotListViewResponse.setStatusMessage(TestConstants.STATUS_MESSAGE);
		Mockito.when(depotManagementService.getDepotListViewRequest(depotListViewRequest, depotListViewResponse,
				httpResponse, bindingResult)).thenReturn(depotListViewResponse);
		depotListViewResponse = onlineDepotManagementRestController.depotListView(depotListViewRequest, bindingResult,
				depotListViewResponse, httpResponse);
		Assert.assertNotNull(depotListViewResponse);
		Assert.assertEquals(TestConstants.STATUS_MESSAGE, depotListViewResponse.getStatusMessage());
		Assert.assertEquals(TestConstants.STATUS_CODE, depotListViewResponse.getStatusCode());
	}
	
	@Test
	public void testDepotListViewBindingResultErrors() throws IOException {
		depotListViewRequest = new DepotListViewRequest();
		depotListViewRequest.setUserId(TestConstants.USER_ID);
		depotListViewResponse = new DepotListViewResponse();
		depotListViewResponse.setStatusCode(TestConstants.STATUS_CODE);
		depotListViewResponse.setStatusMessage(TestConstants.STATUS_MESSAGE);
		setBindingResultCommonScript();
		depotListViewResponse = onlineDepotManagementRestController.depotListView(depotListViewRequest, bindingResult,
				depotListViewResponse, httpResponse);
		Assert.assertNotNull(depotListViewResponse);
		Assert.assertEquals(TestConstants.STATUS_CODE, depotListViewResponse.getStatusCode());
	}

	private void setBindingResultCommonScript() {
		ObjectError objectError = new ObjectError(TestConstants.STATUS_CODE, TestConstants.STATUS_MESSAGE);
		errorCodeList = new ArrayList<String>();
		objectErrors = new ArrayList<>();
		objectErrors.add(objectError);
		String errorCode = TestConstants.STATUS_CODE;
		errorCodeList.add(errorCode);
		Mockito.when(bindingResult.getAllErrors()).thenReturn(objectErrors);
		Mockito.when(property.getProperty(objectError.getDefaultMessage())).thenReturn(errorCode);
		Mockito.when(bindingResult.hasErrors()).thenReturn(true);
	}
}
