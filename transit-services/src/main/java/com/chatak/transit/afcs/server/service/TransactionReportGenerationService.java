package com.chatak.transit.afcs.server.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.chatak.transit.afcs.server.pojo.web.TransactionReportGenerationRequest;
import com.chatak.transit.afcs.server.pojo.web.TransactionReportGenerationResponse;

public interface TransactionReportGenerationService {

	void checkTxnReportGeneationErrors(TransactionReportGenerationRequest request,
			TransactionReportGenerationResponse response);

	TransactionReportGenerationResponse generateTransactionReport(TransactionReportGenerationRequest request,
			HttpServletResponse httpResponse) throws IOException;
	
}