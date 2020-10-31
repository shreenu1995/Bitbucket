package com.chatak.transit.afcs.server.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatak.transit.afcs.server.dao.model.PgTransactionData;

public interface PgTransactionRepository extends JpaRepository<PgTransactionData, Long> {

	public PgTransactionData findByTicketTxnId(Long ticketTxnId);

}
