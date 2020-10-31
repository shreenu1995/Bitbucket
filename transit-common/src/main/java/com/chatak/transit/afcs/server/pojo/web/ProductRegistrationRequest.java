package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class ProductRegistrationRequest extends LoginResponseParameters implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long productId;
	private Long organizationId;
	private Long ptoId;
	private String productType;
	private String productName;
	private String ticketAndPassType;
	private Long amount;
	private String validFrom;
	private String validTo;
	private Long discount;
	private String status;
	private String userId;
	private String reason;
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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

	public String getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	public String getValidTo() {
		return validTo;
	}

	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}

	public Long getDiscount() {
		return discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductRegistrationRequest [productId=");
		builder.append(productId);
		builder.append(", organizationId=");
		builder.append(organizationId);
		builder.append(", ptoId=");
		builder.append(ptoId);
		builder.append(", productType=");
		builder.append(productType);
		builder.append(", productName=");
		builder.append(productName);
		builder.append(", ticketAndPassType=");
		builder.append(ticketAndPassType);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", validFrom=");
		builder.append(validFrom);
		builder.append(", validTo=");
		builder.append(validTo);
		builder.append(", discount=");
		builder.append(discount);
		builder.append(", status=");
		builder.append(status);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", reason=");
		builder.append(reason);
		builder.append("]");
		return builder.toString();
	}

}