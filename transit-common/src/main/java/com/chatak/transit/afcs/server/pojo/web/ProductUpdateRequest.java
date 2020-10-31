package com.chatak.transit.afcs.server.pojo.web;

public class ProductUpdateRequest extends LoginResponseParameters {

	private Long productId;
	private String organizationName;
	private String ptoName;
	private String productType;
	private String productName;
	private String ticketAndPassType;
	private Long amount;
	private Long discount;
	private String validFrom;
	private String validTo;
	private String status;
	private Long organizationId;
	private Long ptoId;


	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getPtoName() {
		return ptoName;
	}

	public void setPtoName(String ptoName) {
		this.ptoName = ptoName;
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

	public Long getPtoId() {
		return ptoId;
	}

	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductUpdateRequest [productId=");
		builder.append(productId);
		builder.append(", organizationName=");
		builder.append(organizationName);
		builder.append(", ptoName=");
		builder.append(ptoName);
		builder.append(", productType=");
		builder.append(productType);
		builder.append(", productName=");
		builder.append(productName);
		builder.append(", ticketAndPassType=");
		builder.append(ticketAndPassType);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", discount=");
		builder.append(discount);
		builder.append(", validFrom=");
		builder.append(validFrom);
		builder.append(", validTo=");
		builder.append(validTo);
		builder.append(", status=");
		builder.append(status);
		builder.append(", organizationId=");
		builder.append(organizationId);
		builder.append(", ptoId=");
		builder.append(ptoId);
		builder.append("]");
		return builder.toString();
	}

}
