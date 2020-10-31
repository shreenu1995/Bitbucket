package com.chatak.transit.afcs.server.pojo;

public class PGServiceResponse extends Response {

	private static final long serialVersionUID = 1L;

	private String errorCode;

	private String errorMessage;

	private String txnDateTime;

	private String txnRefNumber;

	private Long authId;

	private CardData cardData;

	private String cgRefNumber;

	private String merchantCode;

	private String totalAmount;

	private String totalTxnAmount;

	private String merchantName;

	private String deviceLocalTxnTime;

	private String transactionType;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getTxnDateTime() {
		return txnDateTime;
	}

	public void setTxnDateTime(String txnDateTime) {
		this.txnDateTime = txnDateTime;
	}

	public String getTxnRefNumber() {
		return txnRefNumber;
	}

	public void setTxnRefNumber(String txnRefNumber) {
		this.txnRefNumber = txnRefNumber;
	}

	public Long getAuthId() {
		return authId;
	}

	public void setAuthId(Long authId) {
		this.authId = authId;
	}

	public CardData getCardData() {
		return cardData;
	}

	public void setCardData(CardData cardData) {
		this.cardData = cardData;
	}

	public String getCgRefNumber() {
		return cgRefNumber;
	}

	public void setCgRefNumber(String cgRefNumber) {
		this.cgRefNumber = cgRefNumber;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTotalTxnAmount() {
		return totalTxnAmount;
	}

	public void setTotalTxnAmount(String totalTxnAmount) {
		this.totalTxnAmount = totalTxnAmount;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getDeviceLocalTxnTime() {
		return deviceLocalTxnTime;
	}

	public void setDeviceLocalTxnTime(String deviceLocalTxnTime) {
		this.deviceLocalTxnTime = deviceLocalTxnTime;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

}
