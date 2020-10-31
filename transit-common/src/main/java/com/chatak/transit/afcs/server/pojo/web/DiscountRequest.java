package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class DiscountRequest extends LoginResponseParameters implements Serializable {

	private static final long serialVersionUID = -2029196559417410198L;
	private Long organizationId;
	private Long ptoId;
	private Long discountId;
	private Long routeStageStationDifference;
	private Long discount;
	private String discountType;
	private String discountName;
	private String status;
	private int pageIndex;
	private String organizationName;
	private String ptoName;
	private int  pageSize;
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
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
	public Long getDiscountId() {
		return discountId;
	}
	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}
	public Long getRouteStageStationDifference() {
		return routeStageStationDifference;
	}
	public void setRouteStageStationDifference(Long routeStageStationDifference) {
		this.routeStageStationDifference = routeStageStationDifference;
	}
	public Long getDiscount() {
		return discount;
	}
	public void setDiscount(Long discount) {
		this.discount = discount;
	}
	public String getDiscountType() {
		return discountType;
	}
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	public String getDiscountName() {
		return discountName;
	}
	public void setDiscountName(String discountName) {
		this.discountName = discountName;
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
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DiscountRequest [organizationId=");
		builder.append(organizationId);
		builder.append(", ptoId=");
		builder.append(ptoId);
		builder.append(", discountId=");
		builder.append(discountId);
		builder.append(", routeStageStationDifference=");
		builder.append(routeStageStationDifference);
		builder.append(", discount=");
		builder.append(discount);
		builder.append(", discountType=");
		builder.append(discountType);
		builder.append(", discountName=");
		builder.append(discountName);
		builder.append(", status=");
		builder.append(status);
		builder.append(", pageIndex=");
		builder.append(pageIndex);
		builder.append(", organizationName=");
		builder.append(organizationName);
		builder.append(", ptoName=");
		builder.append(ptoName);
		builder.append("]");
		return builder.toString();
	}


}
