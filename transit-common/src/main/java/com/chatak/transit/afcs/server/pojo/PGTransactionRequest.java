package com.chatak.transit.afcs.server.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PGTransactionRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long txnAmount;

	private String merchantName;

	private String invoiceNumber;

	private String registerNumber;

	private String cardToken;

	private CardData cardData;

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

	private String ticketNumber;

	private String transactionId;

	private String ticketDateTime;

	private String ticketFareAmount;

	private String ticketPaymentMode;

	private String merchantCode;

	private String terminalId;

	private String entryMode;

	private String originChannel;

	private String transactionType;

	public String getEntryMode() {
		return entryMode;
	}

	public void setEntryMode(String entryMode) {
		this.entryMode = entryMode;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getOriginChannel() {
		return originChannel;
	}

	public void setOriginChannel(String originChannel) {
		this.originChannel = originChannel;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getTxnId() {
		return txnId;
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

	public String getTicketDateTime() {
		return ticketDateTime;
	}

	public void setTicketDateTime(String ticketDateTime) {
		this.ticketDateTime = ticketDateTime;
	}

	public String getTicketFareAmount() {
		return ticketFareAmount;
	}

	public void setTicketFareAmount(String ticketFareAmount) {
		this.ticketFareAmount = ticketFareAmount;
	}

	public String getTicketPaymentMode() {
		return ticketPaymentMode;
	}

	public void setTicketPaymentMode(String ticketPaymentMode) {
		this.ticketPaymentMode = ticketPaymentMode;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return the qrCode
	 */
	public String getQrCode() {
		return qrCode;
	}

	/**
	 * @param qrCode
	 *            the qrCode to set
	 */
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	/**
	 * @return the merchatntName
	 */
	public String getMerchantName() {
		return merchantName;
	}

	/**
	 * @param merchatntName
	 *            the merchatntName to set
	 */
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * @param cardData
	 *            the cardData to set
	 */
	public void setCardData(CardData cardData) {
		this.cardData = cardData;
	}

	/**
	 * @param registerNumber
	 *            the registerNumber to set
	 */
	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}

	/**
	 * @return the cardToken
	 */
	public String getCardToken() {
		return cardToken;
	}

	/**
	 * @param invoiceNumber
	 *            the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return the registerNumber
	 */
	public String getRegisterNumber() {
		return registerNumber;
	}

	/**
	 * @param cardToken
	 *            the cardToken to set
	 */
	public void setCardToken(String cardToken) {
		this.cardToken = cardToken;
	}

	/**
	 * @return the cardData
	 */
	public CardData getCardData() {
		return cardData;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the authId
	 */
	public String getAuthId() {
		return authId;
	}

	/**
	 * @param authId
	 *            the authId to set
	 */
	public void setAuthId(String authId) {
		this.authId = authId;
	}

	/**
	 * @return the txnRefNumber
	 */
	public String getTxnRefNumber() {
		return txnRefNumber;
	}

	/**
	 * @param txnRefNumber
	 *            the txnRefNumber to set
	 */
	public void setTxnRefNumber(String txnRefNumber) {
		this.txnRefNumber = txnRefNumber;
	}

	public String getIpPort() {
		return ipPort;
	}

	public void setIpPort(String ipPort) {
		this.ipPort = ipPort;
	}

	/**
	 * @return the merchatntAmount
	 */
	public Long getMerchantAmount() {
		return merchantAmount;
	}

	/**
	 * @param merchatntAmount
	 *            the merchatntAmount to set
	 */
	public void setMerchantAmount(Long merchantAmount) {
		this.merchantAmount = merchantAmount;
	}

	/**
	 * @return the issueerTxnRefNumber
	 */
	public String getCgRefNumber() {
		return cgRefNumber;
	}

	/**
	 * @param issueerTxnRefNumber
	 *            the issueerTxnRefNumber to set
	 */
	public void setCgRefNumber(String cgRefNumber) {
		this.cgRefNumber = cgRefNumber;
	}

	/**
	 * @return the feeAmount
	 */
	public Long getFeeAmount() {
		return feeAmount;
	}

	/**
	 * @param feeAmount
	 *            the feeAmount to set
	 */
	public void setFeeAmount(Long feeAmount) {
		this.feeAmount = feeAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the totalTxnAmount
	 */
	public Long getTotalTxnAmount() {
		return totalTxnAmount;
	}

	/**
	 * @param totalTxnAmount
	 *            the totalTxnAmount to set
	 */
	public void setTotalTxnAmount(Long totalTxnAmount) {
		this.totalTxnAmount = totalTxnAmount;
	}

	/**
	 * @return the txnAmount
	 */
	public Long getTxnAmount() {
		return txnAmount;
	}

	/**
	 * @param txnAmount
	 *            the txnAmount to set
	 */
	public void setTxnAmount(Long txnAmount) {
		this.txnAmount = txnAmount;
	}

	/**
	 * @return the splitRefNumber
	 */
	public String getSplitRefNumber() {
		return splitRefNumber;
	}

	/**
	 * @param splitRefNumber
	 *            the splitRefNumber to set
	 */
	public void setSplitRefNumber(String splitRefNumber) {
		this.splitRefNumber = splitRefNumber;
	}

	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * @param mobileNumber
	 *            the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber
	 *            the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
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

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
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

}
