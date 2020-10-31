package com.chatak.transit.afcs.server.service;

import java.io.IOException;

import com.chatak.transit.afcs.server.dao.model.TicketsTxnData;
import com.chatak.transit.afcs.server.pojo.PGServiceResponse;
import com.chatak.transit.afcs.server.pojo.TicketTransactionDataRequest;

public interface PGService {

	PGServiceResponse invokePayment(TicketsTxnData ticketsTxnData, TicketTransactionDataRequest request)
			throws IOException;
}
