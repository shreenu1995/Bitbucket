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
@Table(name = "product_management")
public class ProductManagement implements Serializable {

	private static final long serialVersionUID = 1543704154961700890L;

	@Id
	@SequenceGenerator(name = "seq_product_management", sequenceName = "seq_product_management")
	@GeneratedValue(generator = "seq_product_management")
	@Column(name = "product_id")
	private Long productId;

	@Column(name = "organization_id")
	private Long organizationId;
	
	@Column(name = "pto_id")
	private Long ptoId;

	@Column(name = "product_type")
	private String productType;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "ticket_and_pass_type")
	private String ticketAndPassType;

	@Column(name = "amount")
	private Long amount;

	@Column(name = "discount")
	private Long discount;

	@Column(name = "valid_from")
	private Timestamp validFrom;

	@Column(name = "valid_to")
	private Timestamp validTo;

	@Column(name = "status")
	private String status;

	@Column(name = "reason")
	private String reason;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getPtoId() {
		return ptoId;
	}

	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getTicketAndPassType() {
		return ticketAndPassType;
	}

	public void setTicketAndPassType(String ticketAndPassType) {
		this.ticketAndPassType = ticketAndPassType;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getDiscount() {
		return discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public Timestamp getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Timestamp validFrom) {
		this.validFrom = validFrom;
	}

	public Timestamp getValidTo() {
		return validTo;
	}

	public void setValidTo(Timestamp validTo) {
		this.validTo = validTo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
