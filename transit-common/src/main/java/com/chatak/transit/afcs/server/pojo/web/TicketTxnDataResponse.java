package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TicketTxnDataResponse implements Serializable {

	private static final long serialVersionUID = -3536097436553375077L;

	private Long id;
	private String ticketNumber;
	private String transactionId;
	private Timestamp ticketDateTime;
	private double ticketFareAmount;
	private String ticketFareOptionalPositiveAmount;
	private String ticketFareOptionalNegativeAmount;
	private Date ticketOperationDate;
	private String ticketPaymentMode;
	private String ticketOriginStationCode;
	private String ticketDestStationCode;
	private String ticketPassengerCount;
	private Long ptoId;
	private Long deviceId;
	private String conductorEmpId;
	private String driverEmpId;
	private String shiftCode;
	private int shiftBatchNumber;
	private String tripNumber;
	private String routeCode;
	private String transportID;
	private String currentStopId;
	private String cardNumber;
	private String cardBalance;
	private String cardExpiryDate;
	private String paymentTxnUniqueId;
	private String paymentTxnTerminalId;
	private String passTypeCode;
	private String checksum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Timestamp getTicketDateTime() {
		return ticketDateTime;
	}

	public void setTicketDateTime(Timestamp ticketDateTime) {
		this.ticketDateTime = ticketDateTime;
	}

	public double getTicketFareAmount() {
		return ticketFareAmount;
	}

	public void setTicketFareAmount(double ticketFareAmount) {
		this.ticketFareAmount = ticketFareAmount;
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

	public Date getTicketOperationDate() {
		return ticketOperationDate;
	}

	public void setTicketOperationDate(Date ticketOperationDate) {
		if (ticketOperationDate != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar time = Calendar.getInstance();
			time.set(Calendar.YEAR, Integer.parseInt(ticketOperationDate.toString().substring(0, 4)));
			time.set(Calendar.MONTH, Integer.parseInt(ticketOperationDate.toString().substring(5, 7)) - 1);
			time.set(Calendar.DATE, Integer.parseInt(ticketOperationDate.toString().substring(8, 10)) + 1);
			this.ticketOperationDate = Date.valueOf(sdf.format(time.getTime()));
		} else {
			this.ticketOperationDate = null;
		}

	}

	public String getTicketPaymentMode() {
		return ticketPaymentMode;
	}

	public void setTicketPaymentMode(String ticketPaymentMode) {
		this.ticketPaymentMode = ticketPaymentMode;
	}

	public String getTicketOriginStationCode() {
		return ticketOriginStationCode;
	}

	public void setTicketOriginStationCode(String ticketOriginStationCode) {
		this.ticketOriginStationCode = ticketOriginStationCode;
	}

	public String getTicketDestStationCode() {
		return ticketDestStationCode;
	}

	public void setTicketDestStationCode(String ticketDestStationCode) {
		this.ticketDestStationCode = ticketDestStationCode;
	}

	public String getTicketPassengerCount() {
		return ticketPassengerCount;
	}

	public void setTicketPassengerCount(String ticketPassengerCount) {
		this.ticketPassengerCount = ticketPassengerCount;
	}

	public Long getPtoId() {
		return ptoId;
	}

	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public String getConductorEmpId() {
		return conductorEmpId;
	}

	public void setConductorEmpId(String conductorEmpId) {
		this.conductorEmpId = conductorEmpId;
	}

	public String getDriverEmpId() {
		return driverEmpId;
	}

	public void setDriverEmpId(String driverEmpId) {
		this.driverEmpId = driverEmpId;
	}

	public String getShiftCode() {
		return shiftCode;
	}

	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}

	public int getShiftBatchNumber() {
		return shiftBatchNumber;
	}

	public void setShiftBatchNumber(int shiftBatchNumber) {
		this.shiftBatchNumber = shiftBatchNumber;
	}

	public String getTripNumber() {
		return tripNumber;
	}

	public void setTripNumber(String tripNumber) {
		this.tripNumber = tripNumber;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public String getTransportID() {
		return transportID;
	}

	public void setTransportID(String transportID) {
		this.transportID = transportID;
	}

	public String getCurrentStopId() {
		return currentStopId;
	}

	public void setCurrentStopId(String currentStopId) {
		this.currentStopId = currentStopId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardBalance() {
		return cardBalance;
	}

	public void setCardBalance(String cardBalance) {
		this.cardBalance = cardBalance;
	}

	public String getCardExpiryDate() {
		return cardExpiryDate;
	}

	public void setCardExpiryDate(String cardExpiryDate) {
		this.cardExpiryDate = cardExpiryDate;
	}

	public String getPaymentTxnUniqueId() {
		return paymentTxnUniqueId;
	}

	public void setPaymentTxnUniqueId(String paymentTxnUniqueId) {
		this.paymentTxnUniqueId = paymentTxnUniqueId;
	}

	public String getPaymentTxnTerminalId() {
		return paymentTxnTerminalId;
	}

	public void setPaymentTxnTerminalId(String paymentTxnTerminalId) {
		this.paymentTxnTerminalId = paymentTxnTerminalId;
	}

	public String getPassTypeCode() {
		return passTypeCode;
	}

	public void setPassTypeCode(String passTypeCode) {
		this.passTypeCode = passTypeCode;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

}