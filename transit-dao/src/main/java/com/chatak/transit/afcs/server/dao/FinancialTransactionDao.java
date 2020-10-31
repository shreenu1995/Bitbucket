package com.chatak.transit.afcs.server.dao;

import com.chatak.transit.afcs.server.dao.model.FinancialTransactionData;
import com.chatak.transit.afcs.server.pojo.FinancialTxnDataRequest;

public interface FinancialTransactionDao {

	boolean saveFinancialTxnData(FinancialTransactionData financialTxnData);

	boolean validateFinancialTxnRequest(FinancialTxnDataRequest request);

}
