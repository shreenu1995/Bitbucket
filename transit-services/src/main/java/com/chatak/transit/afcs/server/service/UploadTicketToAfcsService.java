package com.chatak.transit.afcs.server.service;

import com.chatak.transit.afcs.server.pojo.TicketTransactionDataResponse;
import com.chatak.transit.afcs.server.pojo.TransitTicketTransactionDataRequest;

public interface UploadTicketToAfcsService {

	public TicketTransactionDataResponse saveUploadTicket(TransitTicketTransactionDataRequest request);

}
