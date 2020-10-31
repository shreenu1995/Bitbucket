package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import java.util.List;

public class DiscountListResponse extends WebResponse implements Serializable {

	private static final long serialVersionUID = -574781143454900348L;

	private List<DiscountRequest> listOfDiscount;

	private int totalRecords;

	private String discountName;

	public String getDiscountName() {
		return discountName;
	}

	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public List<DiscountRequest> getListOfDiscount() {
		return listOfDiscount;
	}

	public void setListOfDiscount(List<DiscountRequest> listOfDiscount) {
		this.listOfDiscount = listOfDiscount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DiscountListResponse [listOfDiscount=");
		builder.append(listOfDiscount);
		builder.append(", totalRecords=");
		builder.append(totalRecords);
		builder.append("]");
		return builder.toString();
	}

}
