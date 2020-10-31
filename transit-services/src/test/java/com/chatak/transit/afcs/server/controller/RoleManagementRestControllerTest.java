package com.chatak.transit.afcs.server.controller;

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
import org.springframework.core.env.Environment;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.web.RoleRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.RoleRegistrationResponse;

@RunWith(MockitoJUnitRunner.Silent.class)
public class RoleManagementRestControllerTest {

	@Mock
	RoleRegistrationRequest roleRequest;

	@Mock
	RoleRegistrationResponse roleResponse;

	@Mock
	HttpServletResponse httpResponse;
	
	@Mock
	BindingResult bindingResult;
	
	@Mock
	Environment property;

	@Mock
	List<String> errorCodeList;

	@Mock
	List<ObjectError> objectErrors;

	
	@Test
	public void testSaveRoleRegistrationBindingResultErrors() throws IOException {
		roleResponse = new RoleRegistrationResponse();
		roleResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		roleResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		ObjectError objectError = new ObjectError(TestConstants.STATUS_CODE, TestConstants.STATUS_MESSAGE);
		errorCodeList = new ArrayList<String>();
		objectErrors = new ArrayList<>();
		objectErrors.add(objectError);
		String errorCode = TestConstants.STATUS_CODE;
		errorCodeList.add(errorCode);
		Mockito.when(bindingResult.getAllErrors()).thenReturn(objectErrors);
		Mockito.when(property.getProperty(objectError.getDefaultMessage())).thenReturn(errorCode);
		Mockito.when(bindingResult.hasErrors()).thenReturn(true);
		Assert.assertNotNull(roleResponse);
		Assert.assertEquals(CustomErrorCodes.SUCCESS.getCustomErrorCode(), roleResponse.getStatusCode());
	}

}
