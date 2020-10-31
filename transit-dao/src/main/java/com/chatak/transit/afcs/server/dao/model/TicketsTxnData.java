package com.chatak.transit.afcs.server.dao.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tickets_txn_data")
public class TicketsTxnData implements Serializable {

	private static final long serialVersionUID = -1840023577939172431L;

	@Id
	@SequenceGenerator(name = "seq_tickets_txn_data", sequenceName = "seq_tickets_txn_data")
	@GeneratedValue(generator = "seq_tickets_txn_data")

	@Column(name = "id")
	private Long id;

	// Ticket
	@Column(name = "ticket_number")
	private String ticketNumber;

	@Column(name = "transaction_id")
	private String transactionId;

	@Column(name = "ticket_date_time")
	private Timestamp ticketDateTime;

	@Column(name = "ticket_fare_amount")
	private double ticketFareAmount;

	@Column(name = "ticket_fare_optional_positive_amount")
	private String ticketFareOptionalPositiveAmount;

	@Column(name = "ticket_fare_optional_negative_amount")
	private String ticketFareOptionalNegativeAmount;

	@Column(name = "ticket_operation_date")
	private Date ticketOperationDate;

	@Column(name = "ticket_payment_mode")
	private String ticketPaymentMode;

	@Column(name = "ticket_origin_stop")
	private String ticketOriginStationCode;

	@Column(name = "ticket_destination_stop")
	private String ticketDestStationCode;

	@Column(name = "ticket_passenger_count")
	private String ticketPassengerCount;

	// Device
	@Column(name = "pto_id")
	private Long ptoId;

	@Column(name = "device_id")
	private Long deviceId;

	// Crew
	@Column(name = "user_employee_id1")
	private String conductorEmpId;

	@Column(name = "user_employee_id2")
	private String driverEmpId;

	@Column(name = "shift_code")
	private String shiftCode;

	@Column(name = "shift_batch_number")
	private int shiftBatchNumber;

	@Column(name = "trip_number")
	private String tripNumber;

	@Column(name = "route_code")
	private String routeCode;

	@Column(name = "transport_id")
	private String transportID;

	@Column(name = "current_stop_id")
	private String currentStopId;

	// Card
	@Column(name = "card_number")
	private String cardNumber;

	@Column(name = "card_balance")
	private String cardBalance;

	@Column(name = "card_expiry_date")
	private String cardExpiryDate;

	// Reconciliation
	@Column(name = "payment_txn_unique_id")
	private String paymentTxnUniqueId;

	@Column(name = "payment_txn_terminal_id")
	private String paymentTxnTerminalId;

	// Pass
	@Column(name = "pass_type_code")
	private String passTypeCode;

	// Packet validation
	@Column(name = "checksum")
	private String checksum;

	@Column(name = "operator_id")
	private String operatorId;

	@Column(name = "txn_sts")
	private String txnSts;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "ticket_txn_id", referencedColumnName = "id")
	private Set<PgTransactionData> pgTransactionData;

	public Set<PgTransactionData> getPgTransactionData() {
		return pgTransactionData;
	}

	public void setPgTransactionData(Set<PgTransactionData> pgTransactionData) {
		this.pgTransactionData = pgTransactionData;
	}

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
		this.ticketOperationDate = ticketOperationDate;
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

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getTxnSts() {
		return txnSts;
	}

	public void setTxnSts(String txnSts) {
		this.txnSts = txnSts;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "trip_detail_id", referencedColumnName = "id")
	private Set<TripDetailsData> tripDetailsData;

	public Set<TripDetailsData> getTripDetailsData() {
		return tripDetailsData;
	}

	public void setTripDetailsData(Set<TripDetailsData> tripDetailsData) {
		this.tripDetailsData = tripDetailsData;
	}

}
