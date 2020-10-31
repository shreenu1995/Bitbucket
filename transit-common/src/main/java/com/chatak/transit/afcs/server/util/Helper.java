package com.chatak.transit.afcs.server.util;

import java.sql.Timestamp;
import java.util.List;

import com.chatak.transit.afcs.server.constants.FileDownloadConstant;
import com.chatak.transit.afcs.server.constants.FinancialTxnConstants;
import com.chatak.transit.afcs.server.pojo.FileDownloadRequest;
import com.chatak.transit.afcs.server.pojo.FinancialDataObject;
import com.chatak.transit.afcs.server.pojo.FinancialTxnDataRequest;

public class Helper {
	public static void parseFinancialDataObjects(String buffer, List<FinancialDataObject> list, String noOfField) {
		int numberOfFields = Integer.parseInt(noOfField);
		for (int i = 0; i < numberOfFields; i++) {
			int startingIndex = i * FinancialTxnConstants.DATA_OBJECT_LENGTH;

			FinancialDataObject financialObject = new FinancialDataObject(
					buffer.substring(startingIndex + FinancialTxnConstants.TXNTYPE_INITIAL_INDEX, (i * 14) + 4),
					buffer.substring((i * 14) + 4, (i * 14) + 8), buffer.substring((i * 14) + 8, (i * 14) + 14));

			list.add(financialObject);
		}

	}

	public static void parseFileDownloadRequest(String data, FileDownloadRequest request) {

		request.setPtoOperationId(data.substring(FileDownloadConstant.FILE_DOWNLOAD_PTO_ID_INITIAL_INDEX,
				FileDownloadConstant.FILE_DOWNLOAD_PTO_ID_FINAL_INDEX).trim());

		request.setDeviceId(data.substring(FileDownloadConstant.FILE_DOWNLOAD_DEVICE_ID_INITIAL_INDEX,
				FileDownloadConstant.FILE_DOWNLOAD_DEVICE_ID_FINAL_INDEX).trim());

		request.setVersion(data.substring(FileDownloadConstant.FILE_DOWNLOAD_VERSION_INITIAL_INDEX,
				FileDownloadConstant.FILE_DOWNLOAD_VERSION_FINAL_INDEX));

		request.setTransactionId(data.substring(FileDownloadConstant.FILE_DOWNLOAD_TRANSACTION_ID_INITIAL_INDEX,
				FileDownloadConstant.FILE_DOWNLOAD_TRANSACTION_ID_FINAL_INDEX).trim());

		request.setDateTime(Timestamp.valueOf(
				data.substring(FileDownloadConstant.DATE_TIME_INITIAL_INDEX, FileDownloadConstant.DATE_TIME_FINAL_INDEX)
						.trim()));

	}

	public static void parseFinancialTxnRequest(String data, FinancialTxnDataRequest request) {

		request.setUserId(
				data.substring(FinancialTxnConstants.USERID_INITIAL_INDEX, FinancialTxnConstants.USERID_FINAL_INDEX)
						.trim());

		request.setEmployeeId(data.substring(FinancialTxnConstants.EMPLOYEE_ID_INITIAL_INDEX,
				FinancialTxnConstants.EMPLOYEE_ID_FINAL_INDEX).trim());

		request.setPtoOperationId(data.substring(FinancialTxnConstants.PTO_OPERATION_ID_INITIAL_INDEX,
				FinancialTxnConstants.PTO_OPERATION_ID_FINAL_INDEX).trim());

		request.setDeviceId(data.substring(FinancialTxnConstants.DEVICE_ID_INITIAL_INDEX,
				FinancialTxnConstants.DEVICE_ID_FINAL_INDEX).trim());

		request.setFinancialTxnType(data.substring(FinancialTxnConstants.FINANCIAL_TXN_TYPE_INITIAL_INDEX,
				FinancialTxnConstants.FINANCIAL_TXN_TYPE_FINAL_INDEX).trim());

		request.setDateTime(data
				.substring(FinancialTxnConstants.DATE_TIME_INITIAL_INDEX, FinancialTxnConstants.DATE_TIME_FINAL_INDEX)
				.trim());

		request.setReservedSession(data.substring(FinancialTxnConstants.RESERVED_SESSION_INITIAL_INDEX,
				FinancialTxnConstants.RESERVED_SESSION_FINAL_INDEX).trim());

		request.setNoOfField(data.substring(FinancialTxnConstants.NO_OF_FIELDS_INITIAL_INDEX,
				FinancialTxnConstants.NO_OF_FIELDS_FINAL_INDEX).trim());

		StringBuilder fieldBuffer = new StringBuilder(
				data.substring(FinancialTxnConstants.FIELDS_INITIAL_INDEX, FinancialTxnConstants.FIELDS_FINAL_INDEX)
						.trim());

		parseFinancialDataObjects(fieldBuffer.toString(), request.getFields(), request.getNoOfField());

		request.setShiftCode(data
				.substring(FinancialTxnConstants.SHIFT_CODE_INITIAL_INDEX, FinancialTxnConstants.SHIFT_CODE_FINAL_INDEX)
				.trim());

		request.setShiftBatchNo(data.substring(FinancialTxnConstants.SHIFT_BATCH_NO_INITIAL_INDEX,
				FinancialTxnConstants.SHIFT_BATCH_NO_FINAL_INDEX).trim());

		request.setTripNo(
				data.substring(FinancialTxnConstants.TRIP_NO_INITIAL_INDEX, FinancialTxnConstants.TRIP_NO_FINAL_INDEX)
						.trim());

		request.setPaymentHostBatchNo(data.substring(FinancialTxnConstants.PAYMENT_HOST_BATCH_NO_INITIAL_INDEX,
				FinancialTxnConstants.PAYMENT_HOST_BATCH_NO_FINAL_INDEX).trim());

		request.setPaymentHostTerminalId(data.substring(FinancialTxnConstants.PAYMENT_HOST_TERMINAL_ID_INITIAL_INDEX,
				FinancialTxnConstants.PAYMENT_HOST_TERMINAL_ID_FINAL_INDEX).trim());

		request.setReservedFinance(data.substring(FinancialTxnConstants.RESERVED_FINANCE_INITIAL_INDEX,
				FinancialTxnConstants.RESERVED_FINANCE_FINAL_INDEX).trim());

		request.setSoftwareVersion(data.substring(FinancialTxnConstants.SOFTWARE_VERSION_INITIAL_INDEX,
				FinancialTxnConstants.SOFTWARE_VERSION_FINAL_INDEX).trim());

		request.setMasterVersion(data.substring(FinancialTxnConstants.MASTER_VERSION_INITIAL_INDEX,
				FinancialTxnConstants.MASTER_VERSION_FINAL_INDEX).trim());

		request.setDeviceSerialNo(data.substring(FinancialTxnConstants.DEVICE_SERIAL_NO_INITIAL_INDEX,
				FinancialTxnConstants.DEVICE_SERIAL_NO_FINAL_INDEX).trim());

		request.setDeviceModelNo(data.substring(FinancialTxnConstants.DEVICE_MODEL_NO_INITIAL_INDEX,
				FinancialTxnConstants.DEVICE_MODEL_NO_FINAL_INDEX).trim());

		request.setDeviceComponentsVersion(
				data.substring(FinancialTxnConstants.DEVICE_COMPONENT_VERSION_INITIAL_INDEX,
						FinancialTxnConstants.DEVICE_COMPONENT_VERSION_FINAL_INDEX).trim());

		request.setReservedVersion(data.substring(FinancialTxnConstants.RESERVED_VERSION_INITIAL_INDEX,
				FinancialTxnConstants.RESERVED_VERSION_FINAL_INDEX).trim());

		request.setChecksum(
				data.substring(FinancialTxnConstants.CHECKSUM_INITIAL_INDEX, FinancialTxnConstants.CHECKSUM_FINAL_INDEX)
						.trim());

	}
	
	private Helper() {
		super();
	}

}
