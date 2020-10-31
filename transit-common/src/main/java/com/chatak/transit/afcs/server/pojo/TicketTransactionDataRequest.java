/**
 * @author Girmiti Software
 *
 */
package com.chatak.transit.afcs.server.pojo;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chatak.transit.afcs.server.constants.TicketConstants;

public class TicketTransactionDataRequest implements Serializable {

	private static final long serialVersionUID = -1881410074111306427L;

	private static Logger logger = LoggerFactory.getLogger(TicketTransactionDataRequest.class);

	// Ticket Information
	private String ticketNumber;
	private String transactionId;
	private String ticketDateTime;
	private String ticketFare;
	private String ticketFareOptionalPositiveAmount;
	private String ticketFareOptionalNegativeAmount;
	private String ticketOperationDate;
	private String ticketPaymentMode;
	private String ticketOriginStop;
	private String ticketDestinationStop;
	private String ticketPassengerCount;
	private String reservedTicketInfo;

	// Device Information
	private String ptoOperationId;
	private String deviceId;
	private String reservedDeviceInfo;

	// Crew Information
	private String conductorEmployeeId;
	private String driverEmployeeId;
	private String shiftCode;
	private String shiftBatchNumber;
	private String tripNumber;
	private String routeCode;
	private String transportId;
	private String currentStopId;
	private String reservedCrewInfo;

	// Card Information
	private String cardNumber;
	private String cardBalance;
	private String cardExpiryDate;
	private String reservedCardInfo;

	// Reconciliation Information
	private String paymentTransactionUniqueId;
	private String paymentTransactionTerminalUid;
	private String reservedReconciliationInfo;

	// Pass Information
	private String passType;
	private String reservedPassInfo;

	// Packet Validation
	private String checkSum;

	// send to PG request

	private Long txnAmount;

	private String merchantName;

	private String invoiceNumber;

	private String registerNumber;

	private String cardToken;

	private CardData cardData;

	private String cardHolderName;

	private String cVV;

	private String cardType;

	private String terminalId;

	private String originChannel;

	private String transactionType;

	private String entryMode;

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getcVV() {
		return cVV;
	}

	public void setcVV(String cVV) {
		this.cVV = cVV;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getOriginChannel() {
		return originChannel;
	}

	public void setOriginChannel(String originChannel) {
		this.originChannel = originChannel;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getEntryMode() {
		return entryMode;
	}

	public void setEntryMode(String entryMode) {
		this.entryMode = entryMode;
	}

	public Long getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(Long txnAmount) {
		this.txnAmount = txnAmount;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getRegisterNumber() {
		return registerNumber;
	}

	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}

	public String getCardToken() {
		return cardToken;
	}

	public void setCardToken(String cardToken) {
		this.cardToken = cardToken;
	}

	public CardData getCardData() {
		return cardData;
	}

	public void setCardData(CardData cardData) {
		this.cardData = cardData;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTxnRefNumber() {
		return txnRefNumber;
	}

	public void setTxnRefNumber(String txnRefNumber) {
		this.txnRefNumber = txnRefNumber;
	}

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public String getIpPort() {
		return ipPort;
	}

	public void setIpPort(String ipPort) {
		this.ipPort = ipPort;
	}

	public String getCgRefNumber() {
		return cgRefNumber;
	}

	public void setCgRefNumber(String cgRefNumber) {
		this.cgRefNumber = cgRefNumber;
	}

	public Long getMerchantAmount() {
		return merchantAmount;
	}

	public void setMerchantAmount(Long merchantAmount) {
		this.merchantAmount = merchantAmount;
	}

	public Long getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(Long feeAmount) {
		this.feeAmount = feeAmount;
	}

	public Long getTotalTxnAmount() {
		return totalTxnAmount;
	}

	public void setTotalTxnAmount(Long totalTxnAmount) {
		this.totalTxnAmount = totalTxnAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSplitRefNumber() {
		return splitRefNumber;
	}

	public void setSplitRefNumber(String splitRefNumber) {
		this.splitRefNumber = splitRefNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTimeZoneOffset() {
		return timeZoneOffset;
	}

	public void setTimeZoneOffset(String timeZoneOffset) {
		this.timeZoneOffset = timeZoneOffset;
	}

	public String getTimeZoneRegion() {
		return timeZoneRegion;
	}

	public void setTimeZoneRegion(String timeZoneRegion) {
		this.timeZoneRegion = timeZoneRegion;
	}

	public String getDeviceSerial() {
		return deviceSerial;
	}

	public void setDeviceSerial(String deviceSerial) {
		this.deviceSerial = deviceSerial;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getTicketFareAmount() {
		return ticketFareAmount;
	}

	public void setTicketFareAmount(String ticketFareAmount) {
		this.ticketFareAmount = ticketFareAmount;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	private String orderId;

	private String txnRefNumber;

	private String authId;

	private String ipPort;

	private String cgRefNumber;

	private Long merchantAmount;

	private Long feeAmount;

	private Long totalTxnAmount;

	private String description;

	private String splitRefNumber;

	private String mobileNumber;

	private String accountNumber;

	private String qrCode;

	private String currencyCode;

	private String userName;

	private String timeZoneOffset;

	private String timeZoneRegion;

	private String deviceSerial;

	private String email;

	private String txnId;

	private String ticketFareAmount;

	private String merchantCode;

	private String operatorId;

	public void parseTicketTxnRequest(String data) {

		// Ticket Information Data Parsing
		ticketNumber = data.substring(TicketConstants.TICKET_NUM_INITIAL_INDEX, TicketConstants.TICKET_NUM_FINAL_INDEX)
				.trim();
		transactionId = data
				.substring(TicketConstants.TRANSACTION_ID_INITIAL_INDEX, TicketConstants.TRANSACTION_ID_FINAL_INDEX)
				.trim();

		logger.info("Started parsing ticket date and time");
		ticketDateTime = data
				.substring(TicketConstants.TICKET_DATE_TIME_INITIAL_INDEX, TicketConstants.TICKET_DATE_TIME_FINAL_INDEX)
				.trim();
		logger.info(TicketConstants.FINISHED_TICKET_DATE_TIME, ticketDateTime);
		ticketFare = data.substring(TicketConstants.TICKET_FARE_INITIAL_INDEX, TicketConstants.TICKET_FARE_FINAL_INDEX)
				.trim();
		ticketOperationDate = data.substring(TicketConstants.TICKET_OPERATION_DATE_INITIAL_INDEX,
				TicketConstants.TICKET_OPERATION_DATE_FINAL_INDEX).trim();
		ticketPaymentMode = data.substring(TicketConstants.TICKET_PAYMENT_MODE_INITIAL_INDEX,
				TicketConstants.TICKET_PAYMENT_MODE_FINAL_INDEX).trim();
		ticketOriginStop = data.substring(TicketConstants.TICKET_ORIGIN_STOP_INITIAL_INDEX,
				TicketConstants.TICKET_ORIGIN_STOP_FINAL_INDEX).trim();
		ticketDestinationStop = data
				.substring(TicketConstants.TICKET_DEST_STOP_INITIAL_INDEX, TicketConstants.TICKET_DEST_STOP_FINAL_INDEX)
				.trim();
		ticketPassengerCount = data.substring(TicketConstants.TICKET_PASSENGER_COUNT_INITIAL_INDEX,
				TicketConstants.TICKET_PASSENGER_COUNT_FINAL_INDEX).trim();
		reservedTicketInfo = data
				.substring(TicketConstants.RESERVED_TICKET_INITIAL_INDEX, TicketConstants.RESERVED_TICKET_FINAL_INDEX)
				.trim();

		logger.info(TicketConstants.TICKET_NO, ticketNumber);
		logger.info(TicketConstants.TXN_ID, transactionId);
		logger.info(TicketConstants.TICKET_DATE_TIME, ticketDateTime);
		logger.info(TicketConstants.TICKET_FARE, ticketFare);
		logger.info(TicketConstants.TICKET_OPERATION_DATE, ticketOperationDate);
		logger.info(TicketConstants.TICKET_PAYMENT_MODE, ticketPaymentMode);
		logger.info(TicketConstants.TICKET_ORIGIN_STOP, ticketOriginStop);
		logger.info(TicketConstants.TICKET_DESTINATION_STOP, ticketDestinationStop);
		logger.info(TicketConstants.TICKET_PASSANGER_COUNT, ticketPassengerCount);
		logger.info(TicketConstants.TICKET_RESERVED_INFO, reservedTicketInfo);

		// Device Information Data Parsing
		ptoOperationId = data
				.substring(TicketConstants.PTO_OPERATION_ID_INITIAL_INDEX, TicketConstants.PTO_OPERATION_ID_FINAL_INDEX)
				.trim();

		deviceId = data.substring(TicketConstants.DEVICE_ID_INITIAL_INDEX, TicketConstants.DEVICE_ID_FINAL_INDEX)
				.trim();

		reservedDeviceInfo = data
				.substring(TicketConstants.RESERVERD_DEVICE_INITIAL_INDEX, TicketConstants.RESERVERD_DEVICE_FINAL_INDEX)
				.trim();

		logger.info(TicketConstants.PTO_OPERATION_ID, ptoOperationId);

		logger.info(TicketConstants.DEVICE_ID, deviceId);

		logger.info(TicketConstants.RESERVED_DEVICE_INFO, reservedDeviceInfo);

		// Crew Information Data Parsing
		conductorEmployeeId = data
				.substring(TicketConstants.CONDUCTOR_EMP_ID_INITIAL_INDEX, TicketConstants.CONDUCTOR_EMP_ID_FINAL_INDEX)
				.trim();
		driverEmployeeId = data
				.substring(TicketConstants.DRIVER_EMP_ID_INITIAL_INDEX, TicketConstants.DRIVER_EMP_ID_FINAL_INDEX)
				.trim();
		shiftCode = data.substring(TicketConstants.SHIFT_CODE_INITIAL_INDEX, TicketConstants.SHIFT_CODE_FINAL_INDEX)
				.trim();
		shiftBatchNumber = data
				.substring(TicketConstants.SHIFT_BATCH_NUM_INITIAL_INDEX, TicketConstants.SHIFT_BATCH_NUM_FINAL_INDEX)
				.trim();
		tripNumber = data.substring(TicketConstants.TRIP_NUM_INITIAL_INDEX, TicketConstants.TRIP_NUM_FINAL_INDEX)
				.trim();
		routeCode = data.substring(TicketConstants.ROUTE_CODE_INITIAL_INDEX, TicketConstants.ROUTE_CODE_FINAL_INDEX)
				.trim();
		transportId = data
				.substring(TicketConstants.TRANSPORT_ID_INITIAL_INDEX, TicketConstants.TRANSPORT_ID_FINAL_INDEX).trim();
		currentStopId = data
				.substring(TicketConstants.CURRENT_STOP_ID_INITIAL_INDEX, TicketConstants.CURRENT_STOP_ID_FINAL_INDEX)
				.trim();

		reservedCrewInfo = data
				.substring(TicketConstants.RESERVED_CREW_INITIAL_INDEX, TicketConstants.RESERVED_CREW_FINAL_INDEX)
				.trim();

		logger.info(TicketConstants.CONDUCTOR_EMP_ID, conductorEmployeeId);
		logger.info(TicketConstants.DRIVER_EMP_ID, driverEmployeeId);
		logger.info(TicketConstants.SHIFT_ID, shiftCode);
		logger.info(TicketConstants.TRIP_ID, tripNumber);
		logger.info(TicketConstants.ROUTE_CODE, routeCode);
		logger.info(TicketConstants.VEHICLE_NO, transportId);
		logger.info(TicketConstants.CURRENT_STOP_ID, currentStopId);
		logger.info(TicketConstants.RESERVED_CREW_INFO, reservedCrewInfo);

		// Card Information Data Parsing
		if (!ticketPaymentMode.equalsIgnoreCase(TicketConstants.TICKET_CASH_PAYMENT_MODE)) {
			cardNumber = data.substring(TicketConstants.CARD_NUM_INITIAL_INDEX, TicketConstants.CARD_NUM_FINAL_INDEX)
					.trim();
			cardBalance = data
					.substring(TicketConstants.CARD_BALANCE_INITIAL_INDEX, TicketConstants.CARD_BALANCE_FINAL_INDEX)
					.trim();

			cardExpiryDate = data
					.substring(TicketConstants.CARD_EXP_DATE_INITIAL_INDEX, TicketConstants.CARD_EXP_DATE_FINAL_INDEX)
					.trim();

			reservedCardInfo = data
					.substring(TicketConstants.RESERVED_CARD_INITIAL_INDEX, TicketConstants.RESERVED_CARD_FINAL_INDEX)
					.trim();

			logger.info(TicketConstants.CARD_NO, cardNumber);
			logger.info(TicketConstants.CARD_BALANCE, cardBalance);
			logger.info(TicketConstants.CARD_EXPIRY, cardExpiryDate);
			logger.info(TicketConstants.CARD_RESERVED_INFO, reservedCardInfo);
		}
		// Reconciliation Information Data Parsing
		if (!ticketPaymentMode.equalsIgnoreCase(TicketConstants.TICKET_CASH_PAYMENT_MODE)) {

			paymentTransactionUniqueId = data.substring(TicketConstants.PAYMENT_TXN_UNIQUE_ID_INITIAL_INDEX,
					TicketConstants.PAYMENT_TXN_UNIQUE_ID_FINAL_INDEX).trim();
			paymentTransactionTerminalUid = data.substring(TicketConstants.PAYMENT_TXN_TERMINAL_UID_INITIAL_INDEX,
					TicketConstants.PAYMENT_TXN_TERMINAL_UID_FINAL_INDEX).trim();
			reservedReconciliationInfo = data.substring(TicketConstants.RESERVED_RECONCILIATION_INITIAL_INDEX,
					TicketConstants.RESERVED_RECONCILIATION_DATE_FINAL_INDEX).trim();

			logger.info(TicketConstants.PAY_TXN_ID, paymentTransactionUniqueId);
			logger.info(TicketConstants.PAY_TXN_TERMINAL_ID, paymentTransactionTerminalUid);
			logger.info(TicketConstants.RESERVED_RECONCIL_INFO, reservedReconciliationInfo);
		}
		// Pass Information Data Parsing

		if (!transactionId.equalsIgnoreCase(TicketConstants.TICKET_CASH_PAYMENT_MODE)) {
			passType = data.substring(TicketConstants.PASS_TYPE_INITIAL_INDEX, TicketConstants.PASS_TYPE_FINAL_INDEX)
					.trim();

			reservedPassInfo = data
					.substring(TicketConstants.RESERVED_PASS_INITIAL_INDEX, TicketConstants.RESERVED_PASS_FINAL_INDEX)
					.trim();

			logger.info(TicketConstants.PASS_TYPE, passType);
			logger.info(TicketConstants.RESERVED_PASS_INFO, reservedPassInfo);
		}

		operatorId = data.substring(TicketConstants.OPERATOR_INITIAL_INDEX, TicketConstants.OPERATOR_FINAL_INDEX)
				.trim();
		logger.info(TicketConstants.OPERATOR, checkSum);

		// Packet validation Data Parsing
		checkSum = data.substring(TicketConstants.CHECKSUM_INDEX).trim();

		logger.info(TicketConstants.CHECKSUM, checkSum);

	}

	// Ticket Information POJO
	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTicketDateTime() {

		logger.info("Inside get Ticket date time()");
		return ticketDateTime;
	}

	public void setTicketDateTime(String ticketDateTime) {
		this.ticketDateTime = ticketDateTime;
	}

	public String getTicketFare() {
		return ticketFare;
	}

	public void setTicketFare(String ticketFare) {
		this.ticketFare = ticketFare;
	}

	public String getTicketOperationDate() {
		return ticketOperationDate;
	}

	public void setTicketOperationDate(String ticketOperationDate) {
		this.ticketOperationDate = ticketOperationDate;
	}

	public String getTicketPaymentMode() {
		return ticketPaymentMode;
	}

	public void setTicketPaymentMode(String ticketPaymentMode) {
		this.ticketPaymentMode = ticketPaymentMode;
	}

	public String getTicketOriginStop() {
		return ticketOriginStop;
	}

	public void setTicketOriginStop(String ticketOriginStop) {
		this.ticketOriginStop = ticketOriginStop;
	}

	public String getTicketDestinationStop() {
		return ticketDestinationStop;
	}

	public void setTicketDestinationStop(String ticketDestinationStop) {
		this.ticketDestinationStop = ticketDestinationStop;
	}

	public String getTicketPassengerCount() {
		return ticketPassengerCount;
	}

	public void setTicketPassengerCount(String ticketPassengerCount) {
		this.ticketPassengerCount = ticketPassengerCount;
	}

	public String getReservedTicketInfo() {
		return reservedTicketInfo;
	}

	public void setReservedTicketInfo(String reservedTicketInfo) {
		this.reservedTicketInfo = reservedTicketInfo;
	}

	public String getTicketFareOptionalPositiveAmount() {
		return ticketFareOptionalPositiveAmount;
	}

	public void setTicketFareOptionalPositiveAmount(String ticketFareOptionalPositiveAmount) {
		this.ticketFareOptionalPositiveAmount = ticketFareOptionalPositiveAmount;
	}

	public String getTicketFareOptionalNegativeAmount() {
		return ticketFareOptionalNegativeAmount;
	}

	public void setTicketFareOptionalNegativeAmount(String ticketFareOptionalNegativeAmount) {
		this.ticketFareOptionalNegativeAmount = ticketFareOptionalNegativeAmount;
	}

	// Device Information POJO
	public String getPtoOperationId() {
		return ptoOperationId;
	}

	public void setPtoOperationId(String ptoOperationId) {
		this.ptoOperationId = ptoOperationId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getReservedDeviceInfo() {
		return reservedDeviceInfo;
	}

	public void setReservedDeviceInfo(String reservedDeviceInfo) {
		this.reservedDeviceInfo = reservedDeviceInfo;
	}

	// Crew Information POJO
	public String getConductorEmployeeId() {
		return conductorEmployeeId;
	}

	public void setConductorEmployeeId(String conductorEmployeeId) {
		this.conductorEmployeeId = conductorEmployeeId;
	}

	public String getDriverEmployeeId() {
		return driverEmployeeId;
	}

	public void setDriverEmployeeId(String driverEmployeeId) {
		this.driverEmployeeId = driverEmployeeId;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.transportId = vehicleNumber;
	}

	public String getCurrentStopId() {
		return currentStopId;
	}

	public void setCurrentStopId(String currentStopId) {
		this.currentStopId = currentStopId;
	}

	public String getReservedCrewInfo() {
		return reservedCrewInfo;
	}

	public void setReservedCrewInfo(String reservedCrewInfo) {
		this.reservedCrewInfo = reservedCrewInfo;
	}

	public String getShiftCode() {
		return shiftCode;
	}

	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}

	public String getShiftBatchNumber() {
		return shiftBatchNumber;
	}

	public void setShiftBatchNumber(String shiftBatchNumber) {
		this.shiftBatchNumber = shiftBatchNumber;
	}

	public String getTripNumber() {
		return tripNumber;
	}

	public void setTripNumber(String tripNumber) {
		this.tripNumber = tripNumber;
	}

	public String getTransportId() {
		return transportId;
	}

	public void setTransportId(String transportId) {
		this.transportId = transportId;
	}

	// Card Information POJO

	public String getCardBalance() {
		return cardBalance;
	}

	public void setCardBalance(String cardBalance) {
		this.cardBalance = cardBalance;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getReservedCardInfo() {
		return reservedCardInfo;
	}

	public void setReservedCardInfo(String reservedCardInfo) {
		this.reservedCardInfo = reservedCardInfo;
	}

	public String getCardExpiryDate() {
		return cardExpiryDate;
	}

	public void setCardExpiryDate(String cardExpiryDate) {
		this.cardExpiryDate = cardExpiryDate;
	}

	// Reconciliation Information POJO

	public String getPaymentTransactionUniqueId() {
		return paymentTransactionUniqueId;
	}

	public void setPaymentTransactionUniqueId(String paymentTransactionUniqueId) {
		this.paymentTransactionUniqueId = paymentTransactionUniqueId;
	}

	public String getPaymentTransactionTerminalUid() {
		return paymentTransactionTerminalUid;
	}

	public void setPaymentTransactionTerminalUid(String paymentTransactionTerminalUid) {
		this.paymentTransactionTerminalUid = paymentTransactionTerminalUid;
	}

	public String getReservedReconciliationInfo() {
		return reservedReconciliationInfo;
	}

	public void setReservedReconciliationInfo(String reservedReconciliationInfo) {
		this.reservedReconciliationInfo = reservedReconciliationInfo;
	}

	// Pass Information POJO
	public String getPassType() {
		return passType;
	}

	public void setPassType(String passType) {
		this.passType = passType;
	}

	public String getReservedPassInfo() {
		return reservedPassInfo;
	}

	public void setReservedPassInfo(String reservedPassInfo) {
		this.reservedPassInfo = reservedPassInfo;
	}

	// Packet Validation POJO
	public String getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

}
