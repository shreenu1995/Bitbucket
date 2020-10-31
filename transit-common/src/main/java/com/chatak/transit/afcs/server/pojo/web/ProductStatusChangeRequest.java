package com.chatak.transit.afcs.server.pojo.web;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.chatak.transit.afcs.server.constants.Constants;

public class ProductStatusChangeRequest extends BaseRequest{

	private static final long serialVersionUID = 5716646251536876340L;

	@NotEmpty(message = "chatak.afcs.service.user.id.required")
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.user.id.invalid")
	private String userId;

	private Long productId;

	private String status;

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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
		builder.append("ProductStatusChangeRequest [userId=");
		builder.append(userId);
		builder.append(", productId=");
		builder.append(productId);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
	
}
