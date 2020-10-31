package com.chatak.transit.afcs.server.dao;

import java.sql.Timestamp;
import java.util.List;

import com.chatak.transit.afcs.server.dao.model.TicketsTxnData;
import com.chatak.transit.afcs.server.pojo.web.TransactionReportGenerationRequest;
import com.chatak.transit.afcs.server.pojo.web.TransactionReportGenerationResponse;

public interface TransactionReportGenerationDao {

	List<TicketsTxnData> getReportTxnId(String txnId,int pageIndex);

	List<TicketsTxnData> getReportDateTime(Timestamp startDate, Timestamp endDate,int pageIndex);

	TransactionReportGenerationResponse getReportAll(String txnId, String startDate, String endDate,int pageIndex, String organizationId, Long ptoId);

	boolean validateRequest(TransactionReportGenerationRequest request);

	List<TicketsTxnData> getReportAllPart(String txnId, Timestamp startDate, Timestamp endDate,int pageIndex);
	
	List<TicketsTxnData> getReportByPtoIdAndTransactionId(Long ptoId, String transactioId);
	
	List<TicketsTxnData> getReportByPtoIdAndOrganizationId(Long ptoId, String organizationId);

}
