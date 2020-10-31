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
import com.chatak.transit.afcs.server.exception.PosException;
import com.chatak.transit.afcs.server.pojo.TerminalSetUpRequest;
import com.chatak.transit.afcs.server.pojo.TerminalSetUpResponse;
import com.chatak.transit.afcs.server.service.TerminalSetUpService;

@RestController
@RequestMapping(value = "/offline/")
public class OfflineTerminalSetupRestController {
	
	private static final String LENGTH_CHECK = "RestController string length check = ";

	@Autowired
	private TerminalSetUpService terminalSetupService;

	private static Logger logger = LoggerFactory.getLogger(OfflineTerminalSetupRestController.class);

	@PostMapping(value = "terminalSetupRequest",consumes = { "text/plain" })
	public String getTerminalSetup(@RequestBody String data,
			HttpServletResponse httpResponse, BindingResult bindingResult)
			throws ParseException, IOException, PosException {
		String successResponse;
		String info = "Entering:: OfflineTerminalSetUpRestController:: getTerminalSetup method";
		TerminalSetUpRequest request = new TerminalSetUpRequest();
		TerminalSetUpResponse response = new TerminalSetUpResponse();
		logger.debug(info);
		if (terminalSetupService.validateSetupDataLength(data)) {
			request.parseSetupData(data);
			successResponse = terminalSetupService.getTerminalSetupRequest(request, httpResponse);
			int responseLength =  response.toString().length();
			logger.debug(LENGTH_CHECK,responseLength);
			return successResponse;
		} else {
			response.setStatusCode(CustomErrorCodes.INVALID_PACKET_LENGTH.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_PACKET_LENGTH.getCustomErrorMsg());
			response.setDeviceId("");
			response.setPtoOperationId("");
			response.setReserverd("");
			response.setChecksum("");
			httpResponse.sendError(HttpErrorCodes.BAD_REQUEST.getHttpErrorCode(), response.toString());
			return response.toString();
		}
	}
}