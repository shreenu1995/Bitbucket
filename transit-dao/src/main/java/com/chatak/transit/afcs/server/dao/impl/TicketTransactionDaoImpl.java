/**
 * @author Girmiti Software
 *
 */
package com.chatak.transit.afcs.server.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.transit.afcs.server.dao.TicketTransactionDao;
import com.chatak.transit.afcs.server.dao.model.QDeviceTypeMaster;
import com.chatak.transit.afcs.server.dao.model.QPtoMaster;
import com.chatak.transit.afcs.server.dao.model.QTicketsTxnData;
import com.chatak.transit.afcs.server.dao.model.TicketsTxnData;
import com.chatak.transit.afcs.server.dao.repository.TicketTxnDataRepository;
import com.chatak.transit.afcs.server.pojo.TicketTransactionDataRequest;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class TicketTransactionDaoImpl implements TicketTransactionDao {

	Logger logger = LoggerFactory.getLogger(TicketTransactionDao.class);

	@Autowired
	TicketTxnDataRepository ticketTxnDataRepository;

	@PersistenceContext
	private EntityManager em;

	@Override
	public TicketsTxnData saveTicketTxnDataRequest(TicketsTxnData ticketTxnData) {
		String info = "saveTicketTxnDataRequest called from Dao Model";
		logger.info(info);
		TicketsTxnData txn = ticketTxnDataRepository.save(ticketTxnData);
			txn.setTxnSts("Success");
			ticketTxnDataRepository.save(txn);
			return txn;
	}
	

	@Override
	public boolean isTicketRequestValid(TicketTransactionDataRequest request) {
		JPAQuery query = new JPAQuery(em);
		return query
				.from(QDeviceTypeMaster.deviceTypeMaster, QPtoMaster.ptoMaster)
				.where(QPtoMaster.ptoMaster.ptoMasterId.eq(Long.valueOf(request.getPtoOperationId()))).exists();
	}

	@Override
	public boolean isValidTicketNumber(TicketTransactionDataRequest request) {
		JPAQuery query = new JPAQuery(em);
		return !query.from(QTicketsTxnData.ticketsTxnData)
				.where(QTicketsTxnData.ticketsTxnData.ticketNumber.eq(request.getTicketNumber())).exists();
	}

}