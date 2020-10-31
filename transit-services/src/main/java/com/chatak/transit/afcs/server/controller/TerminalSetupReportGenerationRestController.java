package com.chatak.transit.afcs.server.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.pojo.web.TerminalSetupReportGenerationResponse;
import com.chatak.transit.afcs.server.pojo.web.TerminalsetupReportGenerationRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.TerminalSetupReportGenerationService;

@RestController
@RequestMapping(value = "/online/")
public class TerminalSetupReportGenerationRestController extends ServiceHelper {

	Logger logger = LoggerFactory.getLogger(TerminalSetupReportGenerationRestController.class);

	@Autowired
	private TerminalSetupReportGenerationService terminalSetupReportGenerationService;

	@PostMapping(value = "generateTerminalSetupReport")
	public TerminalSetupReportGenerationResponse generateTerminalSetupReport(@RequestBody TerminalsetupReportGenerationRequest request, BindingResult bindingResult,
			HttpServletResponse httpResponse, TerminalSetupReportGenerationResponse response) throws IOException {

		if (!request.getEquimentOemId().isEmpty()) {
			request.setEquimentOemId(request.getEquimentOemId());
		} else {
			request.setEquimentOemId(null);
		}

		if (!request.getEquimentModelNumber().isEmpty()) {
			request.setEquimentModelNumber(request.getEquimentModelNumber());
		} else {
			request.setEquimentModelNumber(null);
		}

		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
			return response;
		}
		return terminalSetupReportGenerationService.generateTerminalSetupReport(request, response, httpResponse);
	}
}
