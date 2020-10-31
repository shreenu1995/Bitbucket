package com.chatak.transit.afcs.server.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "pg_transaction")
public class PgTransactionData implements Serializable {

	private static final long serialVersionUID = -1086204217090812459L;

	@Id
	@SequenceGenerator(name = "seq_pg_transaction", sequenceName = "seq_pg_transaction")
	@GeneratedValue(generator = "seq_pg_transaction")
	@Column(name = "id")
	private Long id;

	@Column(name = "txn_status")
	private String txnStatus;

	@Column(name = "txn_date_time")
	private Timestamp txDdateTime;

	@Column(name = "txn_ref_number")
	private String txnRefNumber;

	@Column(name = "auth_id")
	private Long authId;

	@Column(name = "cg_ref_number")
	private String cgRefNumber;

	@Column(name = "merchant_code")
	private String merchantCode;

	@Column(name = "total_amount")
	private Long totalAmount;

	@Column(name = "created_time")
	private Timestamp createdTime;

	@Column(name = "total_txn_amount")
	private Long totalTxnAmount;

	@Column(name = "merchant_name")
	private String merchantName;

	@Column(name = "device_local_txn_time")
	private String deviceLocalTxnTime;

	@Column(name = "transaction_type")
	private String transactionType;

	@Column(name = "ticket_txn_id")
	private Long ticketTxnId;

	public Long getTicketTxnId() {
		return ticketTxnId;
	}

	public void setTicketTxnId(Long ticketTxnId) {
		this.ticketTxnId = ticketTxnId;
	}

	public String getTxnStatus() {
		return txnStatus;
	}

	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}

	public Timestamp getTxDdateTime() {
		return txDdateTime;
	}

	public void setTxDdateTime(Timestamp txDdateTime) {
		this.txDdateTime = txDdateTime;
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

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public Long getTotalTxnAmount() {
		return totalTxnAmount;
	}

	public void setTotalTxnAmount(Long totalTxnAmount) {
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
