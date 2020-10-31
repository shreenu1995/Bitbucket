package com.chatak.transit.afcs.server.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.exception.PosException;
import com.chatak.transit.afcs.server.pojo.TerminalSetUpRequest;
import com.chatak.transit.afcs.server.pojo.TerminalSetUpResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.TerminalSetUpService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OfflineTerminalSetupRestControllerTest {

	@InjectMocks
	OfflineTerminalSetupRestController offlineTerminalSetupRestController;

	@Mock
	TerminalSetUpService terminalSetupService;

	@Mock
	TerminalSetUpRequest request;

	@Mock
	BindingResult bindingResult;

	@Mock
	TerminalSetUpResponse response;

	@Mock
	HttpServletResponse httpResponse;

	@Mock
	WebResponse webResponse;

	@Test
	public void testGetTerminalSetupInvalidRequest() throws IOException, ParseException, PosException {
		response = new TerminalSetUpResponse();
		response.setStatusCode(CustomErrorCodes.INVALID_PACKET_LENGTH.getCustomErrorCode());
		response.setStatusMessage(CustomErrorCodes.INVALID_PACKET_LENGTH.getCustomErrorMsg());
		Mockito.when(terminalSetupService.validateSetupDataLength(TestConstants.TERMINAL_SETUP_REQUEST))
				.thenReturn(true);
		Mockito.when(terminalSetupService.getTerminalSetupRequest(Matchers.any(), Matchers.any()))
				.thenReturn(TestConstants.STRING_ONE);
		String responses = offlineTerminalSetupRestController.getTerminalSetup(TestConstants.ADMIN_SESSION_REQUEST,
				httpResponse, bindingResult);
		Assert.assertNotNull(responses);
		Assert.assertEquals(responses.substring(TestConstants.INT_ZERO, TestConstants.INT_ONE),
				CustomErrorCodes.INVALID_PACKET_LENGTH.getCustomErrorCode());
	}

	@Test
	public void testGetTerminalSetupValidRequest() throws IOException, ParseException, PosException {
		String successResponse = TestConstants.STATUS_CODE;
		response = new TerminalSetUpResponse();
		response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		Mockito.when(terminalSetupService.validateSetupDataLength(TestConstants.TERMINAL_SETUP_REQUEST))
				.thenReturn(true);
		Mockito.when(terminalSetupService.getTerminalSetupRequest(Matchers.any(), Matchers.any()))
				.thenReturn(successResponse);
		String responses = offlineTerminalSetupRestController.getTerminalSetup(TestConstants.TERMINAL_SETUP_REQUEST,
				httpResponse, bindingResult);
		Assert.assertNotNull(responses);
		Assert.assertEquals(responses.substring(TestConstants.INT_ZERO, TestConstants.INT_ONE),
				CustomErrorCodes.SUCCESS.getCustomErrorCode());
	}

}
