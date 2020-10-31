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
import com.chatak.transit.afcs.server.pojo.web.TransactionReportGenerationRequest;
import com.chatak.transit.afcs.server.pojo.web.TransactionReportGenerationResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.TransactionReportGenerationService;

@RunWith(MockitoJUnitRunner.class)
public class TransactionReportGenerationRestControllerTest {

	@InjectMocks
	TransactionReportGenerationRestController transactionReportGenerationRestController;

	@Mock
	TransactionReportGenerationService transactionReportGenerationService;

	@Mock
	TransactionReportGenerationRequest request;

	@Mock
	TransactionReportGenerationResponse response;

	@Mock
	BindingResult bindingResult;
	
	@Mock
	HttpServletResponse httpResponse;

	@Mock
	WebResponse webResponse;

	@Mock
	List<String> errorCodeList;
	
	@Mock
	Environment property;

	@Mock
	List<ObjectError> objectErrors;

	@Test
	public void testGenerateTransactionReport() throws IOException {
		response = new TransactionReportGenerationResponse();
		response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		Mockito.when(transactionReportGenerationService.generateTransactionReport(request, httpResponse))
				.thenReturn(response);
		response = transactionReportGenerationRestController.generateTransactionReport(request, bindingResult, response,
				httpResponse);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getStatusCode(), CustomErrorCodes.SUCCESS.getCustomErrorCode());
		Assert.assertEquals(response.getStatusMessage(), CustomErrorCodes.SUCCESS.getCustomErrorMsg());
	}

	@Test
	public void testGenerateTransactionReportBindingResultsErrors() throws IOException {
		response = new TransactionReportGenerationResponse();
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
		response = transactionReportGenerationRestController.generateTransactionReport(request, bindingResult, response,
				httpResponse);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getStatusCode(), errorCode);
	}
}
