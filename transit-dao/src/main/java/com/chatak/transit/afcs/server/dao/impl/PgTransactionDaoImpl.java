package com.chatak.transit.afcs.server.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.transit.afcs.server.dao.PgTransactionDao;
import com.chatak.transit.afcs.server.dao.model.PgTransactionData;
import com.chatak.transit.afcs.server.dao.repository.PgTransactionRepository;

@Repository
@Transactional
public class PgTransactionDaoImpl implements PgTransactionDao {

	@Autowired
	PgTransactionRepository pgTransactionRepository;

	@PersistenceContext
	EntityManager em;

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public PgTransactionData savePgTxnDataRequest(PgTransactionData pgTxnData) {
		return pgTransactionRepository.save(pgTxnData);
	}

	@Override
	public PgTransactionData findByTxnTicketId(Long pgTxnData) {
		return pgTransactionRepository.findByTicketTxnId(pgTxnData);
	}

}
