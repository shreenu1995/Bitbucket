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
import com.chatak.transit.afcs.server.pojo.web.RouteRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteRegistrationResponse;
import com.chatak.transit.afcs.server.service.RouteManagementService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class RouteManagementRestControllerTest {

	@InjectMocks
	private RouteManagementRestController routeManagementRestController;

	@Mock
	RouteRegistrationRequest request;

	@Mock
	RouteRegistrationResponse response;

	@Mock
	HttpServletResponse httpResponse;

	@Mock
	RouteManagementService routeManagementService;

	@Mock
	BindingResult bindingResult;

	@Mock
	Environment property;

	@Mock
	List<String> errorCodeList;

	@Mock
	List<ObjectError> objectErrors;

	@Test
	public void testRouteRegistration() throws IOException {
		request = new RouteRegistrationRequest();
		response = new RouteRegistrationResponse();
		response.setStatusCode(TestConstants.STATUS_CODE);
		response.setStatusMessage(TestConstants.STATUS_MESSAGE);
		Mockito.when(routeManagementService.routeRegistartion(request, response, httpResponse)).thenReturn(response);
		RouteRegistrationResponse result = routeManagementRestController.routeRegistration(request, bindingResult,
				response, httpResponse);
		Assert.assertNotNull(result);
		Assert.assertEquals(TestConstants.STATUS_MESSAGE, result.getStatusMessage());
		Assert.assertEquals(TestConstants.STATUS_CODE, result.getStatusCode());
	}

	@Test
	public void testRouteRegistrationBindingResultErrors() throws IOException {
		request = new RouteRegistrationRequest();
		response = new RouteRegistrationResponse();
		response.setStatusCode(TestConstants.STATUS_CODE);
		response.setStatusMessage(TestConstants.STATUS_MESSAGE);
		ObjectError objectError = new ObjectError(TestConstants.STATUS_CODE, TestConstants.STATUS_MESSAGE);
		errorCodeList = new ArrayList<String>();
		objectErrors = new ArrayList<>();
		objectErrors.add(objectError);
		String errorCode = TestConstants.STATUS_CODE;
		errorCodeList.add(errorCode);
		Mockito.when(bindingResult.getAllErrors()).thenReturn(objectErrors);
		Mockito.when(property.getProperty(objectError.getDefaultMessage())).thenReturn(errorCode);
		Mockito.when(bindingResult.hasErrors()).thenReturn(true);
		Mockito.when(routeManagementService.routeRegistartion(request, response, httpResponse)).thenReturn(response);
		RouteRegistrationResponse result = routeManagementRestController.routeRegistration(request, bindingResult,
				response, httpResponse);
		Assert.assertNotNull(result);
		Assert.assertEquals(TestConstants.STATUS_CODE, result.getStatusCode());
	}

}
