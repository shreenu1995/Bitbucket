package com.chatak.transit.afcs.server.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.chatak.transit.afcs.server.constants.HTTErrorConstant;
import com.chatak.transit.afcs.server.constants.ServerConstants;
import com.chatak.transit.afcs.server.pojo.FinancialTxnDataRequest;
import com.chatak.transit.afcs.server.pojo.HTTCustomError;
import com.chatak.transit.afcs.server.pojo.TicketTransactionDataRequest;

@PropertySource("classpath:error.properties")
public class HttValidation {

	@Autowired
	Environment env;

	public String getTransactionError(TicketTransactionDataRequest request) {
		if (request.getTicketNumber().isEmpty()) {
			return HTTCustomError.getErrorEmpty(request.getTicketNumber(),
					env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_TICKET_NO_REQUIRED),
					env.getProperty(env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_TICKET_NO_REQUIRED)));
		} else if (request.getTicketDateTime().isEmpty()) {
			return HTTCustomError.getErrorEmpty(request.getTicketDateTime(),
					env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_TICKET_DATE_TIME_REQUIRED),
					env.getProperty(
							env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_TICKET_DATE_TIME_REQUIRED)));
		} else if (request.getTicketOperationDate().isEmpty()) {
			return HTTCustomError.getErrorEmpty(request.getTicketOperationDate(),
					env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_TICKET_OPERATION_DATE_REQUIRED),
					env.getProperty(
							env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_TICKET_OPERATION_DATE_REQUIRED)));
		} else if (request.getTicketFare().isEmpty()) {
			return HTTCustomError.getErrorEmpty(request.getTicketFare(),
					env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_TICKET_FARE_REQUIRED),
					env.getProperty(env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_TICKET_FARE_REQUIRED)));
		} else if (request.getTicketPaymentMode().isEmpty()) {
			return HTTCustomError.getErrorEmpty(request.getTicketPaymentMode(),
					env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_TICKET_PAYMENT_MODE_REQUIRED),
					env.getProperty(
							env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_TICKET_PAYMENT_MODE_REQUIRED)));
		} else if (request.getTransactionId().isEmpty()) {
			return HTTCustomError.getErrorEmpty(request.getTransactionId(),
					env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_TXN_ID_REQUIRED),
					env.getProperty(env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_TXN_ID_REQUIRED)));
		} else if (request.getPtoOperationId().isEmpty()) {
			return HTTCustomError.getErrorEmpty(request.getPtoOperationId(),
					env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_PTO_OPERATION_ID_REQUIRED),
					env.getProperty(
							env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_PTO_OPERATION_ID_REQUIRED)));
		} else if (request.getDeviceId().isEmpty()) {
			return HTTCustomError.getErrorEmpty(request.getDeviceId(),
					env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_DEVICE_ID_REQUIRED),
					env.getProperty(env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_DEVICE_ID_REQUIRED)));
		} else if (request.getShiftBatchNumber().isEmpty()) {
			return HTTCustomError.getErrorEmpty(request.getShiftBatchNumber(),
					env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_SHIFT_BATCH_NO_REQUIRED),
					env.getProperty(env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_SHIFT_BATCH_NO_REQUIRED)));
		} else if (!HTTCustomError
				.getError(request.getShiftBatchNumber(),
						env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_SHIFT_BATCH_NO_INVALID),
						env.getProperty(
								env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_SHIFT_BATCH_NO_INVALID)))
				.equals(ServerConstants.STATUS)) {
			return HTTCustomError.getError(request.getTicketNumber(),
					env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_SHIFT_BATCH_NO_INVALID),
					env.getProperty(env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_SHIFT_BATCH_NO_INVALID)));
		} else if (request.getShiftCode().isEmpty()) {
			return HTTCustomError.getErrorEmpty(request.getShiftCode(),
					env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_SHIFT_CODE_INVALID),
					env.getProperty(env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_SHIFT_CODE_INVALID)));
		} else {
			return ServerConstants.STATUS;
		}
	}

	public String getShiftEndError(FinancialTxnDataRequest request) {
		if (request.getShiftBatchNo().isEmpty()) {
			return HTTCustomError.getErrorEmpty(request.getShiftBatchNo(),
					env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_SHIFT_BATCH_NO_REQUIRED),
					env.getProperty(env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_SHIFT_BATCH_NO_REQUIRED)));
		} else if (!HTTCustomError
				.getError(request.getShiftBatchNo(),
						env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_SHIFT_BATCH_NO_INVALID),
						env.getProperty(
								env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_SHIFT_BATCH_NO_INVALID)))
				.equals(ServerConstants.STATUS)) {
			return HTTCustomError.getError(request.getShiftBatchNo(),
					env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_SHIFT_BATCH_NO_INVALID),
					env.getProperty(env.getProperty(HTTErrorConstant.CHATAK_AFCS_SERVICE_HTT_SHIFT_BATCH_NO_INVALID)));
		} else {
			return ServerConstants.STATUS;
		}
	}

	public static boolean isTimeStampValid(String timeStamp) throws ParseException {
		SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.parse(timeStamp);
		Pattern p = Pattern.compile("^\\d{4}[-]?\\d{1,2}[-]?\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}");
		return p.matcher(timeStamp).matches();
	}

}
