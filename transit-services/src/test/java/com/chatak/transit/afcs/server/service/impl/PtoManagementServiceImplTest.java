package com.chatak.transit.afcs.server.service.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.OrganizationManagementDao;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.web.OrganizationRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationRegistrationResponse;

public class PtoManagementServiceImplTest {

	@InjectMocks
	OrganizationManagementServiceImpl ptoManagementServiceImpl;

	@Mock
	OrganizationRegistrationRequest request;

	@Mock
	HttpServletResponse httpResponse;

	@Mock
	OrganizationManagementDao ptoManagementDao;

	@Mock
	CustomErrorResolution dataValidation;

	@Before
	public void init() {
	MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void savePtoRegistrationSuccessTest() throws IOException {
		request = new OrganizationRegistrationRequest();
		OrganizationRegistrationResponse ptoRegistrationResponse = new OrganizationRegistrationResponse();
		ptoRegistrationResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		setPtoRegistrationRequest();
		Mockito.when(ptoManagementDao.validateOrganizationRegistration(Matchers.any())).thenReturn(true);
		ptoRegistrationResponse = ptoManagementServiceImpl.createOrganization(request, httpResponse,
				ptoRegistrationResponse);
	}

	private void setPtoRegistrationRequest() {
		request.setCity(TestConstants.STATUS_MESSAGE);
		request.setState(TestConstants.STATUS_SUCCESS);
		request.setUserId(TestConstants.USER_ID);
	}

	@Test
	public void savePtoRegistrationServerErrorTest() throws IOException {
		request = new OrganizationRegistrationRequest();
		OrganizationRegistrationResponse ptoResponse = new OrganizationRegistrationResponse();
		ptoResponse.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
		setPtoRegistrationRequest();
		Mockito.when(ptoManagementDao.validateOrganizationRegistration(Matchers.any())).thenReturn(true);
		ptoResponse = ptoManagementServiceImpl.createOrganization(request, httpResponse,
				ptoResponse);
		Assert.assertEquals(ptoResponse.getStatusCode(), CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
	}

	@Test
	public void savePtoRegistartionInvaliUserIdTest() throws IOException {
		OrganizationRegistrationResponse ptoResponse = new OrganizationRegistrationResponse();
		request= new OrganizationRegistrationRequest();
		request.setUserId(TestConstants.USER_ID);
		Mockito.when(dataValidation.isValidUser(Matchers.anyString())).thenReturn(false);
		OrganizationRegistrationResponse response = ptoManagementServiceImpl.createOrganization(request, httpResponse,
				ptoResponse);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getStatusCode(), CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode());
	}

}
