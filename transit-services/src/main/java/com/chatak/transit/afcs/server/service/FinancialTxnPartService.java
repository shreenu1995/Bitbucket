package com.chatak.transit.afcs.server.service;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.chatak.transit.afcs.server.pojo.FinancialTxnDataRequest;
import com.chatak.transit.afcs.server.pojo.FinancialTxnDataResponse;

public interface FinancialTxnPartService {

	String saveFinancialTxnData(FinancialTxnDataRequest request, HttpServletResponse httpResponse,
			FinancialTxnDataResponse response, String checkDefault) throws IOException;

}
