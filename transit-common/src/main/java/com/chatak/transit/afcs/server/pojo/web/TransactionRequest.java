package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.chatak.transit.afcs.server.constants.Constants;

public class TransactionRequest implements Serializable {

	private static final long serialVersionUID = -3536097436553375077L;

	@NotEmpty(message = "chatak.afcs.service.user.id.required")
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.user.id.invalid")
	private String userId;

	@NotBlank(message = "chatak.afcs.service.transaction.id.required")
	@Size(min = 4, max = 4, message = "chatak.afcs.service.transaction.id.invalid")
	private String transactionId;

	@NotBlank(message = "chatak.afcs.service.password.required")
	@Size(min = 6, max = 15, message = "chatak.afcs.service.password.size.invalid")
	private String password;

	@NotBlank(message = "chatak.afcs.service.datetime.required")
	@Size(min = 19, max = 19, message = "chatak.afcs.service.datetime.invalid")
	private String dateTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public String toString() {

		StringBuilder response = new StringBuilder();

		return response.append("AdminTransactionDataRequest [userId=").append(getUserId()).append(", password=")
				.append(password).append(", transactionid=").append(getTransactionId()).append(", datetime=")
				.append(dateTime).append("]").toString();
	}
}