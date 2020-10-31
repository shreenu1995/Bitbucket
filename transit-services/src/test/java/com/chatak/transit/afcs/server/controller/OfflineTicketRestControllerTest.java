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
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;

import com.chatak.transit.afcs.constants.TestConstants;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.exception.PosException;
import com.chatak.transit.afcs.server.pojo.TicketTransactionDataRequest;
import com.chatak.transit.afcs.server.pojo.TicketTransactionDataResponse;
import com.chatak.transit.afcs.server.service.TicketTransactionService;

@RunWith(MockitoJUnitRunner.class)
public class OfflineTicketRestControllerTest {

	@InjectMocks
	OfflineTicketRestController offlineTicketRestController;

	@Mock
	TicketTransactionService ticketTransactionService;

	@Mock
	TicketTransactionDataRequest ticketTransactionDataRequest;

	@Mock
	TicketTransactionDataResponse ticketTransactionDataResponse;

	@Mock
	HttpServletResponse httpResponse;

	@Mock
	BindingResult bindingResult;

	@Test
	public void testSaveTicketDataRequestInvalidPacketLength() throws PosException, ParseException, IOException {
		ticketTransactionDataResponse = new TicketTransactionDataResponse();
		ticketTransactionDataResponse.setStatusCode(CustomErrorCodes.INVALID_PACKET_LENGTH.getCustomErrorCode());
		ticketTransactionDataResponse.setStatusMessage(CustomErrorCodes.INVALID_PACKET_LENGTH.getCustomErrorMsg());
		String data = offlineTicketRestController.saveTicketDataRequest(TestConstants.USER_ID, httpResponse,
				ticketTransactionDataResponse, bindingResult);
		Assert.assertEquals(CustomErrorCodes.INVALID_PACKET_LENGTH.getCustomErrorCode(),
				data.substring(TestConstants.INT_ZERO, TestConstants.INT_ONE));
	}

	@Test
	public void testSaveTicketDataRequest() throws PosException, ParseException, IOException {
		ticketTransactionDataResponse = new TicketTransactionDataResponse();
		ticketTransactionDataResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		ticketTransactionDataResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		Mockito.when(ticketTransactionService.validateTicketDataLength(TestConstants.TICKET_TRANSACTION_DATA_REQUEST))
				.thenReturn(true);
		Mockito.when(ticketTransactionService.saveTicketDataRequest(Matchers.any(), Matchers.any(), Matchers.any()))
				.thenReturn(TestConstants.STATUS_CODE);
		String data = offlineTicketRestController.saveTicketDataRequest(TestConstants.TICKET_TRANSACTION_DATA_REQUEST,
				httpResponse, ticketTransactionDataResponse, bindingResult);
		Assert.assertNotNull(data);
		Assert.assertEquals(CustomErrorCodes.SUCCESS.getCustomErrorCode(),
				data.substring(TestConstants.INT_ZERO, TestConstants.INT_ONE));

	}
}
