/**
 * @author Girmiti Software
 *
 */
package com.chatak.transit.afcs.server.service;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletResponse;

import com.chatak.transit.afcs.server.pojo.TicketTransactionDataRequest;
import com.chatak.transit.afcs.server.pojo.TicketTransactionDataResponse;

public interface TicketTransactionService {

	String saveTicketDataRequest(TicketTransactionDataRequest request, TicketTransactionDataResponse response,
			HttpServletResponse httpResponse)throws ParseException,IOException;

	boolean validateTicketDataLength(String data);

	void errorResponseService(TicketTransactionDataRequest request, TicketTransactionDataResponse response,
			boolean isValidTimeStamp);

}