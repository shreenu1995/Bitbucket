package com.chatak.transit.afcs.server.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.web.RoleRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.RoleRegistrationResponse;

@RunWith(MockitoJUnitRunner.Silent.class)
public class RoleManagementServiceImplTest {
	
	@Mock
	CustomErrorResolution dataValidation;

	@Mock
	RoleRegistrationRequest request;

	@Mock
	RoleRegistrationResponse response;

	@Mock
	HttpServletResponse httpResponse;
	
	@Mock
	List<Integer> privilegesId;

	@Test
	public void testSaveRoleRegistration() throws IOException {
		request = new RoleRegistrationRequest();
		request.setUserId(TestConstants.USER_ID);
		request.setPtoId(TestConstants.PTO_ID);
		privilegesId = new ArrayList<>();
		privilegesId.add(TestConstants.INT_EIGHT);
		request.setPrivilegesId(privilegesId);
		response = new RoleRegistrationResponse();
		response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		Assert.assertEquals(CustomErrorCodes.SUCCESS.getCustomErrorMsg(), response.getStatusMessage());
		Assert.assertEquals(CustomErrorCodes.SUCCESS.getCustomErrorCode(), response.getStatusCode());
	}

	@Test
	public void testSaveRoleRegistrationFalse() throws IOException {
		request = new RoleRegistrationRequest();
		request.setUserId(TestConstants.USER_ID);
		request.setPtoId(TestConstants.PTO_ID);
		privilegesId = new ArrayList<>();
		privilegesId.add(TestConstants.INT_EIGHT);
		request.setPrivilegesId(privilegesId);
		response = new RoleRegistrationResponse();
		response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
		response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
		Assert.assertEquals(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg(), response.getStatusMessage());
		Assert.assertEquals(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode(), response.getStatusCode());
	}

	@Test
	public void testSaveRoleRegistrationTransactionIdInvalid() throws IOException {
		response = new RoleRegistrationResponse();
		response.setStatusCode(CustomErrorCodes.INVALID_TRANSACTION_ID.getCustomErrorCode());
		response.setStatusMessage(CustomErrorCodes.INVALID_TRANSACTION_ID.getCustomErrorMsg());
		Assert.assertEquals(CustomErrorCodes.INVALID_TRANSACTION_ID.getCustomErrorMsg(), response.getStatusMessage());
		Assert.assertEquals(CustomErrorCodes.INVALID_TRANSACTION_ID.getCustomErrorCode(), response.getStatusCode());
	}

	@Test
	public void testSaveRoleRegistrationUserIdInvalid() throws IOException {
		request = new RoleRegistrationRequest();
		request.setUserId(TestConstants.USER_ID);
		privilegesId = new ArrayList<>();
		privilegesId.add(TestConstants.INT_EIGHT);
		request.setPrivilegesId(privilegesId);
		response = new RoleRegistrationResponse();
		response.setStatusCode(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode());
		response.setStatusMessage(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
		Assert.assertEquals((CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg()), response.getStatusMessage());
		Assert.assertEquals(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode(), response.getStatusCode());
	}

	@Test
	public void testSaveRoleRegistrationPtoIDValidation() throws IOException {
		request = new RoleRegistrationRequest();
		request.setUserId(TestConstants.USER_ID);
		request.setPtoId(TestConstants.PTO_ID);
		privilegesId = new ArrayList<>();
		privilegesId.add(TestConstants.INT_EIGHT);
		request.setPrivilegesId(privilegesId);
		Mockito.when(dataValidation.isValidUser(request.getUserId())).thenReturn(true);
		response = new RoleRegistrationResponse();
		response.setStatusCode(CustomErrorCodes.ORGANIZATION_NOT_EXIST.getCustomErrorCode());
		response.setStatusMessage(CustomErrorCodes.ORGANIZATION_NOT_EXIST.getCustomErrorMsg());
		Assert.assertEquals((CustomErrorCodes.ORGANIZATION_NOT_EXIST.getCustomErrorMsg()), response.getStatusMessage());
		Assert.assertEquals(CustomErrorCodes.ORGANIZATION_NOT_EXIST.getCustomErrorCode(), response.getStatusCode());
	}
}
