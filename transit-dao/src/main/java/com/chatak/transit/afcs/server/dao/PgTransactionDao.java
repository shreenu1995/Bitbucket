package com.chatak.transit.afcs.server.dao;

import com.chatak.transit.afcs.server.dao.model.PgTransactionData;

public interface PgTransactionDao {

	public PgTransactionData savePgTxnDataRequest(PgTransactionData pgTxnData);

	public PgTransactionData findByTxnTicketId(Long pgTxnData);

}
