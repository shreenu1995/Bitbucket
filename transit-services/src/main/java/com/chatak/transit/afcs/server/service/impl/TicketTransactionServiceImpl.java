/**
 * @author Girmiti Software
 *
 */
package com.chatak.transit.afcs.server.service.impl;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.constants.ServerConstants;
import com.chatak.transit.afcs.server.constants.TicketConstants;
import com.chatak.transit.afcs.server.controller.HttValidation;
import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.DeviceTrackingDao;
import com.chatak.transit.afcs.server.dao.TicketTransactionDao;
import com.chatak.transit.afcs.server.dao.model.DeviceTracking;
import com.chatak.transit.afcs.server.dao.model.TicketsTxnData;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.HttpErrorCodes;
import com.chatak.transit.afcs.server.pojo.TicketTransactionDataRequest;
import com.chatak.transit.afcs.server.pojo.TicketTransactionDataResponse;
import com.chatak.transit.afcs.server.service.PGService;
import com.chatak.transit.afcs.server.service.TicketTransactionService;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
@PropertySource("classpath:error.properties")
public class TicketTransactionServiceImpl extends HttValidation implements TicketTransactionService {

	Logger logger = LoggerFactory.getLogger(TicketTransactionServiceImpl.class);
	private static final String SHIFT_BATCH_NO = "shiftbatch no= ";
	private static final String TICKET_DATA_LENGTH = "Ticket Data Length is: ";

	@Autowired
	TicketTransactionDao ticketTxnDao;

	@Autowired
	CustomErrorResolution customErrorResolution;

	@Autowired
	PtoMasterRepository ptoMasterRepository;

	@Autowired
	PGService pgService;

	@Autowired
	DeviceTrackingDao deviceTrackingDao;

	@Override
	public String saveTicketDataRequest(TicketTransactionDataRequest request, TicketTransactionDataResponse response,
			HttpServletResponse httpResponse) throws ParseException, IOException {
		TicketsTxnData ticketTxnData = new TicketsTxnData();
		if (!getTransactionError(request).equals(ServerConstants.STATUS)) {
			response.setReservedResponse("");
			response.setChecksum("");
			return getTransactionError(request) + response.getReservedResponse() + response.getChecksum();
		}

		boolean isValidTimeStamp = isTimeStampValid(request.getTicketDateTime());
		if (isValidTimeStamp
				&& ticketTxnDao.isValidTicketNumber(request) && ticketTxnDao.isTicketRequestValid(request)) {

			logger.info("Ticket Data Length Validation Successfull!!!");
			ticketTxnData.setTicketNumber(request.getTicketNumber());
			ticketTxnData.setTransactionId(request.getTransactionId());
			ticketTxnData.setTicketDateTime(Timestamp.valueOf(request.getTicketDateTime()));
			ticketTxnData.setTicketFareOptionalPositiveAmount(request.getTicketFareOptionalPositiveAmount());
			ticketTxnData.setTicketFareOptionalNegativeAmount(request.getTicketFareOptionalNegativeAmount());
			if (!StringUtil.isNullEmpty(request.getTicketFare())) {
				Double ticketFare = (Double.parseDouble(request.getTicketFare()));
				ticketTxnData.setTicketFareAmount(ticketFare);
			}
			ticketTxnData.setTicketOperationDate(Date.valueOf(request.getTicketOperationDate()));
			ticketTxnData.setTicketPaymentMode(request.getTicketPaymentMode());
			ticketTxnData.setTicketOriginStationCode(request.getTicketOriginStop());
			ticketTxnData.setTicketDestStationCode(request.getTicketDestinationStop());
			ticketTxnData.setTicketPassengerCount(request.getTicketPassengerCount());
			ticketTxnData.setPtoId(Long.valueOf(request.getPtoOperationId()));
			ticketTxnData.setDeviceId(Long.valueOf(request.getDeviceId()));
			ticketTxnData.setConductorEmpId(request.getConductorEmployeeId());
			ticketTxnData.setDriverEmpId(request.getDriverEmployeeId());
			ticketTxnData.setShiftCode(request.getShiftCode());
			logger.info(SHIFT_BATCH_NO, request.getShiftBatchNumber());
			ticketTxnData.setShiftBatchNumber(Integer.parseInt(request.getShiftBatchNumber()));
			ticketTxnData.setTripNumber(request.getTripNumber());
			ticketTxnData.setRouteCode(request.getRouteCode());
			ticketTxnData.setTransportID(request.getTransportId());
			ticketTxnData.setCurrentStopId(request.getCurrentStopId());
			ticketTxnData.setCardNumber(request.getCardNumber());
			ticketTxnData.setCardBalance(request.getCardBalance());
			ticketTxnData.setCardExpiryDate(request.getCardExpiryDate());
			ticketTxnData.setPaymentTxnUniqueId(request.getPaymentTransactionUniqueId());
			ticketTxnData.setPaymentTxnTerminalId(request.getPaymentTransactionTerminalUid());
			ticketTxnData.setPassTypeCode(request.getPassType());
			ticketTxnData.setOperatorId(request.getOperatorId());

			// FIXME
			// TicketsTxnData ticketsTxnData =
			// ticketTxnDao.saveTicketTxnDataRequest(ticketTxnData);
			/*
			 * if (ticketsTxnData != null) {
			 * logger.info("Ticket Data Saved SuccessFully!!! ");
			 * 
			 * response.setReservedResponse(""); response.setChecksum("");
			 * 
			 * // Invoke PG API for payment PGServiceResponse pgResponse =
			 * pgService.invokePayment(ticketsTxnData, request);
			 * response.setStatusMessage(pgResponse.getErrorMessage());
			 * response.setStatusCode(pgResponse.getErrorCode());
			 * response.setErrorCode(pgResponse.getErrorCode());
			 * 
			 * }
			 */

			TicketsTxnData ticketsTxnData = ticketTxnDao.saveTicketTxnDataRequest(ticketTxnData);
			if (ticketsTxnData != null) {
				logger.info("Ticket Data Saved SuccessFully!!! ");
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				response.setReservedResponse("");
				response.setChecksum("");
			} else {
				response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
				response.setReservedResponse("");
				response.setChecksum("");
				httpResponse.sendError(HttpErrorCodes.INTERNAL_SERVER_ERROR.getHttpErrorCode(), response.toString());
				logger.info("Failed to save Ticket Data");
			}

			DeviceTracking deviceTracking = new DeviceTracking();
			deviceTracking.setDeviceSerial(ticketTxnData.getDeviceId());
			deviceTracking.setPtoId(Long.valueOf(request.getPtoOperationId()));
			deviceTracking.setRouteId(ticketTxnData.getRouteCode());
			deviceTracking.setStatus("On Track");
			// FIXME
			deviceTracking.setLatitude("12.9560848");
			deviceTracking.setLongitude("77.7165814");
			deviceTrackingDao.saveDeviceTrackingdetails(deviceTracking);

		} else {
			errorResponseService(request, response, isValidTimeStamp);
			response.setReservedResponse("");
			response.setChecksum("");
			httpResponse.sendError(HttpErrorCodes.NOT_FOUND.getHttpErrorCode(), response.toString());
			logger.info("Data Validation Failed.");
		}

		return response.toString();

	}

	@Override
	public boolean validateTicketDataLength(String data) {
		logger.info("Inside validateDataLength() function ");
		logger.info(TICKET_DATA_LENGTH, (data.length() == TicketConstants.TICKET_TXN_DATA_LENGTH));
		return (data.length() == TicketConstants.TICKET_TXN_DATA_LENGTH);
	}

	@Override
	public void errorResponseService(TicketTransactionDataRequest request, TicketTransactionDataResponse response, boolean isValidTimeStamp) {
		if (customErrorResolution.ticketNoValidation(request.getTicketNumber())) {
			response.setStatusCode(CustomErrorCodes.INVALID_TICKET.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_TICKET.getCustomErrorMsg());
		} else if (!(customErrorResolution.ptoIdValidation(Long.valueOf(request.getPtoOperationId())))){
			response.setStatusCode(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorMsg());
		} else if (!isValidTimeStamp) {
			response.setStatusCode(CustomErrorCodes.INVALID_TIMESTAMP_FORMAT.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_TIMESTAMP_FORMAT.getCustomErrorMsg());
		} else {
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		}
	}

}
