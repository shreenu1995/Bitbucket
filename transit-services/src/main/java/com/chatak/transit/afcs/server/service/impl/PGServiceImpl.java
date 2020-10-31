package com.chatak.transit.afcs.server.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.dao.PgTransactionDao;
import com.chatak.transit.afcs.server.dao.model.PgTransactionData;
import com.chatak.transit.afcs.server.dao.model.TicketsTxnData;
import com.chatak.transit.afcs.server.pojo.CardData;
import com.chatak.transit.afcs.server.pojo.PGServiceResponse;
import com.chatak.transit.afcs.server.pojo.PGTransactionRequest;
import com.chatak.transit.afcs.server.pojo.TicketTransactionDataRequest;
import com.chatak.transit.afcs.server.service.PGService;
import com.chatak.transit.afcs.util.JsonTransitUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@PropertySource("classpath:error.properties")
public class PGServiceImpl implements PGService {

	@Autowired
	PgTransactionDao pgtransactionDao;

	private static Logger logger = LoggerFactory.getLogger(PGServiceImpl.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public PGServiceResponse invokePayment(TicketsTxnData ticketsTxnData, TicketTransactionDataRequest request)
			throws IOException {
		logger.debug("invokePayment :: PGServiceImpl");
		
		// Create the request object based on PG specs
		PGTransactionRequest txnrequest = new PGTransactionRequest();

		CardData cdata = new CardData();
		cdata.setCardHolderName(request.getCardHolderName());
		cdata.setCardNumber(request.getCardNumber());
		cdata.setCvv(request.getcVV());
		cdata.setExpDate(request.getCardExpiryDate());
		cdata.setCardType(request.getCardType());
		txnrequest.setCardData(cdata);
		txnrequest.setDeviceSerial(request.getDeviceSerial());
		txnrequest.setMerchantCode(request.getMerchantCode());
		txnrequest.setTimeZoneRegion(request.getTimeZoneRegion());
		txnrequest.setTimeZoneOffset(request.getTimeZoneOffset());
		txnrequest.setTicketNumber(request.getTicketNumber());
		txnrequest.setOrderId(request.getOrderId());
		txnrequest.setInvoiceNumber(request.getInvoiceNumber());
		txnrequest.setMerchantAmount(request.getMerchantAmount());
		txnrequest.setTotalTxnAmount(request.getTotalTxnAmount());
		txnrequest.setTerminalId(request.getTerminalId());
		txnrequest.setOriginChannel(request.getOriginChannel());
		txnrequest.setTransactionType(request.getTransactionType());
		txnrequest.setEntryMode(request.getEntryMode());

		PgTransactionData pgTransactionData = new PgTransactionData();

		pgTransactionData.setTxnStatus(Constants.PENDING);
		pgTransactionData.setMerchantCode(txnrequest.getMerchantCode());
		pgTransactionData.setTransactionType(txnrequest.getTransactionType());
		pgTransactionData.setTicketTxnId(ticketsTxnData.getId());
		// save in pgtransaction
		pgTransactionData = pgtransactionDao.savePgTxnDataRequest(pgTransactionData);
		String output = JsonTransitUtil.postRequest(txnrequest, String.class, Constants.FETCH_PD_PROCESS);
		PGServiceResponse serviceResponse = objectMapper.readValue(output, PGServiceResponse.class);
		if (null != serviceResponse && serviceResponse.getErrorMessage().equals("Approved")) {
			pgTransactionData = pgtransactionDao.findByTxnTicketId(pgTransactionData.getTicketTxnId());
			pgTransactionData.setTxnStatus(Constants.APPROVED);
			pgTransactionData.setCgRefNumber(serviceResponse.getCgRefNumber());
			pgTransactionData.setDeviceLocalTxnTime(serviceResponse.getDeviceLocalTxnTime());
			pgTransactionData.setTxnRefNumber(serviceResponse.getTxnRefNumber());
			pgTransactionData.setMerchantName(serviceResponse.getMerchantName());
			pgTransactionData.setAuthId(serviceResponse.getAuthId());
			pgtransactionDao.savePgTxnDataRequest(pgTransactionData);

		} else {
			pgTransactionData = pgtransactionDao.findByTxnTicketId(pgTransactionData.getTicketTxnId());
			pgTransactionData.setTxnStatus(Constants.FAILURE);
			pgtransactionDao.savePgTxnDataRequest(pgTransactionData);
		}
		// Marshall to object
		return objectMapper.readValue(output, PGServiceResponse.class);

	}

}
