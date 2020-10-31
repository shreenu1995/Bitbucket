package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.model.NotEmptyAndNull;
import com.chatak.transit.afcs.server.model.RegexCheck;
import com.chatak.transit.afcs.server.model.SizeCheck;

public class ChangePasswordRequest implements Serializable {

	private static final long serialVersionUID = -3536097436553375077L;

	@NotEmpty(message = "chatak.afcs.service.user.id.required",groups=NotEmptyAndNull.class)
	@Size(min=8, max = 32, message = "chatak.afcs.service.user.id.length.invalid",groups=SizeCheck.class)
	@Pattern(regexp = Constants.EMAIL_REGXP,message = "chatak.afcs.service.user.id.invalid", groups=RegexCheck.class)
	private String userId;

	@NotBlank(message = "chatak.afcs.service.current.password.required")
	@Size(min=6,max = 15, message = "chatak.afcs.service.password.size.invalid")
	private String currentPassword;

	@NotBlank(message = "chatak.afcs.service.new.password.required")
	@Size(min=6,max = 15, message = "chatak.afcs.service.password.size.invalid")
	private String newPassword;
	
	private String confirmNewPassword;
	
	private String email;

	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {

		StringBuilder response = new StringBuilder();

		return response.append("ChangePasswordRequest [userid=").append(getUserId()).append(", transactionid=")
				.append(", currentpassword=").append(currentPassword)
				.append(", newpassword=").append(newPassword).append("]").toString();

	}

}
