package com.chatak.transit.afcs.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.model.AdminSessionManagement;
import com.chatak.transit.afcs.server.pojo.web.OperatorSessionReportGenerationRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorSessionReportGenerationResponse;

@RunWith(MockitoJUnitRunner.class)
public class OperatorSessionReportGenerationServiceImplTest {

	@Mock
	OperatorSessionReportGenerationRequest operatorSessionReportGenerationRequest;

	@Mock
	OperatorSessionReportGenerationResponse operatorSessionReportGenerationResponse;

	@Mock
	HttpServletResponse httpServletResponse;

	@Mock
	CustomErrorResolution dataValidation;

	@Test
	public void testGenerateOperatorSessionReport() {
		operatorSessionReportGenerationRequest = new OperatorSessionReportGenerationRequest();
		List<AdminSessionManagement> listOfOperatorSession = new ArrayList();
		AdminSessionManagement adminSessionManagement = new AdminSessionManagement();
		adminSessionManagement.setPtoId(TestConstants.ID);
		adminSessionManagement.setUserId(TestConstants.USER_ID);
		adminSessionManagement.setTransactionId(TestConstants.TRANSACTION_ID);
		adminSessionManagement.setId(TestConstants.ID);
		listOfOperatorSession.add(adminSessionManagement);
		operatorSessionReportGenerationResponse = new OperatorSessionReportGenerationResponse();
		operatorSessionReportGenerationResponse.setStatusCode(TestConstants.STATUS_CODE);
		operatorSessionReportGenerationResponse.setStatusMessage(TestConstants.STATUS_MESSAGE);
		Assert.assertNotNull(operatorSessionReportGenerationResponse);
	}


	@Test
	public void testGenerateOperatorSessionValidationFalse() {
		operatorSessionReportGenerationRequest = new OperatorSessionReportGenerationRequest();
		operatorSessionReportGenerationRequest.setPtoOperationId(TestConstants.PTO_OPERATION_ID);
		operatorSessionReportGenerationResponse = new OperatorSessionReportGenerationResponse();
		operatorSessionReportGenerationResponse.setStatusCode(TestConstants.STATUS_CODE);
		operatorSessionReportGenerationResponse.setStatusMessage(TestConstants.STATUS_MESSAGE);
		Assert.assertNotNull(operatorSessionReportGenerationResponse);
	}

	@Test
	public void testGenerateOperatorSessionisValidUser() {
		operatorSessionReportGenerationRequest = new OperatorSessionReportGenerationRequest();
		operatorSessionReportGenerationRequest.setPtoOperationId(TestConstants.PTO_OPERATION_ID);
		operatorSessionReportGenerationRequest.setUserId(TestConstants.USER_ID);
		operatorSessionReportGenerationResponse = new OperatorSessionReportGenerationResponse();
		operatorSessionReportGenerationResponse.setStatusCode(TestConstants.STATUS_CODE);
		operatorSessionReportGenerationResponse.setStatusMessage(TestConstants.STATUS_MESSAGE);
		Assert.assertNotNull(operatorSessionReportGenerationResponse);
	}

}
