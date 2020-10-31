package com.chatak.transit.afcs.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.transit.afcs.server.dao.UploadTicketDao;
import com.chatak.transit.afcs.server.dao.model.TicketsTxnData;
import com.chatak.transit.afcs.server.dao.model.TripDetailsData;
import com.chatak.transit.afcs.server.dao.repository.TripDetailsRepository;
import com.chatak.transit.afcs.server.dao.repository.UploadTicketRepository;

@Repository
@Transactional
public class UploadTicketDaoImpl implements UploadTicketDao {

	@Autowired
	UploadTicketRepository uploadTicketRepository;

	@Autowired

	TripDetailsRepository tripDetailsRepository;

	@Override
	public TicketsTxnData saveUploadTicket(TicketsTxnData ticketsTxnData) {

		return uploadTicketRepository.save(ticketsTxnData);
	}

	@Override
	public TripDetailsData saveTripDetails(TripDetailsData tripDetailsData) {

		return tripDetailsRepository.save(tripDetailsData);
	}

}
