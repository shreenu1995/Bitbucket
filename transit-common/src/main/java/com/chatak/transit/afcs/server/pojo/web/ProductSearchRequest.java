package com.chatak.transit.afcs.server.pojo.web;

public class ProductSearchRequest extends LoginResponseParameters {

	private Long ptoId;
	private String ptoName;
	private String organizationName;
	private Long productId;
	private String productType;
	private String productName;
	private String status;
	private int pageIndex;
	private String validFrom;
	private String validTo;
	private Long organizationId;
	private int pageSize;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Long getPtoId() {
		return ptoId;
	}

	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}

	public String getPtoName() {
		return ptoName;
	}

	public void setPtoName(String ptoName) {
		this.ptoName = ptoName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
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

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductSearchRequest [ptoId=");
		builder.append(ptoId);
		builder.append(", ptoName=");
		builder.append(ptoName);
		builder.append(", organizationName=");
		builder.append(organizationName);
		builder.append(", productId=");
		builder.append(productId);
		builder.append(", productType=");
		builder.append(productType);
		builder.append(", productName=");
		builder.append(productName);
		builder.append(", status=");
		builder.append(status);
		builder.append(", pageIndex=");
		builder.append(pageIndex);
		builder.append(", validFrom=");
		builder.append(validFrom);
		builder.append(", validTo=");
		builder.append(validTo);
		builder.append(", organizationId=");
		builder.append(organizationId);
		builder.append("]");
		return builder.toString();
	}

}
