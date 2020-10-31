package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class DiscountResponse extends WebResponse implements Serializable {

	private static final long serialVersionUID = 7995102697079463634L;
	private String discountType;
	private String discountName;
	private Long routeStageStationDifference;
	private Long discount;
	private Long discountId;
	private int pageIndex;
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
	public Long getDiscountId() {
		return discountId;
	}
	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
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
		builder.append("DiscountResponse [discountType=");
		builder.append(discountType);
		builder.append(", discountName=");
		builder.append(discountName);
		builder.append(", routeStageStationDifference=");
		builder.append(routeStageStationDifference);
		builder.append(", discount=");
		builder.append(discount);
		builder.append(", discountId=");
		builder.append(discountId);
		builder.append(", pageIndex=");
		builder.append(pageIndex);
		builder.append("]");
		return builder.toString();
	}


}
