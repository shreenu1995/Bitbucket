package com.chatak.transit.afcs.server.service;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import com.chatak.transit.afcs.server.pojo.FinancialTxnDataRequest;
import com.chatak.transit.afcs.server.pojo.FinancialTxnDataResponse;

public interface FinancialTransactionService {

	public String saveFinancialTxn(FinancialTxnDataRequest request, HttpServletResponse httpResponse,
			FinancialTxnDataResponse financeResponse) throws IOException;

	boolean validateFinancialTxnDataLength(String request);

	void checkFinancialTxnErrors(FinancialTxnDataRequest request, FinancialTxnDataResponse response);

	void validationAmountOrCountForTxnId(String transactionId, FinancialTxnDataResponse response);

}
