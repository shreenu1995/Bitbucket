package com.chatak.transit.afcs.server.dao;

import com.chatak.transit.afcs.server.dao.model.TicketsTxnData;
import com.chatak.transit.afcs.server.dao.model.TripDetailsData;

public interface UploadTicketDao {

	public TicketsTxnData saveUploadTicket(TicketsTxnData ticketsTxnData);

	public TripDetailsData saveTripDetails(TripDetailsData tripDetailsData);

}
