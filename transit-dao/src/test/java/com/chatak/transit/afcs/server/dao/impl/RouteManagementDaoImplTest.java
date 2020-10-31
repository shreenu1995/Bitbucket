package com.chatak.transit.afcs.server.dao.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.transit.afcs.server.dao.model.RouteMaster;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.RouteManagementRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.pojo.web.RouteRegistrationRequest;

@RunWith(MockitoJUnitRunner.class)
public class RouteManagementDaoImplTest {

	@InjectMocks
	private RouteManagementDaoImpl routeManagementDaoImpl;

	@Mock
	RouteMaster routeMaster;

	@Mock
	RouteRegistrationRequest request;

	@Mock
	RouteManagementRepository routeManagementRepository;

	@Mock
	UserCredentialsRepository userCredentialRepository;

	@Mock
	PtoMasterRepository ptoOperationMasterRepository;

	@Test
	public void testValidateRouteRegistrationRequestRouteNameFalse() {
		request = new RouteRegistrationRequest();
		Mockito.when(userCredentialRepository.existsByEmail(request.getUserId())).thenReturn(true);
		Mockito.when(!routeManagementRepository.existsByRouteName(request.getRouteName())).thenReturn(false);
		boolean value = routeManagementDaoImpl.validateRouteRegistrationRequest(request);
		Assert.assertTrue(value);
	}
	
	@Test
	public void testValidateRouteRegistrationRequest() {
		request = new RouteRegistrationRequest();
		Mockito.when(userCredentialRepository.existsByEmail(request.getUserId())).thenReturn(true);
		Mockito.when(!routeManagementRepository.existsByRouteName(request.getRouteName())).thenReturn(true);
		boolean value = routeManagementDaoImpl.validateRouteRegistrationRequest(request);
	}

	@Test
	public void testValidateRouteRegistrationRequestRouteNameFail() {
		request = new RouteRegistrationRequest();
		Mockito.when(userCredentialRepository.existsByEmail(request.getUserId())).thenReturn(true);
		boolean value = routeManagementDaoImpl.validateRouteRegistrationRequest(request);
		Assert.assertTrue(value);
	}

	@Test
	public void testValidateRouteRegistrationRequestUserIdFail() {
		request = new RouteRegistrationRequest();
		Mockito.when(userCredentialRepository.existsByEmail(request.getUserId())).thenReturn(false);
		boolean value = routeManagementDaoImpl.validateRouteRegistrationRequest(request);
		Assert.assertFalse(value);
	}
}
