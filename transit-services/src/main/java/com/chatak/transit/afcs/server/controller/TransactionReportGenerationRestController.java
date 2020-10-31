package com.chatak.transit.afcs.server.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.pojo.web.TransactionReportGenerationRequest;
import com.chatak.transit.afcs.server.pojo.web.TransactionReportGenerationResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.TransactionReportGenerationService;

@RestController
@RequestMapping(value = "/online/")
public class TransactionReportGenerationRestController extends ServiceHelper {

	private static final Logger logger = LoggerFactory.getLogger(TransactionReportGenerationRestController.class);
	
	@Autowired
	private TransactionReportGenerationService transactionReportGenerationService;

	@PostMapping(value = "generateTransactionReport")
	public TransactionReportGenerationResponse generateTransactionReport(
			@Valid @RequestBody TransactionReportGenerationRequest request, BindingResult bindingResult,
			TransactionReportGenerationResponse response, HttpServletResponse httpResponse) throws IOException {
		String req = request.toString();
		logger.info(req);
		if (bindingResult.hasErrors()) {
			WebResponse webResponse = bindingResultErrorDetails(bindingResult);
			response.setStatusCode(webResponse.getStatusCode());
			response.setStatusMessage(webResponse.getStatusMessage());
			return response;
		}
		return transactionReportGenerationService.generateTransactionReport(request, httpResponse);
	}
	
}
