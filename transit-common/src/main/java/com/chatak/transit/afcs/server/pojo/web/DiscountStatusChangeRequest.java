package com.chatak.transit.afcs.server.pojo.web;

public class DiscountStatusChangeRequest extends BaseRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4196686081007634225L;

	private Long discountId;

	private String status;

	public Long getDiscountId() {
		return discountId;
	}

	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DiscountStatusChangeRequest [discountId=");
		builder.append(discountId);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}
