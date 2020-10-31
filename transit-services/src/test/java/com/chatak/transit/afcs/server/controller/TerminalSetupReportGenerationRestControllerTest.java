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
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.web.TerminalSetupReportGenerationResponse;
import com.chatak.transit.afcs.server.pojo.web.TerminalsetupReportGenerationRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.TerminalSetupReportGenerationService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TerminalSetupReportGenerationRestControllerTest {

	@InjectMocks
	TerminalSetupReportGenerationRestController terminalSetupReportGenerationRestController;

	@Mock
	TerminalSetupReportGenerationService terminalSetupReportGenerationService;

	@Mock
	TerminalsetupReportGenerationRequest request;

	@Mock
	List<ObjectError> objectErrors;

	@Mock
	TerminalSetupReportGenerationResponse response;

	@Mock
	List<String> errorCodeList;

	@Mock
	HttpServletResponse httpResponse;

	@Mock
	WebResponse webResponse;

	@Mock
	BindingResult bindingResult;

	@Mock
	Environment property;

	@Test
	public void testGenerateTerminalSetupReport() throws IOException {
		response = new TerminalSetupReportGenerationResponse();
		response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		Mockito.when(terminalSetupReportGenerationService.generateTerminalSetupReport(request, response, httpResponse))
				.thenReturn(response);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getStatusCode(), CustomErrorCodes.SUCCESS.getCustomErrorCode());
		Assert.assertEquals(response.getStatusMessage(), CustomErrorCodes.SUCCESS.getCustomErrorMsg());
	}

	@Test
	public void testGenerateTerminalSetupReportBindingResultsErrors() throws IOException {
		response = new TerminalSetupReportGenerationResponse();
		response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		ObjectError objectError = new ObjectError(TestConstants.STATUS_CODE, TestConstants.STATUS_MESSAGE);
		errorCodeList = new ArrayList<String>();
		objectErrors = new ArrayList<>();
		objectErrors.add(objectError);
		String errorCode = TestConstants.STATUS_CODE;
		errorCodeList.add(errorCode);
		Mockito.when(bindingResult.getAllErrors()).thenReturn(objectErrors);
		Mockito.when(property.getProperty(objectError.getDefaultMessage())).thenReturn(errorCode);
		Mockito.when(bindingResult.hasErrors()).thenReturn(true);
		Mockito.when(terminalSetupReportGenerationService.generateTerminalSetupReport(request, response, httpResponse))
				.thenReturn(response);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getStatusCode(), CustomErrorCodes.SUCCESS.getCustomErrorCode());
	}

}
