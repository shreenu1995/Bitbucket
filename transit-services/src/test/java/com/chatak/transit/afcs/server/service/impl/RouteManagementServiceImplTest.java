package com.chatak.transit.afcs.server.service.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.RouteManagementDao;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.web.RouteRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.RouteRegistrationResponse;

public class RouteManagementServiceImplTest {

	@InjectMocks
	private RouteManagementServiceImpl routeManagementServiceImpl;

	@Mock
	RouteRegistrationRequest request;

	@Mock
	RouteRegistrationResponse response;

	@Mock
	HttpServletResponse httpResponse;

	@Mock
	RouteManagementDao routeMangementDao;

	@Mock
	CustomErrorResolution dataValidation;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testRouteRegistartion() throws IOException {
		request = new RouteRegistrationRequest();
		response = new RouteRegistrationResponse();
		response.setStatusCode(TestConstants.STATUS_CODE);
		response.setStatusMessage(TestConstants.STATUS_MESSAGE);
		Mockito.when(routeMangementDao.validateRouteRegistrationRequest(request)).thenReturn(true);
		RouteRegistrationResponse result = routeManagementServiceImpl.routeRegistartion(request, response,
				httpResponse);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.getStatusCode(), CustomErrorCodes.SUCCESS.getCustomErrorCode());
	}

	@Test
	public void testRouteRegistartionTransactionIDValidation() throws IOException {
		request = new RouteRegistrationRequest();
		response = new RouteRegistrationResponse();
		response.setStatusCode(TestConstants.STATUS_CODE);
		response.setStatusMessage(TestConstants.STATUS_MESSAGE);
		Mockito.when(routeMangementDao.validateRouteRegistrationRequest(request)).thenReturn(false);
		RouteRegistrationResponse result = routeManagementServiceImpl.routeRegistartion(request, response,
				httpResponse);
		Assert.assertNotNull(result);
	}

	@Test
	public void testRouteRegistartionIsValidUser() throws IOException {
		request = new RouteRegistrationRequest();
		response = new RouteRegistrationResponse();
		response.setStatusCode(TestConstants.STATUS_CODE);
		response.setStatusMessage(TestConstants.STATUS_MESSAGE);
		Mockito.when(routeMangementDao.validateRouteRegistrationRequest(request)).thenReturn(false);
		RouteRegistrationResponse result = routeManagementServiceImpl.routeRegistartion(request, response,
				httpResponse);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.getStatusCode(), CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode());
		Assert.assertEquals(result.getStatusMessage(), CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
	}

	private void setRouteRegistrationRequest() {
		request = new RouteRegistrationRequest();
		request.setRouteName(TestConstants.ROUTE_NAME);
		response = new RouteRegistrationResponse();
		response.setStatusCode(TestConstants.STATUS_CODE);
		response.setStatusMessage(TestConstants.STATUS_MESSAGE);
		Mockito.when(routeMangementDao.validateRouteRegistrationRequest(request)).thenReturn(false);
		Mockito.when(!dataValidation.isValidUser(request.getUserId())).thenReturn(true);
	}

	@Test
	public void testRouteRegistartionRouteNameValidationFail() throws IOException {
		setRouteRegistrationRequest();
		Mockito.when(dataValidation.routeNameValidation(request.getRouteName())).thenReturn(false);
		RouteRegistrationResponse result = routeManagementServiceImpl.routeRegistartion(request, response,
				httpResponse);
		Assert.assertNotNull(result);
	}
}
