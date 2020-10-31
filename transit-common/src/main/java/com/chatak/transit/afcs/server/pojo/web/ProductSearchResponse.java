package com.chatak.transit.afcs.server.pojo.web;

import java.util.List;

public class ProductSearchResponse extends WebResponse {

	private static final long serialVersionUID = 1L;
	
	private List<ProductUpdateRequest> listOfProduct;
	
	private int totalNoOfRecords;
	
	private String productName;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public List<ProductUpdateRequest> getListOfProduct() {
		return listOfProduct;
	}

	public void setListOfProduct(List<ProductUpdateRequest> listOfProduct) {
		this.listOfProduct = listOfProduct;
	}

	public int getTotalNoOfRecords() {
		return totalNoOfRecords;
	}

	public void setTotalNoOfRecords(int totalNoOfRecords) {
		this.totalNoOfRecords = totalNoOfRecords;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductSearchResponse [listOfProduct=");
		builder.append(listOfProduct);
		builder.append(", totalNoOfRecords=");
		builder.append(totalNoOfRecords);
		builder.append(", productName=");
		builder.append(productName);
		builder.append("]");
		return builder.toString();
	}

}
