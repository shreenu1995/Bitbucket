
/**
 * @author Girmiti Software
 *
 */

package com.chatak.transit.afcs.server.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.HttpErrorCodes;
import com.chatak.transit.afcs.server.pojo.TicketTransactionDataRequest;
import com.chatak.transit.afcs.server.pojo.TicketTransactionDataResponse;
import com.chatak.transit.afcs.server.service.TicketTransactionService;

@RestController
@RequestMapping(value = "/offline/")
public class OfflineTicketRestController {

	@Autowired
	TicketTransactionService ticketTxnService;

	private static Logger logger = LoggerFactory.getLogger(OfflineTicketRestController.class);
	
	private static final String REQUEST_PACKET = "Request Packet : ";

	@PostMapping(value = "saveTicketTxnData",consumes = { "text/plain" })
	public String saveTicketDataRequest(@RequestBody String data, HttpServletResponse httpResponse,
			TicketTransactionDataResponse response, BindingResult bindingResult)
			throws ParseException, IOException {

		String info = "Entering:: TicketTxnRestController:: saveTicketDataRequest method";
		logger.debug(info);
		logger.info(REQUEST_PACKET,data);
		TicketTransactionDataRequest request = new TicketTransactionDataRequest();
		if (ticketTxnService.validateTicketDataLength(data)) {
			request.parseTicketTxnRequest(data);
			return ticketTxnService.saveTicketDataRequest(request, response, httpResponse);
		} else {
			response.setStatusCode(CustomErrorCodes.INVALID_PACKET_LENGTH.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_PACKET_LENGTH.getCustomErrorMsg());
			response.setReservedResponse("");
			response.setChecksum("");
			httpResponse.sendError(HttpErrorCodes.BAD_REQUEST.getHttpErrorCode(), response.toString());
			logger.info("Failed to save Ticket Data");
			info = "Invalid Ticket Transaction Data Length";
			logger.info(info);
		}
		return response.toString();
	}
}