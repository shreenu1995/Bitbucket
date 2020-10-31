/**
 * @author Girmiti Software
 *
 */
package com.chatak.transit.afcs.server.dao;

import com.chatak.transit.afcs.server.dao.model.TicketsTxnData;
import com.chatak.transit.afcs.server.pojo.TicketTransactionDataRequest;

public interface TicketTransactionDao {
	
	public TicketsTxnData saveTicketTxnDataRequest(TicketsTxnData ticketTxnData);
	
	public boolean isTicketRequestValid(TicketTransactionDataRequest request);
	
	public boolean isValidTicketNumber(TicketTransactionDataRequest request);

}
