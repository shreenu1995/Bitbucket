package com.chatak.transit.afcs.server.pojo;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatchSummary implements Serializable {

	@NotEmpty(message = "CashPaymentCount is required")
	@Size(max = 4)
	private String cashPaymentCount;

	@NotEmpty(message = "CardPaymentCount is required")
	@Size(max = 4)
	private String cardPaymentCount;

	@NotEmpty(message = "TotalCount is required")
	@Size(max = 5)
	private String totalCount;

	@NotEmpty(message = "CashAmount is required")
	@Size(max = 8)
	private String cashAmount;

	@NotEmpty(message = "CardAmount is required")
	@Size(max = 8)
	private String cardAmount;

	@NotEmpty(message = "TotalAmount is required")
	@Size(max = 8)
	private String totalAmount;

	@NotEmpty(message = "BatchNumber is required")
	@Size(max = 5)
	private String batchNumber;

	@NotEmpty(message = "ShiftCode is required")
	@Size(max = 5)
	private String shiftCode;

	public String getCashPaymentCount() {
		return cashPaymentCount;
	}

	public void setCashPaymentCount(String cashPaymentCount) {
		this.cashPaymentCount = cashPaymentCount;
	}

	public String getCardPaymentCount() {
		return cardPaymentCount;
	}

	public void setCardPaymentCount(String cardPaymentCount) {
		this.cardPaymentCount = cardPaymentCount;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(String cashAmount) {
		this.cashAmount = cashAmount;
	}

	public String getCardAmount() {
		return cardAmount;
	}

	public void setCardAmount(String cardAmount) {
		this.cardAmount = cardAmount;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getShiftCode() {
		return shiftCode;
	}

	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}

}
