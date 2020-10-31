package com.chatak.transit.afcs.server.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.constants.FinancialTxnIdValueConstant;
import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.FinancialTransactionDao;
import com.chatak.transit.afcs.server.dao.model.FinancialTransactionData;
import com.chatak.transit.afcs.server.dao.repository.FinancialTransactionRepository;
import com.chatak.transit.afcs.server.dao.repository.TicketTxnDataRepository;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.HttpErrorCodes;
import com.chatak.transit.afcs.server.pojo.FinancialTxnDataRequest;
import com.chatak.transit.afcs.server.pojo.FinancialTxnDataResponse;
import com.chatak.transit.afcs.server.service.FinancialTransactionService;
import com.chatak.transit.afcs.server.service.FinancialTxnPartService;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class FinancialTxnPartServiceImpl implements FinancialTxnPartService {

	private static final Logger  logger = LoggerFactory.getLogger(FinancialTxnPartServiceImpl.class);
	
	private static final String SHIFT_BATCH_NO = "ShiftBatchNumber= ";
	private static final String TOTAL_COUNT = "Total Count= ";
	private static final String TXN_TYPE = "TxnType= ";
	private static final String TICKET_AMOUNT = "Ticket Amount= ";

	@Autowired
	CustomErrorResolution customErrorResolution;

	@Autowired
	FinancialTransactionRepository financialTxnRepository;

	@Autowired
	TicketTxnDataRepository ticketTxnRepository;

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	FinancialTransactionDao financialDao;

	@Autowired
	FinancialTransactionService financialTxnService;

	@Override
	public String saveFinancialTxnData(FinancialTxnDataRequest request, HttpServletResponse httpResponse,
			FinancialTxnDataResponse response, String checkDefault) throws IOException {
		FinancialTransactionData financialTxnData = new FinancialTransactionData();
		if (financialDao.validateFinancialTxnRequest(request)) {
		String maxShiftBatchNo =	financialTxnRepository.getMaxShiftBatchNo(request.getDeviceSerialNo());
			if (!StringUtil.isNull(maxShiftBatchNo)
					&& (Integer.parseInt(request.getShiftBatchNo()) <= Integer.parseInt(maxShiftBatchNo))) {
				response.setStatusCode(CustomErrorCodes.SHIFT_BATCH_NO_ERROR.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SHIFT_BATCH_NO_ERROR.getCustomErrorMsg());
				response.setReservedResponse("");
				response.setChecksum("");
				return response.toString();
			}
			int numberOfFields = Integer.parseInt(request.getNoOfField());
			Double totalAmount=0.0;
			for (int i = 0; i < numberOfFields; i++) {

				int count = Integer.parseInt(request.getFields().get(i).getCount().trim());
				Double amount = Double.parseDouble(request.getFields().get(i).getAmount().substring(0, 4).trim());

				String txnType = request.getFields().get(i).gettxnType();
				int txnValue = 0;
				txnValue = checkTxnType(txnType, txnValue);

				logger.debug(SHIFT_BATCH_NO, Integer.parseInt(request.getShiftBatchNo()));
				logger.debug(TXN_TYPE, txnType);
				logger.debug(TICKET_AMOUNT, amount);
				logger.debug(TOTAL_COUNT, count);
				logger.debug(TICKET_AMOUNT, ticketTxnRepository.getTotalAmount(txnType, Integer.parseInt(request.getShiftBatchNo())));
					Double amount2=ticketTxnRepository.getTotalAmount(txnType,
						Integer.parseInt(request.getShiftBatchNo()));
					totalAmount+=amount;
					if ((Double.compare(totalAmount, amount2) == 0)
							&& count == ticketTxnRepository.countTicket(txnType,
									Integer.parseInt(request.getShiftBatchNo()))
				
						&& totalAmount != 0 && count != 0) {

						int value = txnValue;
						switch (value) {
						case FinancialTxnIdValueConstant.VALUE_00:
							financialTxnData.setCount0(count);
							financialTxnData.setAmount0(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_01:
							financialTxnData.setCount1(count);
							financialTxnData.setAmount1(amount);
							break;

						case FinancialTxnIdValueConstant.VALUE_02:
							financialTxnData.setCount2(count);
							financialTxnData.setAmount2(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_03:
							financialTxnData.setCount3(count);
							financialTxnData.setAmount3(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_04:
							financialTxnData.setCount4(count);
							financialTxnData.setAmount4(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_05:
							financialTxnData.setCount5(count);
							financialTxnData.setAmount5(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_06:
							financialTxnData.setCount6(count);
							financialTxnData.setAmount6(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_07:
							financialTxnData.setCount7(count);
							financialTxnData.setAmount7(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_08:
							financialTxnData.setCount8(count);
							financialTxnData.setAmount8(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_09:
							financialTxnData.setCount9(count);
							financialTxnData.setAmount9(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_10:
							financialTxnData.setCount10(count);
							financialTxnData.setAmount10(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_11:
							financialTxnData.setCount11(count);
							financialTxnData.setAmount11(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_12:
							financialTxnData.setCount12(count);
							financialTxnData.setAmount12(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_13:
							financialTxnData.setCount13(count);
							financialTxnData.setAmount13(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_14:
							financialTxnData.setCount14(count);
							financialTxnData.setAmount14(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_15:
							financialTxnData.setCount15(count);
							financialTxnData.setAmount15(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_16:
							financialTxnData.setCount16(count);
							financialTxnData.setAmount16(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_17:
							financialTxnData.setCount17(count);
							financialTxnData.setAmount17(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_18:
							financialTxnData.setCount18(count);
							financialTxnData.setAmount18(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_19:
							financialTxnData.setCount19(count);
							financialTxnData.setAmount19(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_20:
							financialTxnData.setCount20(count);
							financialTxnData.setAmount20(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_21:
							financialTxnData.setCount21(count);
							financialTxnData.setAmount21(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_22:
							financialTxnData.setCount22(count);
							financialTxnData.setAmount22(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_23:
							financialTxnData.setCount23(count);
							financialTxnData.setAmount23(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_24:
							financialTxnData.setCount24(count);
							financialTxnData.setAmount24(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_25:
							financialTxnData.setCount25(count);
							financialTxnData.setAmount25(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_26:
							financialTxnData.setCount26(count);
							financialTxnData.setAmount26(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_27:
							financialTxnData.setCount27(count);
							financialTxnData.setAmount27(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_28:
							financialTxnData.setCount28(count);
							financialTxnData.setAmount28(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_29:
							financialTxnData.setCount29(count);
							financialTxnData.setAmount29(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_30:
							financialTxnData.setCount30(count);
							financialTxnData.setAmount30(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_31:
							financialTxnData.setCount31(count);
							financialTxnData.setAmount31(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_32:
							financialTxnData.setCount32(count);
							financialTxnData.setAmount32(amount);
							break;

						case FinancialTxnIdValueConstant.VALUE_33:
							financialTxnData.setCount33(count);
							financialTxnData.setAmount33(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_34:
							financialTxnData.setCount34(count);
							financialTxnData.setAmount34(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_35:
							financialTxnData.setCount35(count);
							financialTxnData.setAmount35(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_36:
							financialTxnData.setCount36(count);
							financialTxnData.setAmount36(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_37:
							financialTxnData.setCount37(count);
							financialTxnData.setAmount37(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_38:
							financialTxnData.setCount38(count);
							financialTxnData.setAmount38(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_39:
							financialTxnData.setCount39(count);
							financialTxnData.setAmount39(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_40:
							financialTxnData.setCount40(count);
							financialTxnData.setAmount40(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_41:
							financialTxnData.setCount41(count);
							financialTxnData.setAmount41(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_42:
							financialTxnData.setCount42(count);
							financialTxnData.setAmount42(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_43:
							financialTxnData.setCount43(count);
							financialTxnData.setAmount43(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_44:
							financialTxnData.setCount44(count);
							financialTxnData.setAmount44(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_45:
							financialTxnData.setCount45(count);
							financialTxnData.setAmount45(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_46:
							financialTxnData.setCount46(count);
							financialTxnData.setAmount46(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_47:
							financialTxnData.setCount47(count);
							financialTxnData.setAmount47(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_48:
							financialTxnData.setCount48(count);
							financialTxnData.setAmount48(amount);
							break;
						case FinancialTxnIdValueConstant.VALUE_49:
							financialTxnData.setCount49(count);
							financialTxnData.setAmount49(amount);
							break;
						default:
							response.setStatusCode(CustomErrorCodes.INVALID_TRANSACTION_ID.getCustomErrorCode());
							response.setStatusMessage(CustomErrorCodes.INVALID_TRANSACTION_ID.getCustomErrorMsg());
							response.setReservedResponse("");
							response.setChecksum("");
							httpResponse.sendError(HttpErrorCodes.NOT_FOUND.getHttpErrorCode(), response.toString());
							return response.toString();
						}
					} else {
						financialTxnService.validationAmountOrCountForTxnId(txnType, response);
						response.setReservedResponse("");
						response.setChecksum("");
						httpResponse.sendError(HttpErrorCodes.NOT_FOUND.getHttpErrorCode(), response.toString());
						return response.toString();
					}
				}
				financialTxnData.setUserId(request.getUserId());
				financialTxnData.setPtoOperationId(request.getPtoOperationId());
				financialTxnData.setDeviceId(request.getDeviceId());
				financialTxnData.setFinancialTxnType(request.getFinancialTxnType());
				financialTxnData.setDateTime(request.getDateTime());
				financialTxnData.setShiftCode(request.getShiftCode());
				financialTxnData.setShiftBatchNo(Integer.parseInt(request.getShiftBatchNo()));
				financialTxnData.setTripNo(request.getTripNo());
				financialTxnData.setPaymentHostBatchNo(request.getPaymentHostBatchNo());
				financialTxnData.setPaymentHostTid(request.getPaymentHostTerminalId());
				financialTxnData.setSoftwareVersion(request.getSoftwareVersion());
				financialTxnData.setMasterVersion(request.getMasterVersion());
				financialTxnData.setDeviceSerialNo(request.getDeviceSerialNo());
				financialTxnData.setDeviceModelNo(request.getDeviceModelNo());
				financialTxnData.setDeviceComponentVersion(request.getDeviceComponentsVersion());
				financialTxnData.setCreatedDateTime(Timestamp.from(Instant.now()));
				financialTxnData.setUpdatedDateTime(Timestamp.from(Instant.now()));
				saveShiftEnd(request, httpResponse, response, financialTxnData);
			} else {
			financialTxnService.checkFinancialTxnErrors(request, response);
			response.setReservedResponse("");
			response.setChecksum("");
			httpResponse.sendError(HttpErrorCodes.NOT_FOUND.getHttpErrorCode(), response.toString());
		}
		return response.toString();
	}

	private int checkTxnType(String txnType, int txnValue) {
		if (txnType.equalsIgnoreCase("TT01")) {
			txnValue = 0;
		} else if (txnType.equalsIgnoreCase("TT02")) {
			txnValue = 1;
		} else if (txnType.equalsIgnoreCase("TL01")) {
			txnValue = 2;
		} else if (txnType.equalsIgnoreCase("TP01")) {
			txnValue = 3;
		} else if (txnType.equalsIgnoreCase("CP01")) {
			txnValue = 4;
		} else if (txnType.equalsIgnoreCase("TK01")) {
			txnValue = 5;
		}
		return txnValue;
	}

	private void saveShiftEnd(FinancialTxnDataRequest request, HttpServletResponse httpResponse,
			FinancialTxnDataResponse response, FinancialTransactionData financialTxnData) throws IOException {
		if (financialDao.saveFinancialTxnData(financialTxnData)) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			response.setReservedResponse(String.valueOf(Integer.parseInt(request.getShiftBatchNo()) + 1));
			response.setChecksum("");
		} else {
			response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
			response.setReservedResponse("");
			response.setChecksum("");
			httpResponse.sendError(HttpErrorCodes.INTERNAL_SERVER_ERROR.getHttpErrorCode(), response.toString());
		}
	}
}
