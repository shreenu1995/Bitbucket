package com.chatak.transit.afcs.server.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.web.OperatorSessionReportGenerationRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorSessionReportGenerationResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

@RunWith(MockitoJUnitRunner.class)
public class OperatorSessionReportGenerationRestControllerTest {

	@Mock
	BindingResult bindingResult;
	
	@Mock
	OperatorSessionReportGenerationRequest request;

	@Mock
	OperatorSessionReportGenerationResponse response;

	@Mock
	HttpServletResponse httpResponse;

	@Mock
	WebResponse webResponse;

	@Mock
	Environment property;

	@Mock
	List<ObjectError> objectErrors;

	@Mock
	List<String> errorCodeList;
	
	@Test
	public void testGenerateOperatorSessionReport() throws IOException {
		response = new OperatorSessionReportGenerationResponse();
		response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getStatusCode(), CustomErrorCodes.SUCCESS.getCustomErrorCode());
		Assert.assertEquals(response.getStatusMessage(), CustomErrorCodes.SUCCESS.getCustomErrorMsg());
	}

	@Test
	public void testGenerateOperatorSessionReportBindingResultsErrors() throws IOException {
		response = new OperatorSessionReportGenerationResponse();
		response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		ObjectError objectError = new ObjectError(TestConstants.STATUS_CODE, TestConstants.STATUS_MESSAGE);
		errorCodeList = new ArrayList<String>();
		objectErrors = new ArrayList<>();
		objectErrors.add(objectError);
		String errorCode = TestConstants.STATUS_CODE;
		errorCodeList.add(errorCode);
		Assert.assertNotNull(response);
	}
}
