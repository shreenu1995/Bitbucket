package com.afcs.web.service;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.TransactionReportGenerationRequest;
import com.chatak.transit.afcs.server.pojo.web.TransactionReportGenerationResponse;

public interface TransactionReportService {

	TransactionReportGenerationResponse generateTransactionReport(TransactionReportGenerationRequest request) throws AFCSException;

}
