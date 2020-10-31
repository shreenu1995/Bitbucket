/**
 * 
 */
package com.chatak.transit.afcs.server.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author pradeep
 *
 */
@Entity
@Table(name = "batch_summary")
public class BatchSummary implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "batch_summary_id_seq", sequenceName = "batch_summary_id_seq")
	@GeneratedValue(generator = "batch_summary_id_seq")
	@Column(name = "bs_id")
	private int bsId;

	@Column(name = "device_id")
	private int deviceId;

	@ManyToOne
    @JoinColumn(name = "operator_id")
    private Operator operator;

	@Column(name = "cash_payment_count")
	private int cashPaymentCount;

	@Column(name = "card_payment_count")
	private int cardPaymentCount;

	@Column(name = "total_count")
	private int totalCount;

	@Column(name = "cash_amount")
	private int cashAmount;

	@Column(name = "card_amount")
	private int cardAmount;

	@Column(name = "total_amount")
	private int totalAmount;

	@Column(name = "batch_number")
	private int batchNumber;

	@Column(name = "shift_code")
	private int shiftCode;

	public int getBsId() {
		return bsId;
	}

	public void setBsId(int bsId) {
		this.bsId = bsId;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public int getCashPaymentCount() {
		return cashPaymentCount;
	}

	public void setCashPaymentCount(int cashPaymentCount) {
		this.cashPaymentCount = cashPaymentCount;
	}

	public int getCardPaymentCount() {
		return cardPaymentCount;
	}

	public void setCardPaymentCount(int cardPaymentCount) {
		this.cardPaymentCount = cardPaymentCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(int cashAmount) {
		this.cashAmount = cashAmount;
	}

	public int getCardAmount() {
		return cardAmount;
	}

	public void setCardAmount(int cardAmount) {
		this.cardAmount = cardAmount;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(int batchNumber) {
		this.batchNumber = batchNumber;
	}

	public int getShiftCode() {
		return shiftCode;
	}

	public void setShiftCode(int shiftCode) {
		this.shiftCode = shiftCode;
	}

}
